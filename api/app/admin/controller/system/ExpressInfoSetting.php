<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\SystemService;

class ExpressInfoSetting extends Base
{
    /**
     * 获取 保存 物流配置
     */
    public function Config()
    {
        if (request()->isPost()) {
            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }

        return jsonReturn(0, 'success', [
            'express' => getConfByType('express'),
        ]);
    }
}