<?php

namespace app\admin\controller;

use app\BaseController;

class Base extends BaseController
{
    public function initialize()
    {
        // 校验用户的权限
        $userInfo = getJWT(getHeaderToken());
        if (empty($userInfo)) {
            exit(json_encode(dataReturn(401, '登录过期')));
        }

        $controller = request()->controller();
        $action = request()->action();
        $routePath = $controller . '/' . $action;

        // 特殊跳过权限
        $skipParam = array_merge(request()->get(), request()->post());

        $rbac = config('rbac');
        $skipAuth = $rbac['skip_rule'];

        if (!isset($skipAuth[$controller . '/*']) &&
            !isset($skipAuth[$routePath]) &&
            $userInfo['id'] != 1 && !isset($skipParam['hashmart_auth_skip'])) {
            // 读取权限节点对比
            $authMap = cache($userInfo['id'] . '_auth_map');
            if (!isset($authMap[$routePath])) {
                exit(json_encode(dataReturn(403, '您无权限')));
            }
        }
    }
}