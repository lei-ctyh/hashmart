<?php

namespace app\admin\validate;

use think\Validate;

class UserValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'nickname|昵称' => 'require|max:155',
        'phone|用户手机号' => 'require|max:11',
        'avatar|用户头像' => 'require|max:155',
        'integral|哈希币' => 'require|number',
        'balance|余额' => 'require',
        'status|状态' => 'require|in:1,2'
    ];

    protected $scene = [
        'edit' => ['id', 'nickname', 'status'],
        'add' => ['phone', 'password'],
    ];

}