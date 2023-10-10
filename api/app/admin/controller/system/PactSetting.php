<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\PactSettingService;

class PactSetting extends Base
{
    /**
     * 获取 保存 协议配置
     */
    public function pactConfig()
    {
        if (request()->isPost()) {
            $PactSettingService = new PactSettingService();
            return json($PactSettingService->save(input('post.')));
        }

        $PactSettingService = new PactSettingService();
        return json($PactSettingService->getPactConfig(input('get.')));
    }
}