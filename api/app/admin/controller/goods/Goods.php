<?php

namespace app\admin\controller\goods;

use app\admin\controller\Base;
use app\admin\service\goods\GoodsService;

class Goods extends Base
{
    /**
     * 商品列表
     */
    public function index()
    {
        $goodsService = new GoodsService();
        return json($goodsService->getGoodsList(input('param.')));
    }

    /**
     * 添加商品
     */
    public function add()
    {
        $goodsService = new GoodsService();
        return json($goodsService->addGoods(input('post.')));
    }

    /**
     * 编辑商品
     */
    public function edit()
    {
        $goodsService = new GoodsService();
        return json($goodsService->editGoods(input('post.')));
    }

    /**
     * 删除商品
     */
    public function del()
    {
        $goodsService = new GoodsService();
        return json($goodsService->delGoods(input('param.id')));
    }

    /**
     * 商品上下架
     */
    public function shelves()
    {
        $goodsService = new GoodsService();
        return json($goodsService->shelvesGoods(input('post.')));
    }
}