<?php

namespace app\admin\service\log;

use app\model\order\OrderExpress;

class ExpressOrderService
{
    /**
     * 获取运费订单
     * @param $param
     * @return array
     */
    public function getExpressOrderInfo($param)
    {
        $expressOrderModel = new OrderExpress();
        return $expressOrderModel->findOne(['id' => $param['express_order_id']]);
    }
}