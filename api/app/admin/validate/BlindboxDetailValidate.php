<?php

namespace app\admin\validate;

use think\Validate;

class BlindboxDetailValidate extends Validate
{
    protected $rule = [
        'blindbox_id|盲盒id' => 'require',
        'tag_id|商品标签' => 'require',
        'goods_id|商品ID' => 'require',
        'goods_name|商品名称' => 'require',
        'goods_image|商品图片' => 'require',
        'price|商品价格' => 'require'
    ];
}