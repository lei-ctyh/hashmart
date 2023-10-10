<?php

namespace app\admin\controller\box;

use app\admin\controller\Base;
use app\admin\service\box\BlindboxDetailService;

class BlindboxDetail extends Base
{
    /**
     * 盲盒详情列表
     */
    public function index()
    {
        $blindboxDetailService = new BlindboxDetailService();
        return json($blindboxDetailService->getBlindboxDetailList(input('param.')));
    }

    /**
     * 添加盲盒商品
     */
    public function add()
    {
        $blindboxDetailService = new BlindboxDetailService();
        return json($blindboxDetailService->addBlindboxDetail(input('post.')));
    }

    /**
     * 编辑盲盒商品
     */
    public function edit()
    {
        $blindboxDetailService = new BlindboxDetailService();
        return json($blindboxDetailService->editBlindboxDetail(input('post.')));
    }

    /**
     * 删除盲盒商品
     */
    public function del()
    {
        $blindboxDetailService = new BlindboxDetailService();
        return json($blindboxDetailService->delBlindboxDetail(input('param.id'), input('param.blindbox_id')));
    }

    /**
     * 获取某个系列下的最小中奖号码
     */
    public function getLotteryProbability()
    {
        $blindboxDetailService = new BlindboxDetailService();
        return json($blindboxDetailService->getLotteryProbability(input('param.blindbox_id'), input('param.percent')));
    }

    /**
     * 获取抽奖的数字范围
     */
    public function getLotteryNumRange()
    {
        $blindboxDetailService = new BlindboxDetailService();
        return json($blindboxDetailService->getLotteryNumRange(input('param.blindbox_id'), input('param.percent'), input('param.detail_id')));
    }
}