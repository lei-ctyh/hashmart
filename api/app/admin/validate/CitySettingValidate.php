<?php

namespace app\admin\validate;

use think\Validate;

class CitySettingValidate extends Validate
{
    protected $rule = [
        'pid|上级' => 'require',
        'name|名称' => 'require',
        'is_show|是否展示' => 'require',
    ];
}