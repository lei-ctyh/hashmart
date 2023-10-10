<?php

namespace app\api\controller\v1\shop;

use app\api\service\shop\ShopService;
use app\BaseController;

class Shop extends BaseController
{
    /**
     * 哈希币商品分类信息
     */
    public function cateList()
    {
        $shopService = new ShopService();
        return json($shopService->getCateList());
    }

    /**
     * 哈希币商品分类下的商品详情
     */
    public function cateDetail()
    {
        $shopService = new ShopService();
        return json($shopService->getGoodsListByCate(request()->get()));
    }

    /**
     * 哈希币商品列表
     */
    public function goodsList()
    {
        $shopService = new ShopService();
        return json($shopService->getGoodsList(request()->get()));
    }

    /**
     * 获取商品详情
     */
    public function goodsInfo()
    {
        $shopService = new ShopService();
        return json($shopService->getGoodsInfo(request()->get()));
    }

    /**
     * 获取幻灯
     */
    public function getSlider()
    {
        $shopService = new ShopService();
        return json($shopService->getSliderList());
    }
}