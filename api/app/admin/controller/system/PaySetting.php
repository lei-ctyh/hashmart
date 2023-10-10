<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\PaySettingService;
use app\admin\service\system\SystemService;

class PaySetting extends Base
{
    /**
     * 获取 保存 支付配置
     */
    public function payConfig()
    {
        if (request()->isPost()) {
            $param = input('post.');

            $systemService = new SystemService();
            $systemService->saveSystem($param);
            return jsonReturn(0, '保存成功');
        }

        return jsonReturn(0, 'success', [
            'wechat_pay' => getConfByType('wechat_pay'),
            'alipay' => getConfByType('alipay'),
            'api_url' => getConfByType('api_url')
        ]);
    }

    /**
     * 上传配置文件
     */
    public function uploadTxtFile()
    {
        $file = request()->file('file');
        $uploadService = new PaySettingService();
        return json($uploadService->uploadTxtFile($file));
    }
}