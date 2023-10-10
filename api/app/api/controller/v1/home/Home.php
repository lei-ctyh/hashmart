<?php

namespace app\api\controller\v1\home;

use app\api\service\home\HomeService;
use app\BaseController;

class Home extends BaseController
{
    // 首页
    public function index()
    {
        $homeService = new HomeService();
        return json($homeService->getHomeData(request()->get()));
    }
}