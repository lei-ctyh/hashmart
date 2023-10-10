<?php

namespace app\api\controller\v1\order;

use app\api\controller\Base;
use app\api\service\order\OrderService;

class Order extends Base
{
    protected $userInfo = [];

    public function initialize()
    {
        parent::initialize();
        $this->userInfo = getJWT(getHeaderToken());
    }

    /**
     * 试算
     */
    public function trail()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new OrderService();
        return json($orderService->trail($postParam));
    }

    /**
     * 创建的订单
     */
    public function createOrder()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new OrderService();
        return json($orderService->createOrder($postParam));
    }

    /**
     * 发起支付
     */
    public function payOrder()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new OrderService();
        return json($orderService->payOrder($postParam));
    }

    /**
     * 查询支付结果
     */
    public function result()
    {
        $postParam = request()->post();

        $orderService = new OrderService();
        return json($orderService->getResult($postParam));
    }

    /**
     * 订单列表
     */
    public function orderList()
    {
        $param = input('param.');

        $orderService = new OrderService();
        return json($orderService->getOrderList($param));
    }

    /**
     * 盲盒订单取消
     */
    public function cancel()
    {
        $param = input('post.');

        $orderService = new OrderService();
        return json($orderService->cancelOrder($param));
    }

    /**
     * 重新支付
     */
    public function repay()
    {
        $param = input('post.');
        $param['user_info'] = $this->userInfo;

        $orderService = new OrderService();
        return json($orderService->repay($param));
    }

    /**
     * 物流查询
     */
    public function express()
    {
        $deliverNo = input('param.deliver_no');

        $orderService = new OrderService();
        return json($orderService->getDeliverExpress($deliverNo));
    }
}