<?php

namespace app\admin\validate;

use think\Validate;

class OrderValidate extends Validate
{
    protected $rule = [
        'express_no|物流公司编码' => 'require',
        'express_name|物流公司名称' => 'require'
    ];

    protected $scene = [
        'edit' => ['express_no', 'express_name']
    ];
}