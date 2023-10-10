<?php

namespace app\admin\service\box;

use app\admin\validate\BlindboxValidate;
use app\model\box\Blindbox;
use app\model\box\BlindboxDetail;
use think\exception\ValidateException;

class BlindboxService
{
    /**
     * 获取盲盒列表
     * @param $param
     * @return array
     */
    public function getBlindboxList($param)
    {
        $where = [];
        if (!empty($param['name'])) {
            $where[] = ['name', '=', $param['name']];
        }

        $blindboxModel = new Blindbox();
        $list = $blindboxModel->getPageList($param['limit'], $where, '*', 'sort desc');

        $blindboxDetailModel = new BlindboxDetail();
        $list['data']->each(function ($item) use ($blindboxDetailModel) {
            $item->goods_num = $blindboxDetailModel->where('blindbox_id', $item->id)->count('id');
        });

        return pageReturn($list);
    }

    /**
     * 添加盲盒
     * @param $param
     * @return array
     */
    public function addBlindbox($param)
    {
        try {
            validate(BlindboxValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        if ($param['price'] < 0) {
            return dataReturn(-3, '单抽价格必须大于等于0');
        }

        $blindboxModel = new Blindbox();
        $has = $blindboxModel->checkUnique(['name' => $param['name']])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该盲盒已经存在');
        }

        $param['create_time'] = now();
        return $blindboxModel->insertOne($param);
    }

    /**
     * 编辑盲盒
     * @param $param
     * @return array
     */
    public function editBlindbox($param)
    {
        try {
            validate(BlindboxValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        if ($param['price'] < 0) {
            return dataReturn(-3, '单抽价格必须大于等于0');
        }

        $blindboxModel = new Blindbox();

        $where[] = ['name', '=', $param['name']];
        $where[] = ['id', '<>', $param['id']];
        $has = $blindboxModel->checkUnique($where)['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该盲盒已经存在');
        }

        $param['update_time'] = now();
        return $blindboxModel->updateById($param, $param['id']);
    }

    /**
     * 删除盲盒
     * @param $id
     * @return array
     */
    public function delBlindbox($id)
    {
        $blindboxDetailModel = new BlindboxDetail();

        $has = $blindboxDetailModel->findOne(['box_id' => $id])['data'];
        if (!empty($has)) {
            return dataReturn(-1, '该盲盒下有商品不可删除');
        }

        $blindboxModel = new Blindbox();
        $res = $blindboxModel->delById($id);

        if ($res['code'] != 0) {
            return $res;
        }

        return (new BlindboxDetail())->delByWhere(['blindbox_id' => $id]);
    }
}