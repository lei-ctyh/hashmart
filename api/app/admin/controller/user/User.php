<?php

namespace app\admin\controller\user;

use app\admin\controller\Base;
use app\admin\service\user\UserService;

class User extends Base
{
    /**
     * 会员列表
     */
    public function index()
    {
        $userService = new UserService();
        return json($userService->getList(input('param.')));
    }

    /**
     * 编辑会员
     */
    public function edit()
    {
        $userService = new UserService();
        return json($userService->editUser(input('post.')));
    }

    /**
     * 添加会员
     */
    public function add()
    {
        $userService = new UserService();
        return json($userService->addUser(input('post.')));
    }

    /**
     * 更改哈希币
     */
    public function integral()
    {
        $userService = new UserService();
        return json($userService->changeIntegral(input('post.')));
    }

    /**
     * 更改余额
     */
    public function balance()
    {
        $userService = new UserService();
        return json($userService->changeBalance(input('post.')));
    }
}