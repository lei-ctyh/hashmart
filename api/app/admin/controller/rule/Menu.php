<?php

namespace app\admin\controller\rule;

use app\admin\controller\Base;
use app\admin\service\rule\MenuService;

class Menu extends Base
{
    /**
     * 菜单列表
     */
    public function index()
    {
        $menuService = new MenuService();
        return json($menuService->getAllMenu());
    }

    /**
     * 添加菜单
     */
    public function add()
    {
        $menuService = new MenuService();
        return json($menuService->addMenu(input('post.')));
    }

    /**
     * 菜单编辑
     */
    public function edit()
    {
        $menuService = new MenuService();
        return json($menuService->editMenu(input('post.')));
    }

    /**
     * 菜单删除
     */
    public function del()
    {
        $menuService = new MenuService();
        return json($menuService->delMenu(input('param.id')));
    }

    /**
     * 获取所有菜单
     */
    public function getAllMenu()
    {
        $menuService = new MenuService();
        return json($menuService->getAllMenuList());
    }
}