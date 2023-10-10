<?php

namespace app\api\validate\user;

use think\Validate;

class UserValidate extends Validate
{
    protected $rule = [
        'id|ID' => 'require',
        'nickname|用户昵称' => 'require|max:155',
        'hash_key|用户hashkey' => 'require|max:155',
        'phone|用户手机号' => 'require|max:11',
        'avatar|头像' => 'require|max:155',
    ];

    protected $message = [
        'id.require' => '用户id不能为空',
        'nickname.require' => '用户昵称不能为空',
        'hash_key.require' => '用户hashkey不能为空',
        'phone.require' => '手机号不能为空',
        'phone.max' => '手机号最长为11位',
        'avatar.require' => '用户头像不能为空',
    ];

    protected $scene = [
        'edit-nickname' => ['nickname'],
        'edit-hash_key' => ['hash_key'],
        'edit-phone' => ['phone'],
        'edit-avatar' => ['avatar'],
    ];
}