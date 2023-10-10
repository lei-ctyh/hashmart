<?php

namespace app\api\service\shop;

use app\api\validate\shop\ShopOrderValidate;
use app\model\goods\Goods;
use app\model\goods\GoodsRuleExtend;
use app\model\order\Order;
use app\model\order\OrderDeliverDetail;
use app\model\order\OrderDetail;
use app\model\user\User;
use app\model\user\UserAddress;
use app\model\user\UserIntegralChangeLog;
use strategy\express\ExpressProvider;
use think\facade\Cache;
use think\facade\Db;
use think\facade\Log;
use utils\CapitalChange;

class ShopOrderService
{
    protected $traceId = '';

    public function __construct($traceId = '')
    {
        $this->traceId = $traceId;
    }

    /**
     * 订单试算
     * @param $param
     * @return array
     */
    public function trail($param)
    {
        $shopOrderValidate = new ShopOrderValidate();
        if (!$shopOrderValidate->check($param)) {
            return dataReturn(-1, $shopOrderValidate->getError());
        }

        // 商品信息
        $goodsModel = new Goods();
        $goodsInfo = $goodsModel->findOne(['id' => $param['goods_id']]);
        if (empty($goodsInfo['data'])) {
            return dataReturn(-1, '商品信息异常');
        }

        $goodsInfo = $goodsInfo['data'];
        $ruleInfo = [];
        // 多规格商品
        if ($goodsInfo['type'] == 2) {
            if (empty($param['sku'])) {
                return dataReturn(-2, '请选择商品规格');
            }

            $hasRule = false;
            foreach ($goodsInfo['ruleExtend'] as $vo) {
                if ($vo['sku'] == $param['sku']) {
                    $hasRule = true;
                    $ruleInfo = $vo;
                }
            }

            if (!$hasRule) {
                return dataReturn(-3, '商品规格错误');
            }
        }

        // 判断库存是否充足
        if ($goodsInfo['type'] == 1) {
            if ($goodsInfo['stock'] != -1 && $goodsInfo['stock'] < $param['num']) {
                return dataReturn(-4, '库存不足');
            }
        } else {
            if ($ruleInfo['stock'] != -1 && $ruleInfo['stock'] < $param['num']) {
                return dataReturn(-5, '库存不足');
            }
        }

        $integralPrice = ($goodsInfo['type'] == 2) ? $ruleInfo['integral_price'] : $goodsInfo['integral_price'];
        $trailData = [
            'goods_id' => $goodsInfo['id'],
            'goods_name' => $goodsInfo['name'],
            'goods_image' => ($goodsInfo['type'] == 2) ? $ruleInfo['image'] : $goodsInfo['image'],
            'rule_id' => ($goodsInfo['type'] == 2) ? $ruleInfo['id'] : 0,
            'sku' => $param['sku'],
            'num' => $param['num'],
            'price' => ($goodsInfo['type'] == 2) ? $ruleInfo['price'] : $goodsInfo['price'],
            'delivery_fee' => $goodsInfo['delivery_fee'],
            'integral_price' => $integralPrice,
            'total_price' => round($integralPrice * $param['num'], 2)
        ];

        return dataReturn(0, 'success', $trailData);
    }

    /**
     * 创建订单
     * @param $param
     * @return array
     */
    public function createOrder($param)
    {
        $shopOrderValidate = new ShopOrderValidate();
        if (!$shopOrderValidate->check($param)) {
            return dataReturn(-1, $shopOrderValidate->getError());
        }

        $trailData = $this->trail($param);
        if ($trailData['code'] != 0) {
            return $trailData;
        }

        $trailData = $trailData['data'];

        // 查询当前用户的哈希币余额
        $userModel = new User();
        $userInfo = $userModel->findOne(['id' => $param['user_info']['id']])['data'];
        if (empty($userInfo)) {
            return dataReturn(-1, '用户信息错误');
        }

        if ($userInfo['integral'] < $trailData['total_price']) {
            return dataReturn(-2, '您的哈希币不足');
        }

        $orderNo = makeOrderNo('G');
        $payOrderNo = makeOrderNo('G');
        $postage = $trailData['delivery_fee'];

        // 查询地址信息
        $addressInfo = (new UserAddress())->findOne([
            'id' => $param['address_id'],
            'user_id' => $userInfo['id']
        ], 'receiver,phone,province_name,city_name,area_name,address')['data'];

        if (empty($addressInfo)) {
            return dataReturn(-12, '收货地址错误');
        }

        Db::startTrans();
        try {

            $orderId = (new Order())->insertGetId([
                'pid' => 0,
                'type' => 1,
                'order_no' => $orderNo,
                'pay_order_no' => $payOrderNo,
                'user_id' => $param['user_info']['id'],
                'user_name' => $param['user_info']['nickname'],
                'blindbox_id' => 0,
                'box_id' => 0,
                'play_id' => 0,
                'total_num' => $trailData['num'],
                'unit_price' => $trailData['integral_price'],
                'postage' => $postage,
                'order_price' => $trailData['total_price'],
                'pay_way' => 3,
                'pay_price' => 0, // TODO 实际支付0元
                'pay_integral' => $trailData['total_price'] + $postage,
                'pay_status' => 1,
                'address_id' => $param['address_id'],
                'address_info' => json_encode($addressInfo),
                'status' => 1,
                'create_time' => date('Y-m-d H:i:s')
            ]);

            (new OrderDetail())->insert([
                'order_id' => $orderId,
                'user_id' => $param['user_info']['id'],
                'goods_id' => $trailData['goods_id'],
                'goods_name' => $trailData['goods_name'],
                'goods_image' => $trailData['goods_image'],
                'rule_id' => $trailData['rule_id'],
                'rule' => $trailData['sku'],
                'num' => $trailData['num'],
                'real_pay_amount' => 0,
                'real_pay_integral' => $trailData['total_price'] + $postage, // TODO 目前是没有购物车，有购物车这里得改
                'create_time' => date('Y-m-d H:i:s')
            ]);
            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-11, '系统错误', $e->getMessage());
        }

        return dataReturn(0, '创建成功', [
            'order_no' => $payOrderNo
        ]);
    }

    /**
     * 订单支付
     * @param $param
     * @return array
     */
    public function payOrder($param)
    {
        if (empty($param['order_no'])) {
            return dataReturn(-1, '订单参数错误');
        }

        // 验证订单信息
        $orderModel = new Order();
        $orderInfo = $orderModel->findOne([
            'pay_order_no' => $param['order_no'],
            'status' => 1
        ])['data'];
        if (empty($orderInfo)) {
            return dataReturn(-1, '订单信息错误');
        }

        Db::startTrans();
        try {

            // 库存处理
            $orderDetail = (new OrderDetail())->findOne(['order_id' => $orderInfo['id']])['data'];

            if ($orderDetail['rule_id'] == 0) { // 单规格
                $goodsModel = new Goods();
                $goodsInfo = $goodsModel->where('id', $orderDetail['goods_id'])->lock(true)->find();
                if ($goodsInfo['stock'] != -1 && $goodsInfo['stock'] < $orderInfo['total_num']) {
                    return dataReturn(-2, '库存不足');
                }

                $stock = -1;
                if ($goodsInfo['stock'] != -1) {
                    $stock = $goodsInfo['stock'] - $orderInfo['total_num'];
                }

                $goodsModel->where('id', $goodsInfo['id'])->update(['stock' => $stock]);
            } else { // 多规格
                $goodsExtend = new GoodsRuleExtend();
                $goodsExtendInfo = $goodsExtend->where('id', $orderDetail['rule_id'])->lock(true)->find();
                if ($goodsExtendInfo['stock'] != -1 && $goodsExtendInfo['stock'] < $orderInfo['total_num']) {
                    return dataReturn(-2, '库存不足');
                }

                $stock = -1;
                if ($goodsExtendInfo['stock'] != -1) {
                    $stock = $goodsExtendInfo['stock'] - $orderInfo['total_num'];
                }

                $goodsExtend->where('id', $goodsExtendInfo['id'])->update(['stock' => $stock]);
            }

            // 扣除哈希币
            $res = CapitalChange::integralDec([
                'amount' => $orderInfo['pay_integral'],
                'user_id' => $param['user_info']['id']
            ]);
            if ($res['code'] != 0) {
                return dataReturn(-3, '系统错误');
            }

            // 修改订单状态
            $orderModel->where('id', $orderInfo['id'])->update([
                'status' => 2,
                'pay_status' => 2,
                'update_time' => date('Y-m-d H:i:s')
            ]);

            // 记录哈希币变动记录
            (new UserIntegralChangeLog())->insert([
                'user_id' => $param['user_info']['id'],
                'username' => $param['user_info']['nickname'],
                'integral' => 0 - $orderInfo['pay_integral'],
                'type' => 2,
                'exchange_id' => 0,
                'order_id' => $orderInfo['id'],
                'create_time' => date('Y-m-d H:i:s')
            ]);

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('哈希币支付错误: ' . $e->getMessage() . '|' . $e->getTraceAsString());
            return dataReturn(-2, '系统错误' . $e->getMessage());
        }

        return dataReturn(0, '支付成功');
    }

    /**
     * 获取物流信息
     * @param $orderId
     * @return array
     */
    public function getDeliverExpress($orderId)
    {
        $userInfo = getJWT(getHeaderToken());

        $orderModel = new Order();
        $info = $orderModel->where([
            'id' => $orderId,
            'user_id' => $userInfo['id']
        ])->with('deliverDetail')->find();

        if (empty($info)) {
            return dataReturn(-1, '该笔单号不存在');
        }

        // 已签收
        if ($info['deliverDetail']['status'] == 2) {
            return dataReturn(0, 'success', json_decode($info['deliverDetail']['express_detail'], true));
        }

        $userInfo = (new User())->findOne([
            'id' => $userInfo['id']
        ])['data'];

        // 30分钟未更新了
        if (time() - strtotime($info['update_time']) > 1800 || empty($info['deliverDetail']['express_detail'])) {
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
            $orderDetail = new OrderDeliverDetail();
            $orderDetail->updateById([
                'status' => $isEnd ? 2 : 1,
                'express_detail' => $expressInfo,
                'update_time' => now()
            ], $info['deliverDetail']['id']);

            return dataReturn(0, '查询成功', $res);
        } else {
            return dataReturn(0, '查询成功', json_decode($info['deliverDetail']['express_detail'], true));
        }
    }
}