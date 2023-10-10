<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\CitySettingService;

class citySetting extends Base
{
    /**
     * 获取列表
     */
    public function list()
    {
        $CitySettingService = new CitySettingService();
        return json($CitySettingService->getList(input('get.')));
    }

    /**
     * 添加城市
     */
    public function add()
    {
        $CitySettingService = new CitySettingService();
        return json($CitySettingService->addCity(input('post.')));
    }

    /**
     * 编辑城市
     */
    public function edit()
    {
        $CitySettingService = new CitySettingService();
        return json($CitySettingService->editCity(input('post.')));
    }

    /**
     * 删除
     */
    public function del()
    {
        $CitySettingService = new CitySettingService();
        return json($CitySettingService->delCity(input('param.id')));
    }
}
