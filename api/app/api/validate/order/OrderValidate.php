<?php

namespace app\api\validate\order;

use think\Validate;

class OrderValidate extends Validate
{
    protected $rule = [
        'blindbox_id' => 'require',
        'num' => 'require|number',
        'pay_way' => 'require',
        'use_integral' => 'require|number'
    ];

    protected $message = [
        'blindbox_id.require' => '盲盒id不能为空',
        'num.require' => '购买数量不能为空',
        'pay_way.require' => '支付方式不能为空',
        'use_integral.require' => '是否使用哈希币不能为空'
    ];
}