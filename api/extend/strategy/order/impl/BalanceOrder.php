<?php

namespace strategy\order\impl;

use app\model\user\User;
use app\model\user\UserBalanceChangeLog;
use app\model\user\UserBalanceRechargeLog;
use strategy\order\OrderInterface;
use think\facade\Db;
use think\facade\Log;
use utils\CapitalChange;

class BalanceOrder implements OrderInterface
{
    public function dealOrder($param)
    {
        $data = $param['data'];

        $orderModel = new UserBalanceRechargeLog();
        $orderInfo = $orderModel->findOne([
            'pay_no' => $data->out_trade_no,
            'status' => 1
        ])['data'];

        if (empty($orderInfo)) {
            Log::error('【订单未查询到错误】三方支付回调异常: ' . json_encode($param));
            return dataReturn(-1);
        }

        Db::startTrans();
        try {

            // 维护订单状态
            $orderModel->where(['id' => $orderInfo['id']])->update([
                'status' => 2, // 已支付
                'third_no' => $data->transaction_id,
                'third_return' => json_encode($data->all()),
                'update_time' => now()
            ]);

            // 记录变更日志
            (new UserBalanceChangeLog())->insert([
                'user_id' => $orderInfo['user_id'],
                'username' => $orderInfo['username'],
                'balance' => $orderInfo['amount'],
                'type' => 2, // 充值新增
                'recharge_id' => $orderInfo['id'],
                'create_time' => now()
            ]);

            // 增加个人余额
            CapitalChange::balanceInc($orderInfo);

            Db::commit();
            return dataReturn(0, '充值成功');
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('余额充值订单出现错误: ' . $data->out_trade_no . '|' . $e->getTraceAsString());
            return dataReturn(-10, '余额充值异常' . $e->getMessage());
        }
    }
}