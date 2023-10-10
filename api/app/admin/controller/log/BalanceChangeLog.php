<?php

namespace app\admin\controller\log;

use app\admin\controller\Base;
use app\admin\service\log\BalanceChangeLogService;

class BalanceChangeLog extends Base
{
    /**
     * 订单列表
     */
    public function index()
    {
        $balanceChangeLogService = new BalanceChangeLogService();
        return json($balanceChangeLogService->getList(input('param.')));
    }
}