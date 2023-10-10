<?php

namespace app\admin\controller\log;

use app\admin\controller\Base;
use app\admin\service\log\BoxExchangeService;
use app\admin\service\log\ExpressOrderService;

class BoxExchange extends Base
{
    /**
     * 兑换列表
     */
    public function index()
    {
        $boxExchangeService = new BoxExchangeService();
        return json($boxExchangeService->getList(input('param.')));
    }

    /**
     * 兑换详情
     */
    public function detail()
    {
        $boxExchangeService = new BoxExchangeService();
        return json($boxExchangeService->detail(input('get.')));
    }

    /**
     * 运费支付记录
     */
    public function expressOrderLog()
    {
        $expressOrderService = new ExpressOrderService();
        return json($expressOrderService->getExpressOrderInfo(input('param.')));
    }
}