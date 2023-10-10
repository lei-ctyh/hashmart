<?php

namespace app\admin\service\user;

use app\model\order\OrderRecordDetail;

class OrderRecordDetailService
{
    /**
     * 获取用户中奖数据奖品详情列表
     * @param $param
     * @return array
     */
    public function getUserOrderRecordDetailList($param): array
    {
        $where[] = ['order_record_id', '=', $param['order_record_id']];
        $orderRecordDetailModel = new OrderRecordDetail();
        $list = $orderRecordDetailModel->where($where)->order('id asc')->paginate($param['limit']);

        return pageReturn(dataReturn(0, 'success', $list));
    }

}