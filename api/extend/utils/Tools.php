<?php

namespace utils;

use app\model\system\SysSetting;
use strategy\sms\SmsProvider;
use strategy\store\StoreProvider;

class Tools
{
    public static function sendSms($param)
    {
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
            cache($param['phone'] . '_' . $param['type'], json_decode($sendParam['code'], true)['code'], 300);
        }

        return $res;
    }

    public static function getPayWay()
    {
        // 支付方式开启情况
        $sysSettingModel = new SysSetting();
        $payWayMap = $sysSettingModel->getOpenWay()['data'];
        $payWay = '';
        if (isset($payWayMap['wechat_pay']) && $payWayMap['wechat_pay'] == 1) {
            $payWay = 'wechat_pay';
        } else if (isset($payWayMap['alipay']) && $payWayMap['alipay'] == 1) {
            $payWay = 'alipay';
        }

        return compact('payWayMap', 'payWay');
    }

    /**
     * 第三方存储
     * @param $storeWay
     * @param $file
     * @param $saveName
     * @return string
     */
    public static function storeOSS($storeWay, $saveName)
    {
        $storeConfigMap = config('shop.store_config');
        $config = getConfByType($storeConfigMap[$storeWay]);
        $provider = new StoreProvider($storeWay, $config);
        $path = app()->getRootPath() . 'public/storage/' . $saveName;

        $provider->getStrategy()->upload($path, $saveName);
        unlink($path);
        removeEmptyDir(dirname($path));
        $ossDomain = $config[config('shop.store_domain')[$storeWay]];
        if (strstr($ossDomain, 'http://') == false && strstr($ossDomain, 'https://') == false) {
            $ossDomain = 'https://' . $ossDomain;
        }

        return $ossDomain . '/' . $saveName;
    }
}
