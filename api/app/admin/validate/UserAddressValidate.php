<?php

namespace app\admin\validate;

use think\Validate;

class UserAddressValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'receiver|昵称' => 'require|max:155',
        'phone|用户手机号' => 'require|max:11',
        'province_code|省份编码' => 'require|max:55',
        'city_code|城市编码' => 'require|max:55',
        'area_code|区编码' => 'require|max:55',
        'address|详细地址' => 'require|max:255',
    ];

    protected $scene = [
        'edit' => ['id', 'receiver', 'phone', 'province_code', 'city_code', 'area_code', 'address'],
    ];
}