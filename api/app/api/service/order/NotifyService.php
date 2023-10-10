<?php

namespace app\api\service\order;

use strategy\order\OrderProvider;
use strategy\pay\PayProvider;
use Yansongda\Pay\Pay;

class NotifyService
{
    /**
     * 支付宝回调
     */
    public function alipayNotify()
    {
        $payProvider = new PayProvider(2);
        $config = $payProvider->getStrategy()->getConfig();
        $alipay = Pay::alipay($config);

        try {

            $result = $alipay->verify();
            file_put_contents('./ali_notify.log', "支付宝支付回调: " . json_encode($result) . PHP_EOL, 8);
            $this->notify(['result' => $result, 'way' => 2]);
        } catch (\Exception $e) {
            file_put_contents('./ali_notify.log', '支付宝回调异常' . '>>' . $e->getMessage() . '>>' . $e->getTraceAsString() . PHP_EOL, 8);
        }

        return $alipay->success()->send();
    }

    /**
     * 微信回调
     */
    public function wechatNotify()
    {
        $payProvider = new PayProvider(1);
        $config = $payProvider->getStrategy()->getConfig();
        $wechat = Pay::wechat($config);

        try {

            $result = $wechat->verify();
            $this->notify(['result' => $result, 'way' => 1]);
        } catch (\Exception $e) {
            file_put_contents('./wechat_notify.log', '微信回调异常' . '>>' . $e->getMessage() . '>>' . $e->getTraceAsString() . PHP_EOL, 8);
        }

        return $wechat->success()->send();
    }

    /**
     * 统一支付回调
     * @param $param
     * @return array
     */
    public function notify($param)
    {
        $data = $param['result'];

        $orderProvider = new OrderProvider($data->out_trade_no);
        $orderProvider->getStrategy()->dealOrder([
            'data' => $data,
            'way' => $param['way']
        ]);

        return dataReturn(0);
    }
}