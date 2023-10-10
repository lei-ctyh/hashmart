<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\SystemService;
use think\App;

class System extends Base
{
    /**
     * 存储配置
     */
    public function store()
    {
        if (request()->isPost()) {
            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }
        
        return jsonReturn(0, 'success', [
            'store' => getConfByType('store'),
            'aliyun' => getConfByType('store_oss'),
            'qiniu' => getConfByType('store_qiniu'),
            'qcloud' => getConfByType('store_tencent'),
        ]);
    }

    /**
     * 基础配置
     */
    public function base()
    {
        if (request()->isPost()) {

            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }

        return jsonReturn(0, 'success', getConfByType('sys_base'));
    }
}