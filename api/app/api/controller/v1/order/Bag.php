<?php

namespace app\api\controller\v1\order;

use app\api\controller\Base;
use app\api\service\order\BagService;

class Bag extends Base
{
    protected $userInfo = [];

    public function initialize()
    {
        parent::initialize();
        $this->userInfo = getJWT(getHeaderToken());
    }

    /**
     * 仓库-盲盒列表
     */
    public function getBagBoxList()
    {
        $postParam = request()->get();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->getBagBoxList($postParam));
    }

    /**
     * 仓库-商品列表
     */
    public function getBagGoodsList()
    {
        $postParam = request()->get();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->getBagGoodsList($postParam));
    }

    /**
     * 仓库-单个盲盒详情
     */
    public function getBagBoxDetail()
    {
        $postParam = request()->get();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->getBagBoxDetail($postParam));
    }

    /**
     * 仓库-单个商品详情
     */
    public function getBagGoodsDetail()
    {
        $postParam = request()->get();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->getBagGoodsDetail($postParam));
    }

    /**
     * 仓库-盲盒兑换
     */
    public function bagBoxExchange()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->bagBoxExchange($postParam));
    }

    /**
     * 盲盒发货试算
     */
    public function boxTrail()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->bagBoxTrail($postParam));
    }

    /**
     * 盲盒发货邮费订单
     */
    public function boxDeliver()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->boxDeliver($postParam));
    }

    /**
     * 仓库-盲盒提货确认
     */
    public function confirmBoxDeliver()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->confirmBoxDeliver($postParam));
    }

    /**
     * 仓库-商品确认发货
     */
    public function bagGoodsConfirm()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;

        $orderService = new BagService();
        return json($orderService->bagGoodsConfirm($postParam));
    }
}