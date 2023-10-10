<?php

namespace app\api\service\activity;

use app\model\system\ActivitySlider;

class SliderService
{
    /**
     * 轮播数据
     * @param $type
     * @return array
     */
    public function getSliderData($type = 1)
    {
        $sliderModel = new ActivitySlider();
        return $sliderModel->getAllList(['status' => 1, 'type' => $type], 'id,title,blindbox_id,goods_id,pic,other');
    }
}