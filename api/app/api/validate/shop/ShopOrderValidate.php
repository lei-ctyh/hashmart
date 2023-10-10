<?php

namespace app\api\validate\shop;

use think\Validate;

class ShopOrderValidate extends Validate
{
    protected $rule = [
        'goods_id' => 'require|number',
        'num' => 'require|number',
        'address_id' => 'require'
    ];

    protected $message  =   [
        'goods_id.require' => '商品的id不能为空',
        'num.require' => '购买的数量不能为空',
        'address_id.require' => '收货地址不能为空',
    ];
}