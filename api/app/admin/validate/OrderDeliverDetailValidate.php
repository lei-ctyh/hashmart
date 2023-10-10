<?php

namespace app\admin\validate;

use think\Validate;

class OrderDeliverDetailValidate extends Validate
{
    protected $rule = [
        'order_id|订单iD' => 'require',
        'user_id|用户iD' => 'require'
    ];

    protected $scene = [
        'add' => ['order_id', 'user_id']
    ];
}