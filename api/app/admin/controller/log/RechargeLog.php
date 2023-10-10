<?php

namespace app\admin\controller\log;

use app\admin\controller\Base;
use app\admin\service\log\RechargeLogService;

class RechargeLog extends Base
{
    /**
     * 会员充值列表
     */
    public function index()
    {
        $rechargeLogService = new RechargeLogService();
        return json($rechargeLogService->getList(input('param.')));
    }
}