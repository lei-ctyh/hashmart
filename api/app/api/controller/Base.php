<?php

namespace app\api\controller;

use app\BaseController;

class Base extends BaseController
{
    public function initialize()
    {
        // 校验用户的权限
        $userInfo = getJWT(getHeaderToken());
        if (empty($userInfo)) {
            exit(json_encode(dataReturn(401, '登录过期，请重新登录')));
        }
    }
}