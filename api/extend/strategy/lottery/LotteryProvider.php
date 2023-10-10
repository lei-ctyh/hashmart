<?php

namespace strategy\lottery;

use strategy\lottery\impl\HashLotteryImpl;

class LotteryProvider
{
    protected $strategy;

    public function __construct($playId)
    {
        switch ($playId) {
            case 1:
                $this->strategy = new HashLotteryImpl();
                break;
        }
    }

    public function getStrategy()
    {
        return $this->strategy;
    }
}
