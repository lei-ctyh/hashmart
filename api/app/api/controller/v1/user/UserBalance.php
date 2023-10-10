<?php

namespace app\api\controller\v1\user;

use app\api\controller\Base;
use app\api\service\user\UserBalanceService;

class UserBalance extends Base
{
    /**
     * 余额一览
     */
    public function index()
    {
        $userBalanceService = new UserBalanceService();
        return json($userBalanceService->getBalanceLogList(input('param.')));
    }

    /**
     * 获取余额基础信息
     */
    public function info()
    {
        $userBalanceService = new UserBalanceService();
        return json($userBalanceService->getBalanceInfo());
    }

    /**
     * 充值创建订单
     */
    public function createOrder()
    {
        $userBalanceService = new UserBalanceService();
        return json($userBalanceService->createOrder(input('post.')));
    }
}