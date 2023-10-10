<?php

namespace app\api\validate\common;

use think\Validate;

class SmsValidate extends Validate
{
    protected $rule = [
        'phone' => 'require',
        'type' => 'require'
    ];

    protected $message  =   [
        'phone.require' => '手机号不能为空',
        'type.require' => '类型不能为空'
    ];
}