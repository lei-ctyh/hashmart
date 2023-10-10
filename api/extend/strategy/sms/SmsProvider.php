<?php

namespace strategy\sms;

use strategy\sms\impl\AliSmsImpl;

class SmsProvider
{
    protected $strategy;

    public function __construct($type)
    {
        if ($type == 'ali') {
            $this->strategy = new AliSmsImpl();
        }
    }

    public function getStrategy()
    {
        return $this->strategy;
    }
}