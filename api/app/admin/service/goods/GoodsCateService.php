<?php

namespace app\admin\service\goods;

use app\admin\validate\GoodsCateValidate;
use app\model\goods\GoodsCate;
use think\exception\ValidateException;

class GoodsCateService
{
    /**
     * 获取分类列表
     * @return array
     */
    public function getCateList()
    {
        $goodsCateModel = new GoodsCate();
        $list = $goodsCateModel->getAllList()['data'];

        return dataReturn(0, 'success', makeTree($list->toArray()));
    }

    /**
     * 添加分类
     * @param $param
     * @return array
     */
    public function addCate($param)
    {
        try {
            validate(GoodsCateValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $goodsCateModel = new GoodsCate();
        // 检测重复
        $has = $goodsCateModel->checkUnique(['name' => $param['name']])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该分类已经存在');
        }

        $param['create_time'] = now();
        return $goodsCateModel->insertOne($param);
    }

    /**
     * 编辑分类
     * @param $param
     * @return array
     */
    public function editCate($param)
    {
        try {
            validate(GoodsCateValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $goodsCateModel = new GoodsCate();
        // 检测重复
        $has = $goodsCateModel->checkUnique(['name' => $param['name']], $param['id'])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该分类已经存在');
        }

        $param['update_time'] = now();
        return $goodsCateModel->updateById($param, $param['id']);
    }

    /**
     * 删除分类
     * @param $id
     * @return array
     */
    public function delCate($id)
    {
        $goodsCateModel = new GoodsCate();

        $has = $goodsCateModel->findOne(['pid' => $id])['data'];
        if (!empty($has)) {
            return dataReturn(-1, '该分类下有子分类不可删除');
        }

        return $goodsCateModel->delById($id);
    }
}