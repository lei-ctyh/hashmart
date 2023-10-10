<?php

namespace app\admin\controller\user;

use app\admin\controller\Base;
use app\admin\service\user\OrderRecordDetailService;

class OrderRecordDetail extends Base
{
    /**
     * 用户中奖数据奖品详情
     */
    public function index()
    {
        $orderRecordDetailService = new OrderRecordDetailService();
        return json($orderRecordDetailService->getUserOrderRecordDetailList(input('param.')));
    }
}