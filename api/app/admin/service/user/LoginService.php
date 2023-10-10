<?php

namespace app\admin\service\user;

use app\admin\service\log\AdminLogService;
use app\admin\service\rule\MenuService;
use app\admin\validate\LoginValidate;
use app\model\system\SysAdmin;
use app\model\system\SysRole;
use think\exception\ValidateException;
use utils\Captcha;

class LoginService
{
    /**
     * 管理员登录
     * @param $param
     * @return array
     */
    public function doLogin($param)
    {
        try {

            validate(LoginValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        // 验证码
        $captcha = new Captcha();
        if (!$captcha->check($param['captcha'], $param['key'])) {
            return dataReturn(-5, '验证码错误');
        }

        $adminModel = new SysAdmin();
        $info = $adminModel->findOne(['username' => $param['username']])['data'];
        if (empty($info)) {
            return dataReturn(-2, '用户名密码错误');
        }

        if (makePassword($param['password'], $info['salt']) != $info['password']) {
            return dataReturn(-3, '用户名密码错误');
        }

        if ($info['status'] == 2) {
            return dataReturn(-4, '该账号已被禁用');
        }

        if ($info['role_id'] != 1) {
            $roleModel = new SysRole();
            $roleInfo = $roleModel->findOne([
                ['id', '=', $info['role_id']],
                ['status', '=', 1]
            ])['data'];

            if (empty($roleInfo)) {
                return dataReturn(-5, '该账号已被禁用');
            }
        }

        $token = setJWT([
            'id' => $info['id'],
            'role_id' => $info['role_id'],
            'nickname' => $info['nickname'],
            'avatar' => $info['avatar']
        ]);

        // 获取权限菜单
        $menuService = new MenuService();
        $menu = $menuService->getMyMenu($info['role_id'])['data'];
        if (!empty($menu)) {
            $menu = $menu->toArray();
        } else {
            $menu = [];
        }

        // 记录权限map后续校验用
        if ($info['id'] != 1) {
            $authMap = [];
            foreach ($menu as $vo) {
                if (!empty($vo['auth'])) {
                    $authMap[$vo['auth']] = 1;
                }
            }
            cache($info['id'] . '_auth_map', $authMap);
        }

        // 记录日志
        (new AdminLogService())->write([
            'admin_id' => $info['id'],
            'username' => $info['username'],
            'title' => '管理员登录'
        ], json_encode(input('param.')));

        $adminModel->updateById([
            'last_login_time' => date('Y-m-d H:i:s'),
            'last_login_ip' => request()->ip()
        ], $info['id']);

        return dataReturn(0, '登录成功', [
            'userInfo' => [
                'id' => $info['id'],
                'role_id' => $info['role_id'],
                'userName' => $info['nickname'],
                'avatar' => $info['avatar']
            ],
            'token' => $token,
            'menu' => makeTree($menu)
        ]);
    }
}