<?php

namespace app\admin\controller\goods;

use app\admin\controller\Base;
use app\admin\service\goods\GoodsCateService;

class GoodsCate extends Base
{
    /**
     * 分类列表
     */
    public function index()
    {
        $goodsCateService = new GoodsCateService();
        return json($goodsCateService->getCateList());
    }

    /**
     * 添加分类
     */
    public function add()
    {
        $goodsCateService = new GoodsCateService();
        return json($goodsCateService->addCate(input('post.')));
    }

    /**
     * 编辑分类
     */
    public function edit()
    {
        $goodsCateService = new GoodsCateService();
        return json($goodsCateService->editCate(input('post.')));
    }

    /**
     * 删除分类
     */
    public function del()
    {
        $goodsCateService = new GoodsCateService();
        return json($goodsCateService->delCate(input('param.id')));
    }
}