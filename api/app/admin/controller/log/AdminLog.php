<?php

namespace app\admin\controller\log;

use app\admin\controller\Base;
use app\admin\service\log\AdminLogService;

class AdminLog extends Base
{
    /**
     * 管理员日志列表
     */
    public function index()
    {
        $adminLogService = new AdminLogService();
        return json($adminLogService->getList(input('param.')));
    }
}