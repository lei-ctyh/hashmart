<?php

namespace strategy\pay\impl;

use strategy\pay\PayInterface;
use Yansongda\Pay\Pay;

class AlipayImpl implements PayInterface
{
    private $config = [];

    private $status = [
        'WAIT_BUYER_PAY' => '等待付款',
        'TRADE_FINISHED' => '交易完成',
        'TRADE_SUCCESS' => '交易完成',
        'TRADE_CLOSED' => '交易关闭'
    ];

    public function __construct()
    {
        $formatConfig = getConfByType('alipay');

        $this->config = [
            'app_id' => $formatConfig['alipay_app_id'],
            'notify_url' => '/order/notify/alipay',
            'ali_public_key' => $formatConfig['alipay_public_key'],
            'private_key' => $formatConfig['alipay_private_key'],
            'log' => [
                'file' => './logs/alipay.log',
                'level' => 'info',
                'type' => 'single',
                'max_file' => 30,
            ],
            'http' => [
                'timeout' => 5.0,
                'connect_timeout' => 5.0
            ],
            // 'mode' => 'dev'
        ];
    }

    public function miniPay($param)
    {
        return dataReturn(-1, '不支持');
    }

    public function appPay($param)
    {
        $this->config['notify_url'] = $param['host'] . $this->config['notify_url'];
        return Pay::alipay($this->config)->app([
            'out_trade_no' => $param['pay_order_no'],
            'total_amount' => $param['pay_price'],
            'subject' => mb_substr($param['subject'], 0, 30),
        ])->getContent();
    }

    public function refund($param)
    {
        $order = [
            'out_trade_no' => $param['order_no'],
            'refund_amount' => $param['refund_price']
        ];

        $result = Pay::alipay($this->config)->refund($order);
        return dataReturn(0, '退款成功', $result);
    }

    public function getConfig()
    {
        return $this->config;
    }
}