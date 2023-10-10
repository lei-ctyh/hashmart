<?php

namespace app\admin\service\rule;

use AlibabaCloud\SDK\Dysmsapi\V20170525\Models\AddShortUrlResponseBody\data;
use app\admin\validate\MenuValidate;
use app\model\system\SysMenu;
use app\model\system\SysRole;
use think\exception\ValidateException;

class MenuService
{
    /**
     * 获取我的权限菜单
     * @param $roleId
     * @return array
     */
    public function getMyMenu($roleId)
    {
        $menuModel = new SysMenu();
        $field = 'id,pid,auth,type,name,path,icon,component';
        if ($roleId == 1) {

            $menuList = $menuModel->getAllList(['status' => 1], $field, 'sort desc')['data'];
        } else {

            $roleModel = new SysRole();
            $roleInfo = $roleModel->findOne(['id' => $roleId])['data'];
            if (empty($roleInfo)) {
                return dataReturn(-1, '角色信息有误');
            }

            $where[] = ['status', '=', 1];
            $where[] = ['id', 'in', $roleInfo['rule']];
            $menuList = $menuModel->getAllList($where, $field, 'sort desc')['data'];
        }

        foreach ($menuList as $key => $vo) {
            $menuList[$key]['meta'] = [
                'icon' => $vo['icon'],
                'title' => $vo['name'],
                'type' => 'menu',
                'hidden' => $vo['type'] == 2
            ];

            if ($vo['pid'] == 0) {
                unset($menuList[$key]['component']);
            }
        }

        return dataReturn(0, 'success', $menuList);
    }

    /**
     * 获取全部菜单
     * @return array
     */
    public function getAllMenu()
    {
        $field = '*';

        $menuModel = new SysMenu();
        $menuList = $menuModel->getAllList(['status' => 1], $field, 'sort desc')['data'];

        return dataReturn(0, 'success', makeTree($menuList->toArray()));
    }

    /**
     * 添加菜单
     * @param $param
     * @return array
     */
    public function addMenu($param)
    {
        try {
            validate(MenuValidate::class)->scene('add')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $param['create_time'] = date('Y-m-d H:i:s');

        $menuModel = new SysMenu();
        return $menuModel->insertOne($param);
    }

    /**
     * 编辑菜单
     * @param $param
     * @return array
     */
    public function editMenu($param)
    {
        try {
            validate(MenuValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $param['update_time'] = date('Y-m-d H:i:s');

        $menuModel = new SysMenu();
        return $menuModel->updateById($param, $param['id']);
    }

    /**
     * 删除菜单
     * @param $id
     * @return array
     */
    public function delMenu($id)
    {
        $menuModel = new SysMenu();
        $hasChild = $menuModel->findOne(['pid' => $id])['data'];
        if (!empty($hasChild)) {
            return dataReturn(-1, '该菜单下有子菜单不可删除');
        }

        return $menuModel->delById($id);
    }

    /**
     * Notes: 获取所有权限
     * Author: LJS
     * @return array
     */
    public function getAllMenuList(): array
    {
        $menuModel = new SysMenu();
        $menu = $menuModel->getAllList([['id', '>', 0], ['status', '=', 1]], ['id', 'pid', 'type', 'name'], 'sort desc')['data']->toArray();
        return dataReturn(0, 'success', makeTree($menu));
    }
}