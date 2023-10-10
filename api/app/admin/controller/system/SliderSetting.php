<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\SliderSettingService;

class SliderSetting extends Base
{
    /**
     * 轮播配置列表
     */
    public function index()
    {
        $RechargeLogService = new SliderSettingService();
        return json($RechargeLogService->getList(input('param.')));
    }

    /**
     * 添加菜单
     */
    public function add()
    {
        $SliderSettingService = new SliderSettingService();
        return json($SliderSettingService->addSliderSetting(input('post.')));
    }

    /**
     * 编辑
     */
    public function edit()
    {
        $SliderSettingService = new SliderSettingService();
        return json($SliderSettingService->editSliderSetting(input('post.')));
    }

    /**
     * 删除
     */
    public function del()
    {
        $SliderSettingService = new SliderSettingService();
        return json($SliderSettingService->delSliderSetting(input('param.id')));
    }
}