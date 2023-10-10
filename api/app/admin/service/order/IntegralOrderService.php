<?php

namespace app\admin\service\order;

use app\admin\validate\OrderDeliverDetailValidate;
use app\admin\validate\OrderValidate;
use app\model\order\Order;
use app\model\order\OrderDeliverDetail;
use app\model\order\OrderDetail;
use app\model\system\SysExpress;
use app\model\user\UserAddress;
use think\exception\ValidateException;
use think\facade\Db;

class IntegralOrderService
{
    /**
     * 获取积分订单列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        $where[] = ["type", "=", 1];
        if (!empty($param['user_id'])) {
            $where[] = ['user_id', '=', $param['user_id']];
        }

        if (!empty($param['order_no'])) {
            $where[] = ['order_no', '=', $param['order_no']];
        }

        if (!empty($param['pay_order_no'])) {
            $where[] = ['pay_order_no', '=', $param['pay_order_no']];
        }

        if (!empty($param['status'])) {
            $where[] = ['status', '=', $param['status']];
        }

        try {

            $orderModel = new Order();
            $list = $orderModel->getPageList($param['limit'], $where)['data'];

            // 待发货订单
            $num = $orderModel->where('status', 2)->count('id');
            $return = pageReturn(dataReturn(0, 'success', $list));
            $return['data']['not_express'] = $num;
            return $return;
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
        $where = [];
        $where[] = ["order_id", "=", $param['order_id']];

        try {
            $orderModel = new OrderDetail();
            $list = $orderModel->findOne($where)['data'];

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 物流公司列表
     * @param $param
     * @return array
     */
    public function expressList($param): array
    {
        $where = [];
        $where[] = ["status", "=", 1];

        try {
            $sysExpressModel = new SysExpress();
            $list = $sysExpressModel->getAllList($where, '*', 'id asc');

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 发货
     * @param $param
     * @return array
     */
    public function delivery($param): array
    {
        $orderParam['express_no'] = $param['express_no'];
        $orderParam['express_name'] = $param['express_name'];
        $orderParam['express_code'] = $param['express_code'];
        $orderParam['status'] = 3;
        $orderParam['delivery_time'] = date('Y-m-d H:i:s');
        try {
            validate(OrderValidate::class)->scene('edit')->check($orderParam);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $orderDeliverDetailParam['order_id'] = $param['order_id'];
        $orderDeliverDetailParam['user_id'] = $param['user_id'];
        $orderDeliverDetailParam['create_time'] = date('Y-m-d H:i:s');

        try {
            validate(OrderDeliverDetailValidate::class)->scene('add')->check($orderDeliverDetailParam);
        } catch (ValidateException $e) {
            return dataReturn(-2, $e->getError());
        }

        Db::startTrans();
        try {
            // 更新order表的物流字段
            $orderModel = new Order();
            $orderModel->where(['id'=> $param['order_id']])->update($orderParam);

            // 更新物流表
            $orderDeliverDetailModel = new OrderDeliverDetail();
            $orderDeliverDetailModel->insert($orderDeliverDetailParam);

            Db::commit();
            return dataReturn(0, '发货成功');
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-3, $e->getMessage());
        }
    }

    /**
     * 物流详情
     * @param $param
     * @return array
     */
    public function express($param): array
    {
        $where = [];
        $where[] = ["order_id", "=", $param['order_id']];
        $where[] = ["user_id", "=", $param['user_id']];

        try {
            $orderDeliverDetailModel = new OrderDeliverDetail();
            $list = $orderDeliverDetailModel->findOne($where)['data'];

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}