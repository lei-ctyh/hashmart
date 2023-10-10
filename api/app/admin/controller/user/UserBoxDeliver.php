<?php

namespace app\admin\controller\user;

use app\admin\controller\Base;
use app\admin\service\user\UserBoxDeliverService;
use app\model\order\OrderExpress;

class UserBoxDeliver extends Base
{
    /**
     * 发货列表
     */
    public function index()
    {
        $userBoxDeliverService = new UserBoxDeliverService();
        return json($userBoxDeliverService->getList(input('param.')));
    }

    /**
     * 发货
     */
    public function deliver()
    {
        $userBoxDeliverService = new UserBoxDeliverService();
        return json($userBoxDeliverService->deliver(input('post.')));
    }

    /**
     * 运费订单
     */
    public function expressOrder()
    {
        $userBoxDeliverService = new UserBoxDeliverService();
        return json($userBoxDeliverService->getOrderInfo(input('param.id')));
    }
}