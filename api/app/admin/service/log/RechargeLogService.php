<?php

namespace app\admin\service\log;

use app\model\user\UserBalanceRechargeLog;

class RechargeLogService
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

        if (!empty($param['recharge_no'])) {
            $where[] = ['recharge_no', '=', $param['recharge_no']];
        }

        if (!empty($param['pay_no'])) {
            $where[] = ['pay_no', '=', $param['pay_no']];
        }

        if (!empty($param['create_time'])) {
            $where[] = ['create_time', 'between', [$param['create_time'][0] . ' 00:00:00', $param['create_time'][1] . ' 23:59:59']];
        }

        if (!empty($param['status'])) {
            $where[] = ['status', '=', $param['status']];
        }

        try {
            $userBalanceRechargeLogModel = new UserBalanceRechargeLog();
            $list = $userBalanceRechargeLogModel->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}