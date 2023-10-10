<?php

namespace app\api\controller\v1\user;

use app\api\service\user\LoginService;
use app\BaseController;

class Login extends BaseController
{
    /**
     * 微信授权登录
     */
    public function loginByWechat()
    {
        $loginService = new LoginService();
        return json($loginService->doLoginByWechat(\request()->post()));
    }

    /**
     * 账号密码登录
     */
    public function loginByAccount()
    {
        $loginService = new LoginService();
        return json($loginService->doLoginByAccount(\request()->post()));
    }

    /**
     * 短信登录
     */
    public function loginBySms()
    {
        $loginService = new LoginService();
        return json($loginService->doLoginBySms(\request()->post()));
    }

    /**
     * 手机号一键登录
     */
    public function loginByPhone()
    {
        $loginService = new LoginService();
        return json($loginService->doLoginByPhone(\request()->post()));
    }

    /**
     * 忘记密码
     */
    public function forget()
    {
        $loginService = new LoginService();
        return json($loginService->forgetPassword(\request()->post()));
    }

    /**
     * 获取用户手机号
     */
    public function getUserPhone()
    {
        $loginService = new LoginService();
        return json($loginService->getUserPhone(\request()->post()));
    }

    /**
     * 获取uniapp信息
     */
    public function getUniapp()
    {
        $loginService = new LoginService();
        return json($loginService->getUniapp());
    }
}