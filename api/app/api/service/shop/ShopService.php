<?php

namespace app\api\service\shop;

use app\api\service\activity\SliderService;
use app\model\goods\Goods;
use app\model\goods\GoodsCate;
use app\model\goods\GoodsRuleExtend;

class ShopService
{
    /**
     * 获取分类列表
     * @return array
     */
    public function getCateList()
    {
        $cateModel = new GoodsCate();
        return $cateModel->getAllList(['status' => 1], 'id,name,icon', 'sort desc');
    }

    /**
     * 分类下的商品列表
     * @param $param
     * @return array
     */
    public function getGoodsListByCate($param)
    {
        $goodsModel = new Goods();
        return $goodsModel->getPageList($param['limit'], [
            'cate_id' => $param['cate_id'],
            'status' => 1,
            'goods_type' => 1,
            'delete_flag' => 1
        ], 'id,type,name,sub_title,image,stock,price,integral_price,sales');
    }

    /**
     * 获取商品列表
     * @param $param
     * @return array
     */
    public function getGoodsList($param)
    {
        if (!checkOpen()) {
            return dataReturn(404, '站点正在维护');
        }

        $goodsModel = new Goods();
        return $goodsModel->getPageList($param['limit'], [
            'status' => 1,
            'goods_type' => 1,
            'delete_flag' => 1
        ], 'id,type,name,sub_title,image,stock,price,integral_price,sales', 'sort desc');
    }

    /**
     * 获取商品详情
     * @param $param
     * @return array
     */
    public function getGoodsInfo($param)
    {
        $goodsModel = new Goods();
        $goodsInfo = $goodsModel->field('id,type,name,sub_title,image,stock,price,delivery_fee,integral_price,sales,content')
            ->with('rule')
            ->where([
                'id' => $param['id'],
                'status' => 1,
                'goods_type' => 1,
                'delete_flag' => 1
            ])->find();

        if (empty($goodsInfo)) {
            return dataReturn(-1, '商品信息错误');
        }

        if (!empty($goodsInfo['rule'])) {
            $goodsInfo['rule']['rule'] = json_decode($goodsInfo['rule']['rule'], true);
        }

        $goodsInfo['ruleExtend'] = [];
        if ($goodsInfo['type'] == 2) {
            $goodsRuleExtendModel = new GoodsRuleExtend();
            $goodsInfo['ruleExtend'] = $goodsRuleExtendModel->getAllList([
                'goods_id' => $param['id']
            ], 'id,sku,stock,sales,image,price,recovery_price,integral_price')['data'];
        }

        return dataReturn(0, 'success', $goodsInfo);
    }

    /**
     * 获取哈希币商城幻灯
     * @return array
     */
    public function getSliderList()
    {
        // 幻灯数据
        $activityService = new SliderService();
        return $activityService->getSliderData(2);
    }
}