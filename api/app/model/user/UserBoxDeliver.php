<?php

namespace app\model\user;

use app\model\BaseModel;

class UserBoxDeliver extends BaseModel
{
    public function detail()
    {
        return $this->hasMany(UserBoxDeliverDetail::class, 'deliver_id', 'id')->visible(['user_box_uuid']);
    }
}