<?php

namespace app\api\service\order;

use app\model\goods\Goods;
use app\model\order\Order;
use app\model\order\OrderDetail;
use app\model\order\OrderExpress;
use app\model\order\OrderRecordDetail;
use app\model\user\UserAddress;
use app\model\user\UserBoxDeliver;
use app\model\user\UserBoxDeliverDetail;
use app\model\user\UserBoxExchange;
use app\model\user\UserBoxExchangeDetail;
use app\model\user\UserBoxHot;
use app\model\user\UserBoxLog;
use app\model\user\UserIntegralChangeLog;
use strategy\pay\PayProvider;
use think\Exception;
use think\facade\App;
use think\facade\Db;
use think\facade\Log;
use utils\CapitalChange;
use utils\FileLock;

class BagService
{
    /**
     * 仓库-盲盒列表
     * @param $param
     * @return array
     */
    public function getBagBoxList($param)
    {
        if (!checkOpen()) {
            return dataReturn(404, '站点正在维护');
        }

        $where[] = ["user_id", "=", $param['user_info']['id']];
        if(!empty($param['status'])){
            $where[] = ["status", "=", $param['status']];
        }

        $userBoxList = [];
        switch ($param['status']) {
            // 全部
            case 0:
                $userBoxList = $this->getAllBagGoods($param, $where);
                break;
            // 盒子中
            case 1:
                $userBoxList = $this->getBoxGoods($param, $where);
                break;
            // 已兑换
            case 2:
                $userBoxList = $this->getBoxExchange($param['limit'], $param['user_info']['id']);
                break;
            // 已提货
            case 3:
                $userBoxList = $this->getBoxDeliver($param['limit'], $param['user_info']['id']);
                break;
        }

        return dataReturn(0, 'success', $userBoxList);
    }

    /**
     * 仓库-商品列表
     * @param $param
     * @return array
     */
    public function getBagGoodsList($param)
    {
        if (!checkOpen()) {
            return dataReturn(404, '站点正在维护');
        }

        $where[] = ["d.user_id", "=", $param['user_info']['id']];
        if(!empty($param['status'])){
            $where[] = ["o.status", "=", $param['status']];
        }
        $orderDetailModel = new OrderDetail();
        $bagGoodsList = $orderDetailModel->alias('d')
            ->field(['d.id','d.order_id','d.goods_name','d.goods_image','d.create_time','o.status', 'o.order_no'])
            ->leftJoin('order o','d.order_id = o.id')
            ->where($where)
            ->order('d.id desc')
            ->paginate($param['limit']);

        return dataReturn(0, 'success', $bagGoodsList);
    }

    /**
     * 仓库-单个盲盒详情
     * @param $param
     * @return array
     */
    public function getBagBoxDetail($param)
    {
        $userBoxHotModel = new UserBoxHot();
        $userBoxHotInfo = $userBoxHotModel->findOne(
            ['id' => $param['blindbox_id'],'user_id' => $param['user_info']['id']],
            ['id','goods_name','goods_image','exchange_time','exchange_time','create_time']);

        return dataReturn(0, 'success', $userBoxHotInfo);
    }

    /**
     * 仓库-单个商品详情
     * @param $param
     * @return array
     */
    public function getBagGoodsDetail($param)
    {
        $orderDetailModel = new OrderDetail();
        $bagGoodsInfo = $orderDetailModel->findOne(
            ['id' => $param['orderdetail_id'],'user_id' => $param['user_info']['id']],
            ['id,goods_name,goods_image,create_time']);

        return dataReturn(0, 'success', $bagGoodsInfo);
    }

    /**
     * 盲盒奖品兑换
     * @param $param
     * @return array
     */
    public function bagBoxExchange($param)
    {
        if (empty($param['box_id']) || empty($param['type'])) {
            return dataReturn(-10, ' 请选择兑换的奖品');
        }

        $userInfo = $param['user_info'];

        $fileLock = new FileLock('box_operate_' . $userInfo['id'], App::getBasePath() . '/public');
        if (!$fileLock->lock()) {
            return dataReturn(-11, '上一笔兑换还在进行中请稍后再试');
        }

        Db::startTrans();
        try {

            // 盒子中的奖品数据
            if ($param['type'] == 1) {
                $blindboxBagModel = new UserBoxLog(); // 全部
            } else {
                $blindboxBagModel = new UserBoxHot(); // 盒子中
            }

            $where[] = ['id', 'in', $param['box_id']]; // box_id 形如 1,2,3
            $where[] = ['user_id', '=', $userInfo['id']];
            if ($param['type'] == 1) {
                $where[] = ['status', '=', 1];
            }

            $userBagList = $blindboxBagModel->with(['orderRecordDetail'])->where($where)->select()->toArray();
            if (empty($userBagList)) {
                Db::commit();
                return dataReturn(-1, '该奖品不存在或已被兑换');
            }

            $totalAmount = 0;
            $uuids = [];
            $recordDetailIds = [];
            foreach ($userBagList as $vo) {
                $totalAmount += $vo['recovery_price'];
                $uuids[] = $vo['uuid'];
                $recordDetailIds[] = $vo['record_detail_id'];
            }

            // 1、记录兑换记录表
            $exchangeData = [
                'exchange_no' => makeOrderNo('E'),
                'user_id' => $userInfo['id'],
                'username' => $userInfo['nickname'],
                'exchange_num' => count($userBagList),
                'total_amount' => $totalAmount,
                'status' => 2,
                'create_time' => date('Y-m-d H:i:s')
            ];
            $exchangeId = (new UserBoxExchange())->insertGetId($exchangeData);

            // 2、记录对象记录详情表
            $exchangeDetailData = [];
            foreach ($userBagList as $vo) {
                $exchangeDetailData[] = [
                    'exchange_id' => $exchangeId,
                    'user_id' => $userInfo['id'],
                    'user_box_uuid' => $vo['uuid'],
                    'record_detail_id' => $vo['record_detail_id'],
                    'goods_id' => $vo['goods_id'],
                    'num' => 1,
                    'amount' => $vo['recovery_price'],
                    'create_time' => date('Y-m-d H:i:s')
                ];
            }
            (new UserBoxExchangeDetail())->insertAll($exchangeDetailData);

            // 3、记录用户哈希币变动
            (new UserIntegralChangeLog())->insert([
                'user_id' => $userInfo['id'],
                'username' => $userInfo['nickname'],
                'integral' => $totalAmount,
                'type' => 1,
                'exchange_id' => $exchangeId,
                'create_time' => date('Y-m-d H:i:s')
            ]);

            // 4、删除用户盒子热点表
            (new UserBoxHot())->whereIn('uuid', $uuids)->delete();

            // 5、标记用户盒子记录表
            (new UserBoxLog())->whereIn('uuid', $uuids)->update([
                'status' => 2, // 已兑换
                'exchange_time' => date('Y-m-d H:i:s'),
                'update_time' => date('Y-m-d H:i:s')
            ]);

            // 6、增加用户哈希币
            $res = CapitalChange::integralInc([
                'amount' => $totalAmount,
                'user_id' => $userInfo['id']
            ]);
            if ($res['code'] != 0) {
                throw new Exception('系统异常');
            }

            // 7、标记中奖详情状态
            $orderRecordDetail = new OrderRecordDetail();
            $orderRecordDetail->whereIn('id', $recordDetailIds)->update([
                'status' => 2, // 已兑换
                'update_time' => now()
            ]);

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            $fileLock->unlock();
            return dataReturn(-10, '兑换异常', $e->getMessage());
        }
        $fileLock->unlock();

        return dataReturn(0, '兑换成功');
    }

    /**
     * 盲盒发货试算
     * @param $param
     * @return array
     */
    public function bagBoxTrail($param)
    {
        // TODO 后续运费模板 按模板
        if (empty($param['address_id'])) {
            return dataReturn(-1, '请选择收货地址');
        }

        if (empty($param['box_id'])) {
            return dataReturn(-2, '请选择发货奖品');
        }

        if (empty($param['type'])) {
            return dataReturn(-5, '请输入兑换类型');
        }

        $userInfo = $param['user_info'];
        if ($param['type'] == 1) {
            $blindboxBagModel = new UserBoxLog();
        } else {
            $blindboxBagModel = new UserBoxHot();
        }

        $where[] = ['id', 'in', $param['box_id']]; // box_id 形如 1,2,3
        $where[] = ['user_id', '=', $userInfo['id']];
        if ($param['type'] == 1) {
            $where[] = ['status', '=', 1];
        }
        $userBagList = $blindboxBagModel->getAllList($where)['data']->toArray();
        if (empty($userBagList)) {
            return dataReturn(-3, '发货奖品不存在');
        }

        $goodsIds = [];
        $bagList = [];
        foreach ($userBagList as $vo) {
            $goodsIds[] = $vo['goods_id'];
            $bagList['id'][] = $vo['id'];
            $bagList['uuid'][] = $vo['uuid'];
        }

        // 取所有商品中个邮费最大的那个当邮费
        $deliveryFee = (new Goods())->whereIn('id', $goodsIds)->max('delivery_fee');

        return dataReturn(0, $bagList, $deliveryFee);
    }

    /**
     * 盲盒发货邮费订单
     * @param $param
     * @return array
     */
    public function boxDeliver($param)
    {
        $userInfo = $param['user_info'];

        $fileLock = new FileLock('box_operate_' . $userInfo['id'], App::getBasePath() . '/public');
        if (!$fileLock->lock()) {
            return dataReturn(-11, '上一笔兑换还在进行中请稍后再试');
        }

        Db::startTrans();
        try {
            $trailData = $this->bagBoxTrail($param);
            if ($trailData['code'] != 0) {
                Db::rollback();
                $fileLock->unlock();
                return $trailData;
            }
            $deliveryFee = $trailData['data'];

            $orderNo = makeOrderNo('E');
            $payNo = makeOrderNo('E');

            // 写入邮费订单
            $orderInfo = [
                'order_no' => $orderNo,
                'pay_no' => $payNo,
                'amount' => $deliveryFee,
                'pay_way' => $param['pay_way'],
                'box_type' => $param['type'],
                'pay_status' => $deliveryFee > 0 ? 1 : 2,
                'user_id' => $userInfo['id'],
                'username' => $userInfo['nickname'],
                'address_id' => $param['address_id'],
                'express_content' => json_encode($trailData['msg']),
                'create_time' => now()
            ];

            if ($deliveryFee == 0) {
                $orderParam['pay_time'] = now();
            }

            $expressOrderInfo = (new OrderExpress())->insertOne($orderInfo);
            $orderInfo['express_order_id'] = $expressOrderInfo['data'];

            // 如果已支付，则直接完成
            if ($orderInfo['pay_status'] == 2) {
                $res = $this->completeExpressOrder($orderInfo, $param['type']);
                if ($res['code'] != 0) {
                    Db::rollback();
                } else {
                    Db::commit();
                }

                $fileLock->unlock();
                return $res;
            }

            // 拼装参数执行支付
            if ($param['pay_way'] == 4) {
                $orderInfo['pay_way'] = 40;
            }

            $payProvider = (new PayProvider($orderInfo['pay_way']))->getStrategy();
            $orderParam = [
                'id' => $expressOrderInfo['data'],
                'user_id' => $userInfo['id'],
                'username' => $userInfo['nickname'],
                'pay_price' => $orderInfo['amount'],
                'host' => getConfByType('api_url')['api_url'],
                'pay_order_no' => $orderInfo['pay_no'],
                'subject' => '邮费支付'
            ];

            if ($param['platform'] == 'miniapp') {
                $res = $payProvider->miniPay($orderParam);
            } else {
                $res = $payProvider->appPay($orderParam);
            }

            if ($orderInfo['pay_way'] == 40) {
                if ($res['code'] != 0) {
                    Db::rollback();
                    return $res;
                }

                $res = $this->completeExpressOrder($orderInfo, $param['type']);
                if ($res['code'] != 0) {
                    Db::rollback();
                } else {
                    Db::commit();
                }

                $fileLock->unlock();
                return $res;
            }

            $fileLock->unlock();
            Db::commit();
            return dataReturn(201, '邮费支付', $res); // 需要支付
        } catch (\Exception $e) {
            $fileLock->unlock();
            Db::rollback();
            return dataReturn(-10, '系统异常' . $e->getMessage() . '|' . $e->getLine());
        }
    }

    /**
     * 提货确认
     * @param $param
     * @return array
     */
    public function confirmBoxDeliver($param)
    {
        $userInfo = $param['user_info'];

        $deliverModel = new UserBoxDeliver();
        $where[] = ['deliver_no', '=', $param['deliver_no']];
        $where[] = ['user_id', '=', $userInfo['id']];
        $deliverInfo = $deliverModel->findOne($where)['data'];
        if (empty($deliverInfo)) {
            return dataReturn(-2, '确认失败');
        }

        if ($deliverInfo['status'] != 2) {
            return dataReturn(-3, '暂不可以确认收货');
        }

        $deliverModel->updateById([
            'status' => 3,
            'update_time' => date('Y-m-d H:i:s')
        ], $deliverInfo['id']);

        return dataReturn(0, '操作成功');
    }

    /**
     * 确认收货
     * @param $param
     * @return array
     */
    public function bagGoodsConfirm($param)
    {
        $userInfo = $param['user_info'];
        $where[] = ['id', '=', $param['order_id']];
        $where[] = ['user_id', '=', $userInfo['id']];

        $orderModel = new Order();
        $orderInfo = $orderModel->findOne($where)['data'];
        if (empty($orderInfo)) {
            return dataReturn(-1, '订单信息错误');
        }

        if ($orderInfo['status'] != 3) {
            return dataReturn(-2, '该订单不可以收货');
        }

        $orderModel->updateById([
            'status' => 5,
            'received_time' => date('Y-m-d H:i:s'),
            'update_time' => date('Y-m-d H:i:s')
        ], $param['order_id']);

        return dataReturn(0, '操作成功');
    }

    /**
     * 完成发货订单
     * @param $orderInfo
     * @param $type
     * @return array
     */
    public function completeExpressOrder($orderInfo, $type)
    {
        Db::startTrans();
        try {

            $boxInfo = json_decode($orderInfo['express_content'], true);
            $boxIds = $boxInfo['id'];

            // 盒子中的奖品数据
            if ($type == 1) {
                $blindboxBagModel = new UserBoxLog(); // 全部
            } else {
                $blindboxBagModel = new UserBoxHot(); // 盒子中
            }

            $where[] = ['id', 'in', $boxIds]; // box_id 形如 1,2,3
            $where[] = ['user_id', '=', $orderInfo['user_id']];
            if ($type == 1) {
                $where[] = ['status', '=', 1];
            }
            $userBagList = $blindboxBagModel->where($where)->select()->toArray();
            if (empty($userBagList)) {
                Db::commit();
                return dataReturn(-1, '该奖品不存在');
            }

            // 查询地址信息
            $addressInfo = (new UserAddress())->findOne([
                'id' => $orderInfo['address_id'],
                'user_id' => $orderInfo['user_id']
            ], 'receiver,phone,province_name,city_name,area_name,address')['data'];

            if (empty($addressInfo)) {
                return dataReturn(-12, '收货地址错误');
            }

            // 1、记录提货表
            $deliverId = (new UserBoxDeliver())->insertGetId([
                'deliver_no' => makeOrderNo('FH'),
                'express_order_id' => $orderInfo['express_order_id'],
                'address_id' => $orderInfo['address_id'],
                'address_info' => json_encode($addressInfo),
                'user_id' => $orderInfo['user_id'],
                'type' => 1,
                'status' => 1, // 待发货
                'postage_fee' => $orderInfo['amount'],
                'create_time' => now()
            ]);

            // 2、记录提货详情表
            $deliverDetail = [];
            $uuids = [];
            $recordDetailIds = [];
            foreach ($userBagList as $vo) {
                $uuids[] = $vo['uuid'];

                $deliverDetail[] = [
                    'deliver_id' => $deliverId,
                    'user_id' => $orderInfo['user_id'],
                    'user_box_uuid' => $vo['uuid'],
                    'record_detail_id' => $vo['record_detail_id'],
                    'create_time' => now()
                ];

                $recordDetailIds[] = $vo['record_detail_id'];
            }
            (new UserBoxDeliverDetail())->insertAll($deliverDetail);

            // 3、删除用户盒子热点表
            (new UserBoxHot())->whereIn('uuid', $uuids)->delete();

            // 4、标记用户盒子记录表
            (new UserBoxLog())->whereIn('uuid', $uuids)->update([
                'status' => 3, // 已提货
                'exchange_time' => now(),
                'update_time' => now()
            ]);

            // 5、标记中奖详情状态
            $orderRecordDetail = new OrderRecordDetail();
            $orderRecordDetail->whereIn('id', $recordDetailIds)->update([
                'status' => 3, // 已提货
                'update_time' => now()
            ]);

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('发货邮费支付异常: ' . $e->getMessage() . '|' . $e->getFile() . '|' . $e->getCode());
            return dataReturn(-1, '支付异常' . $e->getMessage() . '|' . $e->getFile() . '|' . $e->getCode());
        }

        return dataReturn(0, '支付成功');
    }

    /**
     * @param $param
     * @param $where
     * @param \think\Paginator
     */
    protected function getAllBagGoods($param, $where)
    {
        $userBoxLogModel = new UserBoxLog();
        return $userBoxLogModel->where($where)
            ->with('orderRecordDetail')
            ->visible(['id','goods_name','goods_image','exchange_time','exchange_time','create_time','recovery_price', 'status'])
            ->order('id desc')
            ->paginate($param['limit']);
    }

    /**
     * @param $param
     * @param $where
     * @return \think\Paginator
     */
    public function getBoxGoods($param, $where)
    {
        $userBoxHotModel = new UserBoxHot();
        return $userBoxHotModel->where($where)
            ->with('orderRecordDetail')
            ->visible(['id','goods_name','goods_image','exchange_time','exchange_time','create_time','recovery_price'])
            ->order('id desc')
            ->paginate($param['limit']);
    }

    /**
     * 获取用户兑换列表
     * @param $limit
     * @param $userId
     * @return \think\Paginator
     */
    public function getBoxExchange($limit, $userId)
    {
        $exchangeModel = new UserBoxExchange();
        return $exchangeModel->field('id,exchange_no,create_time as exchange_time')
            ->with(['detail.reward'])->where('user_id', $userId)->order('id desc')->paginate($limit);
    }

    /**
     * 获取用户提货列表
     * @param $limit
     * @param $userId
     * @return \think\Paginator
     */
    public function getBoxDeliver($limit, $userId)
    {
        $deliverModel = new UserBoxDeliver();
        return $deliverModel->field('id,deliver_no,status,create_time as deliver_time')
            ->with(['detail.rewardSimple'])->where('user_id', $userId)->order('id desc')->paginate($limit);
    }
}