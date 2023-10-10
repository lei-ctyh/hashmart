<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\SystemService;

class MessageSetting extends Base
{
    /**
     * 获取 保存 短信配置
     */
    public function messageConfig()
    {
        if (request()->isPost()) {
            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }

        return jsonReturn(0, 'success', [
            'sms' => getConfByType('sms')
        ]);
    }
}