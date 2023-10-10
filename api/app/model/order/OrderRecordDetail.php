<?php

namespace app\model\order;

use app\model\BaseModel;
use app\model\box\BlindboxTag;

class OrderRecordDetail extends BaseModel
{
    public function tag()
    {
        return $this->hasOne(BlindboxTag::class, 'id', 'tag_id');
    }
}