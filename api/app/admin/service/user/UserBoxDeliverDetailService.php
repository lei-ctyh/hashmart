<?php

namespace app\admin\service\user;

use app\model\user\UserBoxDeliver;
use app\model\user\UserBoxDeliverDetail;

class UserBoxDeliverDetailService
{
    /**
     * 获取发货详情列表
     * @param $param
     * @return array
     */
    public function getUserBoxDeliverDetailList($param): array
    {
        $where[] = ['deliver_id', '=', $param['deliver_id']];

        $userBoxDeliverDetailModel = new UserBoxDeliverDetail();
        $list = $userBoxDeliverDetailModel->with(['reward'])->where($where)->order('id asc')->paginate($param['limit']);

        $addressInfo = (new UserBoxDeliver())->findOne([
            'id' => $param['deliver_id']
        ], 'address_info')['data'];

        $return = pageReturn(dataReturn(0, 'success', $list));
        $return['data']['address_info'] = $addressInfo['address_info'];
        return $return;
    }

}