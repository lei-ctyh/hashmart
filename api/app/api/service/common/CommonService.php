<?php

namespace app\api\service\common;

use app\api\validate\common\SmsValidate;
use strategy\sms\SmsProvider;
use think\facade\Cache;

class CommonService
{
    /**
     * 处理发短信
     * @param $param
     * @return array|mixed
     */
    public function doSendSms($param)
    {
        $smsValidate = new SmsValidate();
        if (!$smsValidate->check($param)) {
            return dataReturn(-1, $smsValidate->getError());
        }

        // 频率限制
        $code = Cache::get($param['phone'] . '_' . $param['type']);
        if (!empty($code)) {
            return dataReturn(-2, '上条验证码尚未过期，请勿重复发送');
        }

        $info = getConfByType('sms');

        $sms = [
            'access_key_id' => $info['access_key_id'],
            'access_key_secret' => $info['access_key_secret'],
            'sign_name' => $info['sign_name'],
            'templateCode' => $info[$param['type']],
            'phone' => $param['phone']
        ];

        $smsProvider = new SmsProvider('ali');
        $sendParam = formatSmsData($sms);
        $res = $smsProvider->getStrategy()->send($sendParam);

        if ($res['code'] == 0) {
            // 记录5分钟
            Cache::set($param['phone'] . '_' . $param['type'], json_decode($sendParam['code'], true)['code'], 300);
        }

        return $res;
    }
}