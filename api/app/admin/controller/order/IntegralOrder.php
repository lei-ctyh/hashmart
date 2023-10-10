<?php

namespace app\admin\controller\order;

use app\admin\controller\Base;
use app\admin\service\order\IntegralOrderService;

class IntegralOrder extends Base
{
    /**
     * 订单列表
     */
    public function index()
    {
        $integralOrderService = new IntegralOrderService();
        return json($integralOrderService->getList(input('param.')));
    }

    /**
     * 发货
     */
    public function delivery()
    {
        $integralOrderService = new IntegralOrderService();
        return json($integralOrderService->delivery(input('post.')));
    }

    /**
     * 订单详情
     */
    public function detail()
    {
        $integralOrderService = new IntegralOrderService();
        return json($integralOrderService->detail(input('get.')));
    }

    /**
     * 物流详情
     */
    public function express()
    {
        $integralOrderService = new IntegralOrderService();
        return json($integralOrderService->express(input('get.')));
    }

    /**
     * 物流公司列表
     */
    public function expressList()
    {
        $integralOrderService = new IntegralOrderService();
        return json($integralOrderService->expressList(input('get.')));
    }
}