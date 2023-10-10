<?php

namespace strategy\order\impl;

use app\model\order\Order;
use app\model\user\User;
use strategy\lottery\LotteryProvider;
use strategy\order\OrderInterface;
use think\facade\Db;
use think\facade\Log;

class BlindboxOrder implements OrderInterface
{
    public function dealOrder($param)
    {
        $data = $param['data'];

        $orderModel = new Order();
        $orderInfo = $orderModel->findOne([
            'pay_order_no' => $data->out_trade_no,
            'status' => 1
        ])['data'];
        if (empty($orderInfo)) {
            Log::error('【订单未查询到错误】三方支付回调异常: ' . json_encode($param));
            return dataReturn(-1);
        }

        Db::startTrans();
        try {

            if ($param['way'] == 1) {
                $payAmount = $data->total_fee / 100;
                $thirdCode = $data->transaction_id;
            } else {
                $payAmount = $data->total_amount;
                $thirdCode = $data->trade_no;
            }

            // 金额对不上，部分支付
            if ($payAmount != $orderInfo['pay_price']) {
                $orderModel->where('id', $orderInfo['id'])->update([
                    'pay_status' => 5,
                    'status' => 2,
                    'third_code' => $thirdCode,
                    'return_msg' => json_encode($data->all()),
                    'pay_time' => date('Y-m-d H:i:s'),
                    'update_time' => date('Y-m-d H:i:s')
                ]);

                Db::commit();
                return dataReturn(-2);
            }

            // 维护订单状态
            $orderModel->where('id', $orderInfo['id'])->update([
                'pay_status' => 2,
                'status' => ($orderInfo['type'] == 2) ? 5 : 2,
                'third_code' => $thirdCode,
                'return_msg' => json_encode($data->all()),
                'pay_time' => date('Y-m-d H:i:s'),
                'update_time' => date('Y-m-d H:i:s')
            ]);

            $userModel = new User();
            $userInfo = $userModel->findOne([
                'id' => $orderInfo['user_id']
            ])['data'];
            if (empty($userInfo)) {
                return dataReturn(-3);
            }

            // 盲盒订单走开奖逻辑
            if ($orderInfo['type'] == 2) {
                $lotteryProvider = new LotteryProvider($orderInfo['play_id']);
                $lotteryProvider->getStrategy()->run([
                    'id' => $orderInfo['id'],
                    'total_num' => $orderInfo['total_num'],
                    'unit_price' => $orderInfo['unit_price'],
                    'hash_key' => $userInfo['hash_key'],
                    'order_no' => $orderInfo['order_no'],
                    'trace_id' => 0,
                    'blindbox_id' => $orderInfo['blindbox_id'],
                    'user_id' => $orderInfo['user_id'],
                    'user_name' => $orderInfo['user_name'],
                    'pay_price' => $orderInfo['pay_price'],
                    'play_id' => $orderInfo['play_id'],
                    'pay_order_no' => $orderInfo['pay_order_no'],
                ]);
            }

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('【订单回调异常】: ' . $e->getMessage());
            return dataReturn(-6);
        }

        return dataReturn(0, '支付成功');
    }
}