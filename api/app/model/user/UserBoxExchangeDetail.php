<?php

namespace app\model\user;

use app\model\BaseModel;
use app\model\goods\Goods;
use app\model\order\OrderRecordDetail;

class UserBoxExchangeDetail extends BaseModel
{
    public function goods()
    {
        return $this->hasOne(Goods::class, 'id', 'goods_id');
    }

    /**
     * 奖品数据
     */
    public function reward()
    {
        return $this->hasOne(OrderRecordDetail::class, 'id', 'record_detail_id')->visible(['goods_id', 'goods_image', 'goods_name', 'recovery_price']);
    }
}