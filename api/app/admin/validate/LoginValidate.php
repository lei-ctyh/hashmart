<?php

namespace app\admin\validate;

use think\Validate;

class LoginValidate extends Validate
{
    protected $rule = [
        'username|登录账号' => 'require|max:55',
        'password|密码' => 'require|max:32',
        'captcha|验证码' => 'require'
    ];
}