<?php

namespace app\admin\controller\order;

use app\admin\controller\Base;
use app\admin\service\order\BlindBoxOrderService;

class BlindBoxOrder extends Base
{
    /**
     * 盲盒订单列表
     */
    public function index()
    {
        $blindBoxOrderService = new BlindBoxOrderService();
        return json($blindBoxOrderService->getList(input('param.')));
    }

    /**
     * 盲盒导出
     */
    public function export()
    {
        $blindBoxOrderService = new BlindBoxOrderService();
        return json($blindBoxOrderService->export(input('get.')));
    }

    /**
     * 中奖记录
     */
    public function detail()
    {
        $blindBoxOrderService = new BlindBoxOrderService();
        return json($blindBoxOrderService->detail(input('get.')));
    }
}