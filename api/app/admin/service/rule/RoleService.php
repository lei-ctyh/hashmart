<?php

namespace app\admin\service\rule;

use app\admin\validate\RoleValidate;
use app\model\system\SysRole;
use think\exception\ValidateException;

class RoleService
{
    /**
     * Notes: 获取角色列表
     * Author: LJS
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['name'])) {
            $where[] = ['name', '=', $param['name']];
        }
        $where[] = ['id', '>', 1];

        try {
            $roleModel = new SysRole();
            $list = $roleModel->where($where)->order('id desc')->paginate($param['limit']);
            if (! empty($list)) {
                foreach ($list as &$item) {
                    $item['rule'] = explode(",", $item['rule']);
                }
            }

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 获取角色列表
     * @return array
     */
    public function getAllRoleList(): array
    {
        $sysRoleModel = new SysRole();
        return $sysRoleModel->getAllList([['id', '>', 1]]);
    }

    /**
     * 添加角色
     * @param $param
     * @return array
     */
    public function addRole($param): array
    {
        try {
            validate(RoleValidate::class)->scene('add')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        // 检查唯一
        $roleModel = new SysRole();
        $has = $roleModel->checkUnique(['name' => $param['name']])['data'];
        if (! empty($has)) {
            return dataReturn(-2, '该角色名已存在');
        }
        $param['create_time'] = date('Y-m-d H:i:s');
        $param['rule'] = join(",", $param['rule']);

        $res = $roleModel->insertOne($param);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '添加成功');
    }

    /**
     * Notes: 编辑角色
     * Author: LJS
     * @param $param
     * @return array
     */
    public function editRole($param): array
    {
        try {
            validate(RoleValidate::class)->scene('edit')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }
        // 检查唯一
        $roleModel = new SysRole();
        $param['update_time'] = date('Y-m-d H:i:s');
        $param['rule'] = join(",", $param['rule']);

        $res = $roleModel->updateById($param, $param['id']);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '编辑成功');
    }

    /**
     * Notes: 删除角色
     * Author: LJS
     * @param $id
     * @return array
     */
    public function delRole($id): array
    {
        if ($id == 1) {
            return dataReturn(-1, '不可以删除超级管理员角色');
        }

        $roleModel = new SysRole();
        return $roleModel->delById($id);
    }
}