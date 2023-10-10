<?php

namespace app\model\box;

use app\model\BaseModel;

class BlindboxDetail extends BaseModel
{
    public function blindbox()
    {
        return $this->hasOne(Blindbox::class, 'id', 'blindbox_id');
    }

    public function boxTag()
    {
        return $this->hasOne(BlindboxTag::class, 'id', 'tag_id');
    }
}