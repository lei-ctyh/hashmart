<?php

namespace app\model\order;

use app\model\BaseModel;
use app\model\box\Blindbox;

class Order extends BaseModel
{
    public function blindbox()
    {
        return $this->hasOne(Blindbox::class, 'id', 'blindbox_id')->visible(['name', 'pic']);
    }

    public function deliverDetail()
    {
        return $this->hasOne(OrderDeliverDetail::class, 'order_id', 'id');
    }
}