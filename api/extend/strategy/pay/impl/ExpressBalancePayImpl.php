<?php

namespace strategy\pay\impl;

use app\model\order\OrderExpress;
use app\model\user\UserBalanceChangeLog;
use strategy\pay\PayInterface;
use think\facade\Db;
use utils\CapitalChange;

class ExpressBalancePayImpl implements PayInterface
{
    public function miniPay($param)
    {
        return $this->pay($param);
    }

    public function appPay($param)
    {
        return $this->pay($param);
    }

    public function refund($param)
    {

    }

    protected function pay($param)
    {
        Db::startTrans();
        try {

            // 减少余额
            $userRes = CapitalChange::balanceDec([
                'amount' => $param['pay_price'],
                'user_id' => $param['user_id']
            ]);
            if ($userRes['code'] != 0) {
                return $userRes;
            }

            // 记录余额变动记录
            $res = (new UserBalanceChangeLog())->insertOne([
                'user_id' => $param['user_id'],
                'username' => $param['username'],
                'balance' => 0 - $param['pay_price'],
                'type' => 1,
                'order_id' => $param['id'],
                'create_time' => date('Y-m-d H:i:s')
            ]);
            if ($res['code'] != 0) {
                Db::rollback();
                return $res;
            }

            // 修改订单状态
            $res = (new OrderExpress())->updateById([
                'pay_status' => 2,
                'update_time' => date('Y-m-d H:i:s')
            ], $param['id']);
            if ($res['code'] != 0) {
                Db::rollback();
                return $res;
            }

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-12, $e->getMessage() . '---' . $e->getFile() . '--' . $e->getLine());
        }

        return dataReturn(0, '支付成功');
    }

    public function getConfig()
    {
        // TODO: Implement getConfig() method.
    }
}