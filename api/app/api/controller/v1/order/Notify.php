<?php

namespace app\api\controller\v1\order;

use app\api\service\order\NotifyService;
use app\BaseController;

class Notify extends BaseController
{
    /**
     * 支付宝支付回调
     */
    public function alipay()
    {
        $notifyService = new NotifyService();
        return $notifyService->alipayNotify();
    }

    /**
     * 微信支付回调
     */
    public function wechat()
    {
        $notifyService = new NotifyService();
        return $notifyService->wechatNotify();
    }
}