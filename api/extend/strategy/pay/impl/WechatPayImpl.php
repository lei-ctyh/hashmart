<?php

namespace strategy\pay\impl;

use app\model\user\User;
use strategy\pay\PayInterface;
use Yansongda\Pay\Pay;

class WechatPayImpl implements PayInterface
{
    private $config = [];

    public function __construct()
    {
        $formatConfig = getConfByType('wechat_pay');

        $this->config = [
            'app_id' => $formatConfig['wechat_pay_app_id'],
            'mch_id' => $formatConfig['wechat_pay_mchid'],
            'miniapp_id' => $formatConfig['wechat_miniapp_id'],
            'key' => $formatConfig['wechat_pay_key'],
            'notify_url' => '/order/notify/wechat',
            'cert_client' => $formatConfig['wechat_pay_cert'],
            'cert_key' => $formatConfig['wechat_pay_pem'],
            'log' => [
                'file' => './logs/wechat.log',
                'level' => 'info',
                'type' => 'single',
                'max_file' => 30,
            ],
            'http' => [
                'timeout' => 5.0,
                'connect_timeout' => 5.0
            ],
            //'mode' => 'dev'
        ];

        if (!empty($formatConfig['wechat_pay_appid'])) {
            $this->config['appid'] = $formatConfig['wechat_pay_appid'];
        }
    }

    public function miniPay($param)
    {
        $userModel = new User();
        $userInfo = $userModel->findOne(['id' => $param['user_id']])['data'];
        $this->config['notify_url'] = $param['host'] . $this->config['notify_url'];

        return Pay::wechat($this->config)->miniapp([
            'out_trade_no' => $param['pay_order_no'],
            'total_fee' => $param['pay_price'] * 100,
            'body' => mb_substr($param['subject'], 0, 30),
            'openid' => $userInfo['openid'],
        ]);
    }

    public function appPay($param)
    {
        $this->config['notify_url'] = $param['host'] . $this->config['notify_url'];
        return Pay::wechat($this->config)->app([
            'out_trade_no' => $param['pay_order_no'],
            'total_fee' => $param['pay_price'] * 100,
            'body' => mb_substr($param['subject'], 0, 30),
        ]);
    }

    public function refund($param)
    {
        $order = [
            'out_trade_no' => $param['order_no'],
            'out_refund_no' => $param['refund_order_no'],
            'total_fee' => $param['pay_price'] * 100,
            'refund_fee' => $param['refund_price'] * 100,
            'refund_desc' => $param['order_no'] . '退款',
        ];

        $result = Pay::wechat($this->config)->refund($order);
        return dataReturn(0, 'success', $result);
    }

    public function getConfig()
    {
        return $this->config;
    }
}