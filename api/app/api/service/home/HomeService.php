<?php

namespace app\api\service\home;

use app\api\service\activity\SliderService;
use app\model\box\Blindbox;
use app\model\box\BlindboxDetail;

class HomeService
{
    /**
     * 首页聚合数据
     * @return array
     */
    public function getHomeData($param)
    {
        if (!checkOpen()) {
            return dataReturn(404, '站点正在维护');
        }

        // 幻灯数据
        $activityService = new SliderService();
        $sliderList = $activityService->getSliderData();

        // 商品列表
        $goodsList = $this->getGoodsData($param);

        return dataReturn(0, 'success', [
            'slider' => $sliderList['data'],
            'goods' => $goodsList['data']
        ]);
    }

    /**
     * 首页盲盒列表数据
     * @param $param
     * @return array
     */
    public function getGoodsData($param)
    {
        $blindboxModel = new Blindbox();

        $blindboxIds = [];
        $blindboxList = $blindboxModel->field('id,name,desc,pic,sales,hot_tag,recommend_tag,price')->with('detail')
            ->order('sort desc')->paginate($param['limit'])->each(function ($item) use (&$blindboxIds) {
                $blindboxIds[] = $item->id;
            });
        $blindboxDetail = new BlindboxDetail();

        // 价格范围
        $priceRangeList = $blindboxDetail->field('max(price) max_price,min(price) min_price,blindbox_id')->where([
            ['blindbox_id', 'in', $blindboxIds]
        ])->group('blindbox_id')->select();

        $blindboxId2Price = [];
        foreach ($priceRangeList as $vo) {
            $blindboxId2Price[$vo['blindbox_id']] = [
                'min_price' => $vo['min_price'],
                'max_price' => $vo['max_price'],
            ];
        }

        $blindboxList->each(function ($item) use($blindboxId2Price) {
            $item->price_range = $blindboxId2Price[$item->id] ?? [];
            return $item;
        });

        return dataReturn(0, 'success', $blindboxList);
    }
}