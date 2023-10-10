<?php

namespace app\api\controller\v1\goods;

use app\api\service\goods\BlindboxService;
use app\BaseController;

class Blindbox extends BaseController
{
    /**
     * 盲盒商品详情
     */
    public function detail()
    {
        $blindboxService = new BlindboxService();
        return json($blindboxService->getBlindboxDetail(request()->get()));
    }

    /**
     * 盲盒商品的介绍
     */
    public function info()
    {
        $blindboxService = new BlindboxService();
        return json($blindboxService->getGoodsInfo(request()->get()));
    }
}