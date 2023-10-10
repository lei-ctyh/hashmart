<?php

namespace app\admin\validate;

use think\Validate;

class GoodsCateValidate extends Validate
{
    protected $rule = [
        'name|分类名称' => 'require|max:155',
        'icon|图标' => 'require',
        'status|状态' => 'require|in:1,2'
    ];
}