<?php

namespace app\admin\controller\log;

use app\admin\controller\Base;
use app\admin\service\log\LoginLogService;

class LoginLog extends Base
{
    /**
     * 获取会员登录记录列表
     */
    public function list()
    {
        $LoginLogService = new LoginlogService();
        return json($LoginLogService->getList(input('get.')));
    }
}