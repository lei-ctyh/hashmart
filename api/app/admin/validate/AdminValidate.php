<?php

namespace app\admin\validate;

use think\Validate;

class AdminValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'role_id|角色' => 'require|number',
        'username|登录账号' => 'require|alphaNum|max:55',
        'password|密码' => 'require|max:16'
    ];

    protected $scene = [
        'edit' => ['id', 'role_id', 'username'],
        'add' => ['role_id', 'username', 'password']
    ];
}