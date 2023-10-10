<?php

namespace app\model\box;

use app\model\BaseModel;

class Blindbox extends BaseModel
{
    public function detail()
    {
        return $this->hasMany(BlindboxDetail::class, 'blindbox_id', 'id')->visible(['goods_image'])->limit(4);
    }

    public function orderDetail()
    {
        return $this->hasMany(BlindboxDetail::class, 'blindbox_id', 'id')->visible(['goods_name', 'goods_image', 'price', 'recovery_price']);
    }
}