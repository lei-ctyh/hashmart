<?php

namespace strategy\order;

use strategy\order\impl\BalanceOrder;
use strategy\order\impl\BlindboxOrder;
use strategy\order\impl\ExpressOrder;

class OrderProvider
{
    protected $strategy;

    public function __construct($orderNo)
    {
        $prefix = substr($orderNo, 0, 1);
        switch ($prefix) {
            case 'B':
                $this->strategy = new BlindboxOrder();
                break;
            case 'E':
                $this->strategy = new ExpressOrder();
                break;
            case 'R':
                $this->strategy = new BalanceOrder();
                break;
        }
    }

    public function getStrategy()
    {
        return $this->strategy;
    }
}