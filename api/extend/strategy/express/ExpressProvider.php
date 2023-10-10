<?php

namespace strategy\express;

use strategy\express\impl\AliyunImpl;

class ExpressProvider
{
    protected $strategy;

    public function __construct($type)
    {
        if ($type == 'aliyun') {
            $this->strategy = new AliyunImpl();
        }
    }

    public function getStrategy()
    {
        return $this->strategy;
    }
}