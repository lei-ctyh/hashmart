<?php

namespace app\admin\controller\user;

use app\admin\controller\Base;
use app\admin\service\system\SysCityService;
use app\admin\service\user\UserAddressService;

class UserAddress extends Base
{
    /**
     * 会员地址列表
     */
    public function index()
    {
        $userAddressService = new UserAddressService();
        return json($userAddressService->getList(input('param.')));
    }

    /**
     * 编辑会员地址
     */
    public function edit()
    {
        $userAddressService = new UserAddressService();
        return json($userAddressService->editUserAddress(input('post.')));
    }

    /**
     * 删除会员地址
     */
    public function del()
    {
        $userAddressService = new UserAddressService();
        return json($userAddressService->delUserAddress(input('param.id')));
    }

    /**
     * 获取省市区关联数据
     */
    public function areas()
    {
        $sysCityService = new SysCityService();
        $areas = $sysCityService->areas();
        return json($areas);
    }
}