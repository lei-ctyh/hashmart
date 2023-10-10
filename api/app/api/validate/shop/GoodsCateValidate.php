<?php

namespace app\api\validate\shop;

use think\Validate;

class GoodsCateValidate extends Validate
{
    protected $rule = [
        'page' => 'require|number',
        'cate_id' => 'require|number',
        'limit' => 'require|number',
    ];

    protected $message  =   [
        'page.require' => '分页不能为空',
        'cate_id.require' => '分类id不能为空',
        'limit.require' => '分页条数不能为空'
    ];
}