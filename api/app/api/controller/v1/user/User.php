<?php

namespace app\api\controller\v1\user;

use app\api\controller\Base;
use app\api\service\user\UserService;

class User extends Base
{
    protected $userInfo;

    public function initialize()
    {
        parent::initialize();
        $this->userInfo = getJWT(getHeaderToken());
    }

    /**
     * 获取用户信息
     */
    public function getUserInfo()
    {
        $userService = new UserService();
        return json($userService->getUserInfo());
    }

    /**
     * 设置用户信息
     */
    public function setUserInfo()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;
        $userService = new UserService();
        return json($userService->setUserInfo($postParam));
    }

    /**
     * 用户开盒记录
     */
    public function orderRecordLog()
    {
        $userService = new UserService();
        $param = \request()->get();
        $param['user_id'] = $this->userInfo['id'] ?? '';
        return json($userService->orderRecordLog($param));
    }

    /**
     * 我的订单列表
     */
    public function orderList()
    {
        $userService = new UserService();
        $param = \request()->get();
        $param['user_id'] = $this->userInfo['id'] ?? '';
        return json($userService->orderList($param));
    }

    /**
     * 更新token
     */
    public function updateToken()
    {
        $userService = new UserService();
        $token = input('post.token');

        return json($userService->updateToken($token));
    }
}