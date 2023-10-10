<?php

namespace app\admin\validate;

use think\Validate;

class GoodsValidate extends Validate
{
    protected $rule = [
        'name|商品名称' => 'require|max:100',
        'cate_id|分类id' => 'require|number',
        'image|商品主图' => 'require'
    ];
}