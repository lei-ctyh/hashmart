<?php

namespace app\admin\service\rule;

use app\admin\validate\AdminValidate;
use app\model\system\SysAdmin;
use think\exception\ValidateException;

class AdminService
{
    /**
     * 获取管理员列表
     * @param $param
     * @return array
     */
    public function getList($param)
    {
        $where = [];
        if (!empty($param['username'])) {
            $where[] = ['username', '=', $param['username']];
        }

        try {
            $adminModel = new SysAdmin();
            $list = $adminModel->with('role')->where($where)->order('id desc')->paginate($param['limit']);

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 添加管理员
     * @param $param
     * @return array
     */
    public function addAdmin($param)
    {
        try {
            validate(AdminValidate::class)->scene('add')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        // 检查唯一
        $adminModel = new SysAdmin();
        $has = $adminModel->checkUnique(['username' => $param['username']])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该登录名已存在');
        }

        $param['create_time'] = date('Y-m-d H:i:s');
        $param['salt'] = uniqid();
        $param['password'] = makePassword($param['password'], $param['salt']);

        $res = $adminModel->insertOne($param);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '添加成功');
    }

    /**
     * 编辑管理员
     * @param $param
     * @return array
     */
    public function editAdmin($param)
    {
        try {
            validate(AdminValidate::class)->scene('edit')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        // 检查唯一
        $adminModel = new SysAdmin();
        $has = $adminModel->checkUnique(['username' => $param['username']], $param['id'])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该登录名已存在');
        }

        $param['update_time'] = date('Y-m-d H:i:s');

        if (!empty($param['password'])) {
            $param['salt'] = uniqid();
            $param['password'] = makePassword($param['password'], $param['salt']);
        } else {
            unset($param['password']);
        }

        $res = $adminModel->updateById($param, $param['id']);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '编辑成功');
    }

    /**
     * 删除管理员
     * @param $id
     * @return array
     */
    public function delAdmin($id)
    {
        if ($id == 0) {
            return dataReturn(-1, '不可以删除超级管理员');
        }

        $adminModel = new SysAdmin();
        return $adminModel->delById($id);
    }
}