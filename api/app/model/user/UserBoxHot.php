<?php

namespace app\model\user;

use app\model\BaseModel;
use app\model\order\OrderRecordDetail;

class UserBoxHot extends BaseModel
{
    public function orderRecordDetail()
    {
        return $this->hasOne(OrderRecordDetail::class, 'id', 'record_detail_id')->bind([
            'recovery_price' => 'recovery_price'
        ]);
    }
}