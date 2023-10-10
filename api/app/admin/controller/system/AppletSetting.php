<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\SystemService;

class AppletSetting extends Base
{
    /**
     * 获取 保存 支付配置
     */
    public function config()
    {
        if (request()->isPost()) {
            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }

        return jsonReturn(0, 'success', [
            'miniapp' => getConfByType('miniapp'),
        ]);
    }

    /**
     * uniapp 配置
     */
    public function uniapp()
    {
        if (request()->isPost()) {
            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }

        return jsonReturn(0, 'success', [
            'uniapp' => getConfByType('uniapp'),
        ]);
    }
}