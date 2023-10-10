<?php

namespace app\admin\validate;

use think\Validate;

class SliderSettingValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'title|轮播描述' => 'require',
        'pic|导航图片' => 'require',
        'status|状态' => 'require',
    ];

    protected $scene = [
        'add' => ['title', 'pic', 'status']
    ];
}