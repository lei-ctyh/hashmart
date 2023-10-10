<?php

namespace app\api\validate\user;

use think\Validate;

class LoginValidate extends Validate
{
    protected $rule = [
        'phone' => 'require|mobile',
        'password' => 'require|max:55',
        'code' => 'require|max:6',
        'access_token' => 'require',
        'openid' => 'require'
    ];

    protected $message  =   [
        'phone.require' => '手机号不能为空',
        'password.require' => '密码不能为空',
        'password.max' => '长度不得超过55个字符',
        'code.require' => '验证码不能为空',
        'code.max' => '长度不得超过6位',
    ];

    protected $scene = [
        'account' => ['phone', 'password'],
        'code' => ['phone', 'code'],
        'phone' => ['access_token', 'openid']
    ];
}