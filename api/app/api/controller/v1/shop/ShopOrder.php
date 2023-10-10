<?php

namespace app\api\controller\v1\shop;

use app\api\controller\Base;
use app\api\service\shop\ShopOrderService;

class ShopOrder extends Base
{
    /**
     * 订单试算
     */
    public function trail()
    {
        $shopOrderService = new ShopOrderService();
        return json($shopOrderService->trail(request()->post()));
    }

    /**
     * 创建订单
     */
    public function createOrder()
    {
        $shopOrderService = new ShopOrderService();
        $param = request()->post();
        $param['user_info'] = getJWT(getHeaderToken());

        return json($shopOrderService->createOrder($param));
    }

    /**
     * 支付订单
     */
    public function payOrder()
    {
        $shopOrderService = new ShopOrderService();
        $param = request()->post();
        $param['user_info'] = getJWT(getHeaderToken());

        return json($shopOrderService->payOrder($param));
    }

    /**
     * 查询物流
     */
    public function express()
    {
        $shopOrderService = new ShopOrderService();
        $orderId = input('param.order_id');

        return json($shopOrderService->getDeliverExpress($orderId));
    }
}