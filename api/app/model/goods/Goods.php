<?php

namespace app\model\goods;

use app\model\BaseModel;

class Goods extends BaseModel
{
    /**
     * 商品分类
     */
    public function cate()
    {
        return $this->hasOne(GoodsCate::class, 'id', 'cate_id');
    }

    /**
     * 商品规格
     */
    public function rule()
    {
        return $this->hasOne(GoodsRule::class, 'goods_id', 'id');
    }

    /**
     * 商品规格
     */
    public function ruleExtend()
    {
        return $this->hasMany(GoodsRuleExtend::class, 'goods_id', 'id');
    }
}