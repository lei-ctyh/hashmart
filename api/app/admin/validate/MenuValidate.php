<?php

namespace app\admin\validate;

use think\Validate;

class MenuValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'pid|上级菜单' => 'require',
        'name|菜单名' => 'require|max:55',
    ];

    protected $scene = [
        'add' => ['pid', 'name']
    ];
}