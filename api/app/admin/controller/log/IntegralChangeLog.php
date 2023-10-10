<?php

namespace app\admin\controller\log;

use app\admin\controller\Base;
use app\admin\service\log\IntegralChangeLogService;

class IntegralChangeLog extends Base
{
    /**
     * 订单列表
     */
    public function index()
    {
        $integralChangeLogService = new IntegralChangeLogService();
        return json($integralChangeLogService->getList(input('param.')));
    }
}