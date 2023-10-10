<?php

namespace app\api\service\goods;

use app\model\box\Blindbox;
use app\model\box\BlindboxDetail;
use app\model\goods\Goods;

class BlindboxService
{
    /**
     * 获取盲盒商品详情
     * @param $param
     * @return array
     */
    public function getBlindboxDetail($param)
    {
        $blindboxDetailModel = new BlindboxDetail();
        $blindboxList = $blindboxDetailModel->with('boxTag')->where('blindbox_id', $param['id'])
            ->order('probability asc')->select()->toArray();

        if (empty($blindboxList)) {
            return dataReturn(-1, '该盲盒不存在');
        }

        $probability = [];
        $probabilityColor = [];
        foreach ($blindboxList as $vo) {
            if (isset($probability[$vo['boxTag']['name']])) {
                $probability[$vo['boxTag']['name']] += $vo['probability'];
            } else {
                $probability[$vo['boxTag']['name']] = floatval($vo['probability']);
            }

            $probabilityColor[$vo['boxTag']['name']] = $vo['boxTag']['color'];
        }

        $probabilityMap = [];
        foreach ($probability as $key => $vo) {
            $probabilityMap[] = [
                'tag' => $key,
                'probability' => round($vo, 2),
                'color' => $probabilityColor[$key],
            ];
        }

        return dataReturn(0, 'success', [
            'list' => $blindboxList,
            'probability' => $probabilityMap
        ]);
    }

    /**
     * 获取盲盒基础信息
     * @param $param
     * @return array
     */
    public function getBlindboxInfo($param)
    {
        $blindboxModel = new Blindbox();
        return $blindboxModel->findOne(['id' => $param['blindbox_id']]);
    }

    /**
     * 获取商品介绍
     * @param $param
     * @return array
     */
    public function getGoodsInfo($param)
    {
        $goodsModel = new Goods();
        $goodsInfo = $goodsModel->field('id,name,sub_title,image,price,content')
            ->where([
                'id' => $param['id'],
                'status' => 1,
                'goods_type' => 2,
                'delete_flag' => 1
            ])->find();

        if (empty($goodsInfo)) {
            return dataReturn(-1, '商品信息错误');
        }

        return dataReturn(0, 'success', $goodsInfo);
    }
}