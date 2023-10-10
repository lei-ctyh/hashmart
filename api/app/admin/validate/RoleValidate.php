<?php

namespace app\admin\validate;

use think\Validate;

class RoleValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'name|角色名' => 'require|max:155',
        'rule|拥有的权限' => 'require',
        'status|状态' => 'require|number'
    ];

    protected $scene = [
        'edit' => ['id', 'rule', 'name', 'status'],
        'add' => ['rule', 'name', 'status']
    ];
}