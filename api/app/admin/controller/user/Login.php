<?php

namespace app\admin\controller\user;

use app\admin\service\user\LoginService;
use app\BaseController;
use utils\Captcha;

class Login extends BaseController
{
    public function login()
    {
        $loginService = new LoginService();
        return json($loginService->doLogin(input('post.')));
    }

    /**
     * 验证码
     */
    public function captcha()
    {
        $captcha = new Captcha();
        return jsonReturn(0, 'success', $captcha->create());
    }
}