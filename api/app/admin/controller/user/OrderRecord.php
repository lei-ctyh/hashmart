<?php

namespace app\admin\controller\user;

use app\admin\controller\Base;
use app\admin\service\user\OrderRecordService;

class OrderRecord extends Base
{

    /**
     * 用户中奖数据列表
     */
    public function index()
    {
        $orderRecordService = new OrderRecordService();
        return json($orderRecordService->getList(input('param.')));
    }
}