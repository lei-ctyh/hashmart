<?php

namespace strategy\order;

interface OrderInterface
{
    /**
     * 处理订单
     * @param $param
     * @return mixed
     */
    public function dealOrder($param);
}