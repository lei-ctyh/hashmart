<?php

namespace app\model\user;

use app\model\BaseModel;

class UserAddress extends BaseModel
{
    public function addressUser()
    {
        return $this->belongsTo(User::class, 'user_id', 'id');
    }
}