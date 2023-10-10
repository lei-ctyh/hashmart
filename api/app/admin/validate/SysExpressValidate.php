<?php

namespace app\admin\validate;

use think\Validate;

class SysExpressValidate extends Validate
{
    protected $rule = [
        'name|物流公司名称' => 'require',
        'code|物流公司编号' => 'require',
        'status|状态' => 'require',
    ];

    protected $scene = [
        'add' => ['name','code', 'status']
    ];
}