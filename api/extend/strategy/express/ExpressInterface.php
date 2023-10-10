<?php

namespace strategy\express;

interface ExpressInterface
{
    /**
     * 订单查询
     */
    public function search($param);
}