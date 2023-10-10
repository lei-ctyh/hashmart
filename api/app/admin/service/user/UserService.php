<?php

namespace app\admin\service\user;

use app\admin\validate\UserValidate;
use app\model\user\User;
use app\model\user\UserBalanceChangeLog;
use app\model\user\UserIntegralChangeLog;
use think\exception\ValidateException;

class UserService
{
    /**
     * 获取会员列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['nickname'])) {
            $where[] = ['nickname', '=', $param['nickname']];
        }

        if (!empty($param['user_id'])) {
            $where[] = ['id', '=', $param['user_id']];
        }

        if (!empty($param['create_time'])) {
            $where[] = ['create_time', 'between', [$param['create_time'][0] . ' 00:00:00', $param['create_time'][1] . ' 23:59:59']];
        }

        if (!empty($param['source_type'])) {
            $where[] = ['source_type', '=', $param['source_type']];
        }

        if (!empty($param['phone'])) {
            $where[] = ['phone', '=', $param['phone']];
        }

        try {
            $userModel = new User();
            $list = $userModel->where($where)->order('id desc')->paginate($param['limit']);

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 编辑会员
     * @param $param
     * @return array
     */
    public function editUser($param): array
    {
        try {
            validate(UserValidate::class)->scene('edit')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }
        $param['update_time'] = date('Y-m-d H:i:s');

        $userModel = new User();
        $res = $userModel->updateById($param, $param['id']);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '编辑成功');
    }

    /**
     * Notes: 添加会员
     * Author: LJS
     * @param $param
     * @return array
     */
    public function addUser($param): array
    {
        try {
            validate(UserValidate::class)->scene('add')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }
        $param['create_time'] = date('Y-m-d H:i:s');
        $param['update_time'] = date('Y-m-d H:i:s');
        $param['avatar'] = getRandAvtar();

        $userModel = new User();
        $salt = config('shop.salt');
        $param['password'] = makePassword($param['password'], $salt);
        $param['source_type'] = 2; //后台

        return $userModel->insertOne($param);
    }

    /**
     * 更改哈希币
     * @param $param
     * @return array
     */
    public function changeIntegral($param)
    {
        if (empty($param['integral']) || $param['integral'] < 0) {
            return dataReturn(-1, '变动的哈希币必须大于0');
        }

        if ($param['type'] == 2 && $param['integral'] > $param['originalIntegral']) {
            return dataReturn(-2, '该用户没有这么多哈希币可以扣');
        }

        (new UserIntegralChangeLog())->insertOne([
            'user_id' => $param['id'],
            'username' => $param['name'],
            'integral' => ($param['type'] == 1) ? $param['integral'] : 0 - $param['integral'],
            'type' => ($param['type'] == 1) ? 3 : 4,
            'create_time' => now()
        ]);

        $userModel = new User();
        if ($param['type'] == 1) {
            return $userModel->incData(['id' => $param['id']], 'integral', $param['integral']);
        } else {
            return $userModel->decData(['id' => $param['id']], 'integral', $param['integral']);
        }
    }

    /**
     * 更改余额
     * @param $param
     * @return array
     */
    public function changeBalance($param)
    {
        if (empty($param['balance']) || $param['balance'] < 0) {
            return dataReturn(-1, '变动的余额必须大于0');
        }

        if ($param['type'] == 2 && $param['balance'] > $param['originalBalance']) {
            return dataReturn(-2, '该用户没有这么多余额可以扣');
        }

        (new UserBalanceChangeLog())->insertOne([
            'user_id' => $param['id'],
            'username' => $param['name'],
            'balance' => ($param['type'] == 1) ? $param['balance'] : 0 - $param['balance'],
            'type' => ($param['type'] == 1) ? 3 : 4,
            'create_time' => now()
        ]);

        $userModel = new User();
        if ($param['type'] == 1) {
            return $userModel->incData(['id' => $param['id']], 'balance', $param['balance']);
        } else {
            return $userModel->decData(['id' => $param['id']], 'balance', $param['balance']);
        }
    }
}