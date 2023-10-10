<?php

namespace app\admin\service\system;

use app\admin\validate\SliderSettingValidate;
use app\model\system\ActivitySlider;
use think\exception\ValidateException;

class SliderSettingService
{
    /**
     * 获取轮播配置列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['goods_name'])) {
            $where[] = ['goods_name', '=', $param['goods_name']];
        }

        try {
            $activitySliderModel = new ActivitySlider();
            $list = $activitySliderModel->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 添加
     * @param $param
     * @return array
     */
    public function addSliderSetting($param)
    {
        try {
            validate(SliderSettingValidate::class)->scene('add')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        if ($param['type'] == 1) {
            if (empty($param['blindbox_id']) || empty($param['blindbox_name'])) {
                return dataReturn(-2, '请选择盲盒商品');
            }
        } else {
            if (empty($param['goods_id']) || empty($param['goods_name'])) {
                return dataReturn(-2, '请选择商品');
            }
        }

        $param['create_time'] = date('Y-m-d H:i:s');

        $menuModel = new ActivitySlider();
        return $menuModel->insertOne($param);
    }

    /**
     * 编辑
     * @param $param
     * @return array
     */
    public function editSliderSetting($param)
    {
        try {
            validate(SliderSettingValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        if ($param['type'] == 1) {
            if (empty($param['blindbox_id']) || empty($param['blindbox_name'])) {
                return dataReturn(-2, '请选择盲盒商品');
            }
        } else {
            if (empty($param['goods_id']) || empty($param['goods_name'])) {
                return dataReturn(-2, '请选择商品');
            }
        }

        $param['update_time'] = date('Y-m-d H:i:s');

        $menuModel = new ActivitySlider();
        return $menuModel->updateById($param, $param['id']);
    }

    /**
     * 删除
     * @param $id
     * @return array
     */
    public function delSliderSetting($id)
    {
        $activitySliderModel = new ActivitySlider();
        return $activitySliderModel->delById($id);
    }
}