<?php

namespace app\admin\validate;

use think\Validate;

class UserBoxDeliverValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'express_name|物流公司' => 'require|max:155',
        'express_no|物流单号' => 'require|max:155',
    ];

    protected $scene = [
        'deliver' => ['id', 'express_name', 'express_no'],
    ];
}