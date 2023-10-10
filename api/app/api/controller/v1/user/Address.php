<?php

namespace app\api\controller\v1\user;

use app\api\controller\Base;
use app\api\service\user\AddressService;

class Address extends Base
{
    protected $userInfo;

    public function initialize()
    {
        parent::initialize();
        $this->userInfo = getJWT(getHeaderToken());
    }

    /**
     * 省市区数据联动
     */
    public function option()
    {
        $addressService = new AddressService();
        return json($addressService->getOption());
    }

    /**
     * 获取用户收获地址列表
     */
    public function list()
    {
        $addressService = new AddressService();
        return json($addressService->getList($this->userInfo));
    }

    /**
     * 编辑用户收货地址
     */
    public function edit()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;
        $addressService = new AddressService();
        return json($addressService->edit($postParam));
    }

    /**
     * 添加用户收货地址
     */
    public function add()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;
        $addressService = new AddressService();
        return json($addressService->add($postParam));
    }

    /**
     * 收货地址设置默认值
     */
    public function setDefault()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;
        $addressService = new AddressService();
        return json($addressService->setDefault($postParam));
    }

    /**
     * 删除收货地址
     */
    public function delete()
    {
        $postParam = request()->post();
        $postParam['user_info'] = $this->userInfo;
        $addressService = new AddressService();
        return json($addressService->delete($postParam));
    }
}