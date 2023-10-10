<?php

namespace strategy\order\impl;

use app\api\service\order\BagService;
use app\model\order\OrderExpress;
use strategy\order\OrderInterface;
use think\facade\Log;

class ExpressOrder implements OrderInterface
{
    public function dealOrder($param)
    {
        $data = $param['data'];

        $orderModel = new OrderExpress();
        $orderInfo = $orderModel->findOne([
            'pay_no' => $data->out_trade_no,
            'pay_status' => 1
        ])['data'];

        if (empty($orderInfo)) {
            Log::error('【订单未查询到错误】三方支付回调异常: ' . json_encode($param));
            return dataReturn(-1);
        }

        // 完成订单
        $orderInfo['express_order_id'] = $orderInfo['id'];
        $res = (new BagService())->completeExpressOrder($orderInfo, $orderInfo['box_type']);
        Log::info('订单发货支付信息：' . json_encode($res));

        // 维护订单状态
        $orderModel->updateByWehere([
            'pay_status' => ($res['code'] != 0) ? 6 : 2,
            'pay_error' => ($res['code'] != 0) ? $res['msg'] : '',
            'pay_time' => now(),
            'third_code' => $data->trade_no,
            'return_msg' => json_encode($data->all()),
            'update_time' => now()
        ], ['id' => $orderInfo['id']]);

        return $res;
    }
}