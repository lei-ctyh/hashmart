<?php

namespace app\api\service\order;

use app\api\validate\order\OrderValidate;
use app\api\validate\order\TrailValidate;
use app\model\box\Blindbox;
use app\model\order\Order;
use app\model\order\OrderRecord;
use app\model\user\User;
use app\model\user\UserBoxDeliver;
use app\model\user\UserIntegralChangeLog;
use strategy\express\ExpressProvider;
use strategy\lottery\LotteryProvider;
use strategy\pay\PayProvider;
use think\facade\Db;
use utils\CapitalChange;

class OrderService
{
    /**
     * 试算订单试算
     * @param $param
     * @param $type
     * @return array
     */
    public function trail($param, $type = 1)
    {
        $trailValidate = new TrailValidate();
        if (!$trailValidate->check($param)) {
            return dataReturn(-1, $trailValidate->getError());
        }

        // 盲盒信息
        $blindboxModel = new Blindbox();
        $blindboxInfo = $blindboxModel->findOne(['id' => $param['blindbox_id']]);

        // 查询账户余额
        $userModel = new User();
        $userInfo = $userModel->findOne([
            ['id', '=', $param['user_info']['id']],
            ['status', '=', 1]
        ], 'balance,integral')['data'];

        if (empty($userInfo)) {
            return dataReturn(-1, '用户信息错误');
        }

        $totalPrice = round($blindboxInfo['data']['price'] * $param['num'], 2);
        $canUseIntegral = 0;
        $canDeductionAmount = 0;

        // 查询可抵扣的哈希币
        $ratio = getConfByType('sys_base')['change_ratio'];
        if (!empty($ratio) && $userInfo['integral'] > 0) {
            // 可抵扣金额
            $canDeductionAmount = round($userInfo['integral'] / $ratio, 2);

            if ($canDeductionAmount > $totalPrice) {
                $canUseIntegral = round($totalPrice * $ratio, 2);
                $canDeductionAmount = $totalPrice;
                // 如果选择使用哈希币抵扣
                if ($param['use_integral'] == 1) {
                    $totalPrice = 0;
                }
            } else {
                // 如果选择使用哈希币抵扣
                if ($param['use_integral'] == 1) {
                    $totalPrice = $totalPrice - $canDeductionAmount;
                }
                $canUseIntegral = $userInfo['integral'];
            }
        }


        $trailData = [
            'blindbox_img' => $blindboxInfo['data']['pic'],
            'price' => $blindboxInfo['data']['price'],
            'num' => $param['num'],
            'total_price' => round($totalPrice, 2),
            'balance' => $userInfo['balance'],
            'canUseIntegral' => $canUseIntegral,
            'canDeductionAmount' => $canDeductionAmount
        ];

        if ($type == 2) {
            $trailData['ratio'] = $ratio;
        }

        return dataReturn(0, 'success', $trailData);
    }

    /**
     * 创建的订单
     */
    public function createOrder($param)
    {
        $trailValidate = new OrderValidate();
        if (!$trailValidate->check($param)) {
            return dataReturn(-1, $trailValidate->getError());
        }

        $trailData = $this->trail($param, 2);
        if ($trailData['code'] != 0) {
            return $trailData;
        }

        $orderNo = makeOrderNo('B');
        $payOrderNo = makeOrderNo('B');
        $postage = 0;

        $blindboxModel = new Blindbox();
        $blindboxInfo = $blindboxModel->findOne(['id' => $param['blindbox_id']]);

        $orderModel = new Order();
        $res = $orderModel->insertOne([
            'pid' => 0,
            'type' => 2,
            'order_no' => $orderNo,
            'pay_order_no' => $payOrderNo,
            'user_id' => $param['user_info']['id'],
            'user_name' => $param['user_info']['nickname'],
            'blindbox_id' => $param['blindbox_id'],
            'box_id' => 1,
            'play_id' => $blindboxInfo['data']['play_id'],
            'total_num' => $trailData['data']['num'],
            'unit_price' => $blindboxInfo['data']['price'],
            'postage' => $postage,
            'order_price' => $trailData['data']['total_price'],
            'pay_way' => $param['pay_way'],
            'pay_price' => $trailData['data']['total_price'] + $postage,
            'pay_integral' => $trailData['data']['canUseIntegral'],
            'pay_status' => 1,
            'status' => 1,
            'integral_ratio' => $trailData['data']['ratio'],
            'create_time' => date('Y-m-d H:i:s')
        ]);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '创建成功', [
            'order_no' => $payOrderNo
        ]);
    }

    /**
     * 发起支付
     */
    public function payOrder($param)
    {
        if (empty($param['order_no']) || empty($param['platform'])) {
            return dataReturn(-1, '参数错误');
        }

        $param['host'] = getConfByType('api_url')['api_url'];

        // 验证订单信息
        $orderModel = new Order();
        $orderInfo = $orderModel->findOne([
            'pay_order_no' => $param['order_no'],
            'status' => 1
        ])['data'];
        if (empty($orderInfo)) {
            return dataReturn(-1, '订单信息错误');
        }

        $orderParam = [
            'id' => $orderInfo['id'],
            'total_num' => $orderInfo['total_num'],
            'unit_price' => $orderInfo['unit_price'],
            'order_no' => $orderInfo['order_no'],
            'trace_id' => 0,
            'blindbox_id' => $orderInfo['blindbox_id'],
            'user_id' => $orderInfo['user_id'],
            'user_name' => $orderInfo['user_name'],
            'pay_price' => $orderInfo['pay_price'],
            'pay_integral' => $orderInfo['pay_integral'],
            'host' => $param['host'],
            'play_id' => $orderInfo['play_id'],
            'pay_order_no' => $orderInfo['pay_order_no'],
            'subject' => '盲盒购买' . $orderInfo['total_num'] . '个'
        ];

        // 如果使用了哈希币抵扣
        if ($orderInfo['pay_integral'] > 0) {
            // 减少哈希币
            $userRes = CapitalChange::integralDec([
                'amount' => $orderInfo['pay_integral'],
                'user_id' => $orderInfo['user_id']
            ]);
            if ($userRes['code'] != 0) {
                return $userRes;
            }

            $userInfo = $userRes['data'];

            // 记录哈希币变动记录
            (new UserIntegralChangeLog())->insert([
                'user_id' => $userInfo['id'],
                'username' => $userInfo['nickname'],
                'integral' => $orderInfo['pay_integral'],
                'type' => 1,
                'order_id' => $orderInfo['id'],
                'create_time' => date('Y-m-d H:i:s')
            ]);
        }

        // 无需实际支付
        if ($orderInfo['pay_price'] == 0) {
            return $this->completeOrder($orderParam);
        }

        // 拼装参数执行支付
        $payProvider = (new PayProvider($orderInfo['pay_way']))->getStrategy();
        if ($param['platform'] == 'miniapp') {
            $res = $payProvider->miniPay($orderParam);
        } else {
            $res = $payProvider->appPay($orderParam);
        }

        // 余额支付
        if ($orderInfo['pay_way'] == 4) {
            return $res;
        }

        return dataReturn(201, '盲盒订单支付', $res);
    }

    /**
     * 获取订单结果
     * @param $param
     * @return array
     */
    public function getResult($param)
    {
        $userInfo = getJWT(getHeaderToken());

        $orderModel = new Order();
        $orderInfo = $orderModel->findOne([
            'pay_order_no' => $param['order_no'],
            'user_id' => $userInfo['id']
        ])['data'];

        if (empty($orderInfo)) {
            return dataReturn(-1, '订单不存在');
        }

        if ($orderInfo['pay_status'] == 1) {
            return dataReturn(201, '订单尚未支付，请重试');
        }

        if ($orderInfo['pay_status'] > 2) {
            return dataReturn(-2, '订单异常');
        }

        $orderRecord = new OrderRecord();
        $reward = $orderRecord->field('user_id,username,id')->with('detail.tag')->where('order_id', $orderInfo['id'])->find()->toArray();
        foreach ($reward['detail'] as $key => $vo) {
            $reward['detail'][$key]['tag_name'] = $vo['tag']['name'];
            unset($reward['detail'][$key]['tag']);
        }

        return dataReturn(0, 'success', $reward);
    }

    /**
     * 超时订单关闭
     * @param $time
     * @return array
     */
    public function overdueClose($time)
    {
        $orderModel = new Order();
        return $orderModel->updateByWehere([
            'close_time' => now(),
            'status' => 7, // 订单关闭
            'pay_status' => 7 // 支付超时
        ], [
            ['type', '=', 2],
            ['pay_status', '=', 1],
            ['create_time', '<', date('Y-m-d H:i:s', time() - $time)]
        ]);
    }

    /**
     * 盲盒订单列表
     * @param $param
     * @return array
     */
    public function getOrderList($param)
    {
        // TODO 暂定1小时
        $this->overdueClose(3600);

        $orderModel = new Order();
        $where[] = ['type', '=', 2];
        if ($param['type'] == 1) {
            $where[] = ['status', '=', 1];
        } else if ($param['type'] == 2) {
            $where[] = ['status', '=', 5];
        }

        $userInfo = getJWT(getHeaderToken());
        $where[] = ['user_id', '=', $userInfo['id']];

        $list = $orderModel->field('order_no,pay_price,status,total_num,blindbox_id')->with(['blindbox.orderDetail'])->where($where)
            ->paginate($param['limit']);
        return pageReturn(dataReturn(0, '', $list));
    }

    /**
     * 盲盒订单列表
     * @param $param
     * @return array
     */
    public function cancelOrder($param)
    {
        $userInfo = getJWT(getHeaderToken());

        $orderModel = new Order();
        $orderInfo = $orderModel->findOne([
            'user_id' => $userInfo['id'],
            'order_no' => $param['order_no'],
            'status' => 1
        ])['data'];

        if (empty($orderInfo)) {
            return dataReturn(-1, '订单信息异常');
        }

        return $orderModel->updateById([
            'status' => 6, // 取消
            'cancel_time' => now()
        ], $orderInfo['id']);
    }

    /**
     * 盲盒订单重新支付
     * @param $param
     * @return array
     */
    public function repay($param)
    {
        if (empty($param['order_no']) || empty($param['platform'])) {
            return dataReturn(-1, '参数错误');
        }

        $param['host'] = getConfByType('api_url')['api_url'];

        // 验证订单信息
        $orderModel = new Order();
        $orderInfo = $orderModel->findOne([
            'order_no' => $param['order_no'],
            'status' => 1
        ])['data'];
        if (empty($orderInfo)) {
            return dataReturn(-1, '订单信息错误');
        }

        $payOrderNo = makeOrderNo('B');
        $orderModel->updateByWehere([
            'pay_order_no' => $payOrderNo
        ], ['id' => $orderInfo['id']]);

        // 拼装参数执行支付
        $payProvider = (new PayProvider($orderInfo['pay_way']))->getStrategy();
        $orderParam = [
            'id' => $orderInfo['id'],
            'total_num' => $orderInfo['total_num'],
            'unit_price' => $orderInfo['unit_price'],
            'order_no' => $orderInfo['order_no'],
            'trace_id' => 0,
            'blindbox_id' => $orderInfo['blindbox_id'],
            'user_id' => $orderInfo['user_id'],
            'user_name' => $orderInfo['user_name'],
            'pay_price' => $orderInfo['pay_price'],
            'host' => $param['host'],
            'play_id' => $orderInfo['play_id'],
            'pay_order_no' => $payOrderNo,
            'subject' => '盲盒购买' . $orderInfo['total_num'] . '个'
        ];

        if ($param['platform'] == 'miniapp') {
            $res = $payProvider->miniPay($orderParam);
        } else {
            $res = $payProvider->appPay($orderParam);
        }

        return $res;
    }

    /**
     * 获取奖品发货物流
     * @param $deliverNo
     * @return array
     */
    public function getDeliverExpress($deliverNo)
    {
        $userInfo = getJWT(getHeaderToken());

        $userBoxDeliverModel = new UserBoxDeliver();
        $info = $userBoxDeliverModel->findOne([
            'deliver_no' => $deliverNo,
            'user_id' => $userInfo['id']
        ])['data'];

        if (empty($info)) {
            return dataReturn(-1, '该笔单号不存在');
        }

        // 已签收
        if ($info['express_status'] == 3) {
            return dataReturn(0, 'success', json_decode($info['express'], true));
        }

        $userInfo = (new User())->findOne([
            'id' => $userInfo['id']
        ])['data'];
        // 30分钟未更新了
        if (time() - strtotime($info['update_time']) > 1800 || empty($info['express'])) {
            $res = (new ExpressProvider('aliyun'))->getStrategy()->search([
                'no' => $info['express_no'] . ':' . substr($userInfo['phone'], 7, 4),
                'type' => $info['express_code']
            ]);

            $expressInfo = $res['data'];
            $isEnd = false;
            $res = json_decode($expressInfo, true);
            if ($res['status'] == 0 && isset($res['result']['deliverystatus'])) {
                if ($res['result']['deliverystatus'] >= 3) {
                    $isEnd = true;
                }
            }

            // 更新物流
            $userBoxDeliverModel->updateById([
                'express_status' => $isEnd ? 3 : 2,
                'express' => $expressInfo,
                'update_time' => now()
            ], $info['id']);

            return dataReturn(0, '查询成功', $res);
        } else {
            return dataReturn(0, '查询成功', json_decode($info['express'], true));
        }
    }

    /**
     * 直接完成订单
     * @param $param
     * @return array
     */
    protected function completeOrder($param)
    {
        Db::startTrans();
        try {

            $userInfo = (new User())->where('id', $param['user_id'])->find();
            $param['hash_key'] = $userInfo['hash_key']; // 当前设置的哈希种子

            // 调用抽奖玩法
            $lotteryProvider = new LotteryProvider($param['play_id']);
            $res = $lotteryProvider->getStrategy()->run($param);
            if ($res['code'] != 0) {
                Db::rollback();
                return $res;
            }

            // 修改订单状态
            (new Order())->where('id', $param['id'])->update([
                'pay_status' => 2,
                'status' => 5,
                'update_time' => date('Y-m-d H:i:s')
            ]);

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-12, $e->getMessage() . '---' . $e->getFile() . '--' . $e->getLine());
        }

        return dataReturn(0, '支付成功');
    }
}