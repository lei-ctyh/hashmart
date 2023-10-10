<?php

namespace app\admin\controller\rule;

use app\admin\controller\Base;
use app\admin\service\rule\RoleService;

class Role extends Base
{
    public function index()
    {
        $roleService = new RoleService();
        return json($roleService->getList(input('param.')));
    }
    /**
     * 获取全部角色
     */
    public function getAllRole()
    {
        $roleService = new RoleService();
        return json($roleService->getAllRoleList());
    }

    /**
     * 添加角色
     */
    public function add()
    {
        $roleService = new RoleService();
        return json($roleService->addRole(input('post.')));
    }

    /**
     * 编辑角色
     */
    public function edit()
    {
        $roleService = new RoleService();
        return json($roleService->editRole(input('post.')));
    }

    /**
     * 删除角色
     */
    public function del()
    {
        $roleService = new RoleService();
        return json($roleService->delRole(input('param.id')));
    }
}