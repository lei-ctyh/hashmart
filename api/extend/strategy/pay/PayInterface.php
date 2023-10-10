<?php

namespace strategy\pay;

interface PayInterface
{
    /**
     * 小程序内支付
     * @param $param
     * @return mixed
     */
    public function miniPay($param);

    /**
     * app内支付
     * @param $param
     * @return mixed
     */
    public function appPay($param);

    /**
     * 退款
     * @param $param
     * @return mixed
     */
    public function refund($param);

    /**
     * 获取基础配置
     * @return mixed
     */
    public function getConfig();
}