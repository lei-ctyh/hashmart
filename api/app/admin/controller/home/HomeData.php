<?php

namespace app\admin\controller\home;

use app\admin\controller\Base;
use app\admin\service\home\HomeDataService;

class HomeData extends Base
{
    /**
     * 获取首页统计数据
     */
    public function index()
    {
        $homeDataService = new HomeDataService();
        return json($homeDataService->getHomeData(input('param.')));
    }
}