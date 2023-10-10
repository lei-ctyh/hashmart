<?php

namespace strategy\store;

use strategy\store\impl\AliyunImpl;
use strategy\store\impl\LocalImpl;
use strategy\store\impl\QCloudImpl;
use strategy\store\impl\QiniuImpl;

class StoreProvider
{
    protected $strategy = null;

    public function __construct($type, $config)
    {
        if ($type == 'aliyun') {
            $this->strategy = new AliyunImpl($config);
        } else if ($type == 'qiniu') {
            $this->strategy = new QiniuImpl($config);
        } else if ($type == 'qcloud') {
            $this->strategy = new QCloudImpl($config);
        } else if ($type == 'local') {
            $this->strategy = new LocalImpl($config);
        }
    }

    public function getStrategy()
    {
        return $this->strategy;
    }
}