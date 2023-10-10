<?php

namespace app\model\user;

use app\model\BaseModel;

class UserBoxExchange extends BaseModel
{
    public function detail()
    {
        return $this->hasMany(UserBoxExchangeDetail::class, 'exchange_id', 'id')->visible(['exchange_id']);
    }
}