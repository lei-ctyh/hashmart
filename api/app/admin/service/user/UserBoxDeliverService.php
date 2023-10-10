<?php

namespace app\admin\service\user;

use app\admin\validate\UserBoxDeliverValidate;
use app\model\order\OrderExpress;
use app\model\user\User;
use app\model\user\UserBoxDeliver;
use think\exception\ValidateException;

class UserBoxDeliverService
{
    /**
     * 获取奖品发货列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (! empty($param['deliver_no'])) {
            $where[] = ['deliver_no', '=', $param['deliver_no']];
        }

        if (!empty($param['status'])) {
            $where[] = ['status', '=', $param['status']];
        }

        if (!empty($param['user_id'])) {
            $where[] = ['user_id', '=', $param['user_id']];
        }

        try {

            $userBoxDeliverModel = new UserBoxDeliver();
            $list = $userBoxDeliverModel->where($where)->order('id desc')->paginate($param['limit']);
            // 待发货订单
            $num = $userBoxDeliverModel->where('status', 1)->count('id');

            if (! empty($list)) {
                $userIds = [];
                foreach ($list as &$item) {
                    $userIds[] = $item['user_id'];
                    $item['express'] = json_decode($item['express'], true);
                }
                if (! empty($userIds)) {
                    $userModel = new User();
                    $userWhere[] = ['id', 'in', $userIds];
                    $res = $userModel->where($userWhere)->field(['id', 'nickname'])->select()->toArray();
                    if (! empty($res)) {
                        $userNames = [];
                        foreach ($res as $re) {
                            $userNames[$re['id']] = $re['nickname'];
                        }
                        foreach ($list as &$item) {
                            $item['user_name'] = $userNames[$item['user_id']] ?? '';
                        }
                    }
                }
            }

            $return = pageReturn(dataReturn(0, '', $list));
            $return['data']['not_express'] = $num;
            return $return;
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * Notes: 发货
     * Author: LJS
     * @param $param
     * @return array
     */
    public function deliver($param): array
    {
        try {
            validate(UserBoxDeliverValidate::class)->scene('deliver')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }
        $param['update_time'] = date('Y-m-d H:i:s');
        $param['status'] = 2; // 已发货

        $userBoxDeliverModel = new UserBoxDeliver();
        $res = $userBoxDeliverModel->updateById($param, $param['id']);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '发货成功');
    }

    /**
     * 获取订单信息详情
     * @param $id
     * @return array
     */
    public function getOrderInfo($id)
    {
        $orderExpressModel = new OrderExpress();
        $info = $orderExpressModel->findOne(['id' => $id])['data'];

        $payStatus = config('pay.pay_status');
        $info['status_txt'] = $payStatus[$info['pay_status']];

        $payWay = config('pay.pay_way');
        $info['pay_way_txt'] = $payWay[$info['pay_way']];

        return dataReturn(0, 'success', $info);
    }
}