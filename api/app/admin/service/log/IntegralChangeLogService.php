<?php

namespace app\admin\service\log;

use app\model\user\UserIntegralChangeLog;

class IntegralChangeLogService
{
    /**
     * 获取哈希币变动列表
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

        if (!empty($param['create_time'])) {
            $where[] = ['create_time', 'between', [$param['create_time'][0] . ' 00:00:00', $param['create_time'][1] . ' 23:59:59']];
        }

        if (!empty($param['type'])) {
            $where[] = ['type', '=', $param['type']];
        }

        try {
            $UserBoxExchange = new UserIntegralChangeLog();
            $list = $UserBoxExchange->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}