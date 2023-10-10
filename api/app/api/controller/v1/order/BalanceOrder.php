<?php

namespace app\api\controller\v1\order;

use app\api\controller\Base;
use app\api\service\user\UserBalanceRechargeService;

class BalanceOrder extends Base
{
    /**
     * 余额充值订单
     */
    public function index()
    {
        $param = input('param.');

        $userBalanceRechargeService = new UserBalanceRechargeService();
        return json($userBalanceRechargeService->getList($param));
    }

    /**
     * 取消余额充值订单
     */
    public function cancel()
    {
        $param = input('param.');

        $userBalanceRechargeService = new UserBalanceRechargeService();
        return json($userBalanceRechargeService->cancelOrder($param));
    }

    /**
     * 余额充值重新支付
     */
    public function repay()
    {
        $param = input('post.');

        $userBalanceRechargeService = new UserBalanceRechargeService();
        return json($userBalanceRechargeService->repay($param));
    }
}