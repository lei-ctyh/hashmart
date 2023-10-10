<?php

namespace app\admin\service\log;

use app\model\user\UserBoxExchange;
use app\model\user\UserBoxExchangeDetail;

class BoxExchangeService
{
    /**
     * 获取充值列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['username'])) {
            $where[] = ['username', '=', $param['username']];
        }

        if (!empty($param['user_id'])) {
            $where[] = ['user_id', '=', $param['user_id']];
        }

        if (!empty($param['exchange_no'])) {
            $where[] = ['exchange_no', '=', $param['exchange_no']];
        }

        try {
            $UserBoxExchange = new UserBoxExchange();
            $list = $UserBoxExchange->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 订单详情
     * @param $param
     * @return array
     */
    public function detail($param): array
    {
        $where[] = ["exchange_id", "=", $param['exchange_id']];

        try {

            $orderModel = new UserBoxExchangeDetail();
            $list = $orderModel->with('reward')->where($where)->select();

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}