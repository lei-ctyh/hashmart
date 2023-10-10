<?php

namespace app\admin\controller\rule;

use app\admin\controller\Base;
use app\admin\service\rule\AdminService;

class Admin extends Base
{
    /**
     * 管理员列表
     */
    public function index()
    {
        $adminService = new AdminService();
        return json($adminService->getList(input('param.')));
    }

    /**
     * 添加管理员
     */
    public function add()
    {
        $adminService = new AdminService();
        return json($adminService->addAdmin(input('post.')));
    }

    /**
     * 编辑管理员
     */
    public function edit()
    {
        $adminService = new AdminService();
        return json($adminService->editAdmin(input('post.')));
    }

    /**
     * 删除管理员
     */
    public function del()
    {
        $adminService = new AdminService();
        return json($adminService->delAdmin(input('param.id')));
    }
}