<?php

namespace app\api\validate\order;

use think\Validate;

class DeliverValidate extends Validate
{
    protected $rule = [
        'box_id' => 'require',
        'address_id' => 'require|number'
    ];

    protected $message = [
        'box_id.require' => '奖品盒子ID不能为空',
        'address_id.require' => '提货地址不能为空'
    ];
}