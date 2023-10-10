<?php

namespace app\api\validate\user;

use think\Validate;

class AddressValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'receiver|收件人名' => 'require|max:155',
        'phone|用户手机号' => 'require|max:11',
        'province_code|省份编码' => 'require|max:55',
        'city_code|城市编码' => 'require|max:55',
        'area_code|区编码' => 'require|max:55',
        'address|详细地址' => 'require|max:255',
    ];

    protected $message = [
        'id.require' => '收货地址id不能为空',
        'receiver.require' => '收货人名不能为空',
        'phone.require' => '手机号不能为空',
        'phone.max' => '手机号最长为11位',
        'province_code.require' => '省份编码不能为空',
        'city_code.require' => '城市编码不能为空',
        'area_code.require' => '地区编码不能为空',
        'address.require' => '详细地址不能为空',
    ];

    protected $scene = [
        'add' => ['receiver', 'phone', 'province_code', 'city_code', 'area_code', 'address'],
        'edit' => ['id', 'receiver', 'phone', 'province_code', 'city_code', 'area_code', 'address']
    ];
}