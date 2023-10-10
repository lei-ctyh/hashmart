<?php

namespace app\admin\service\user;

use app\model\order\OrderRecord;

class OrderRecordService
{
    /**
     * 获取用户中奖数据列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (! empty($param['username'])) {
            $where[] = ['username', '=', $param['username']];
        }

        if (! empty($param['user_id'])) {
            $where[] = ['user_id', '=', $param['user_id']];
        }

        if (!empty($param['create_time'])) {
            $where[] = ['create_time', 'between', [$param['create_time'][0] . ' 00:00:00', $param['create_time'][1] . ' 23:59:59']];
        }

        try {
            $orderRecordModel = new OrderRecord();
            $list = $orderRecordModel->where($where)->order('id desc')->paginate($param['limit']);

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

}