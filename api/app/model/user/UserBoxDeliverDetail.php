<?php

namespace app\model\user;

use app\model\BaseModel;
use app\model\goods\Goods;
use app\model\order\OrderRecordDetail;
use app\model\order\OrderRecordLog;

class UserBoxDeliverDetail extends BaseModel
{
    /* 远程一对一关联，一个发货详情通过一个record查到一个good
	 * 参数1，我最终需要获取的那张表的模型
	 * 参数2，我需要使用到的中间表模型
	 * 参数3，中间表与主表相等的字段,填写中间表字段
	 * 参数4，目标表与中间表相等的字段,填写目标表字段
	 * 参数5，主表与中间表相等的字段,填写主表字段
	 * 参数6，中间表与目标表相等的字段,填写中间表字段
	 * */
    public function oneGoods()
    {
        return $this->hasOneThrough(Goods::class, OrderRecordLog::class, 'id', 'id', 'record_id', 'goods_id');
    }

    /**
     * 奖品信息
     */
    public function reward()
    {
        return $this->hasOne(UserBoxLog::class, 'uuid', 'user_box_uuid');
    }

    /**
     * 奖品数据
     */
    public function rewardSimple()
    {
        return $this->hasOne(OrderRecordDetail::class, 'id', 'record_detail_id')->visible(['goods_id', 'goods_image', 'goods_name']);
    }
}