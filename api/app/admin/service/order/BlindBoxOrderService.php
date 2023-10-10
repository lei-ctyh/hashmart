<?php

namespace app\admin\service\order;

use app\model\order\Order;
use app\model\order\OrderRecordDetail;

class BlindBoxOrderService
{
    /**
     * 获取会员列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        $where[] = ["type", "=", 2];
        if (!empty($param['order_no'])) {
            $where[] = ['order_no', '=', $param['order_no']];
        }

        if (!empty($param['pay_order_no'])) {
            $where[] = ['pay_order_no', '=', $param['pay_order_no']];
        }

        if (!empty($param['user_name'])) {
            $where[] = ['user_name', '=', $param['user_name']];
        }

        if (!empty($param['pay_status'])) {
            $where[] = ['pay_status', '=', $param['pay_status']];
        }

        try {
            $orderModel = new Order();
            $list = $orderModel->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 中奖记录
     * @param $param
     * @return array
     */
    public function detail($param): array
    {
        $where = [];
        $where[] = ["order_id", "=", $param['order_id']];

        try {
            $orderDetailModel = new OrderRecordDetail();
            $list = $orderDetailModel->getAllList($where)['data'];

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}