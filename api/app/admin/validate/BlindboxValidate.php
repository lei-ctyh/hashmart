<?php

namespace app\admin\validate;

use think\Validate;

class BlindboxValidate extends Validate
{
    protected $rule = [
        'name|盲盒名称' => 'require|max:125',
        'pic|封面' => 'require',
        'price|单抽价格' => 'require|min:0'
    ];
}