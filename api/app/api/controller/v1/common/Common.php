<?php

namespace app\api\controller\v1\common;

use app\api\service\common\UserAgreementService;
use app\BaseController;
use app\Request;
use app\api\service\common\CommonService;

class Common extends BaseController
{
    /**
     * 发送短信
     */
    public function sendSms(Request $request)
    {
        $commonService = new CommonService();
        return json($commonService->doSendSms($request->post()));
    }

    /**
     * 获取头像
     */
    public function getavatar()
    {
        return jsonReturn(0, 'success', getRandAvtar(3));
    }

    /**
     * 获取音乐
     */
    public function getMusic()
    {
        $api = getConfByType('api_url')['api_url'];
        $api= rtrim($api, '/api');

        return jsonReturn(0, 'success', $api . '/static/voice/music.mp3');
    }

    /**
     * 获取用户协议
     */
    public function userAgreement()
    {
        $type = input('param.type');

        $userAgreementService = new UserAgreementService();
        return json($userAgreementService->getUserAgreement($type));
    }

    /**
     * 客服二维码
     */
    public function getKeFuCode()
    {
        return jsonReturn(0, 'success', getConfByType('sys_base')['kefu_wechat']);
    }
}