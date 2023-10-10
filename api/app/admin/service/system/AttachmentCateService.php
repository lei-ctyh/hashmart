<?php

namespace app\admin\service\system;

use app\model\system\SysAttachmentCate;

class AttachmentCateService
{
    /**
     * 获取分类
     * @return array
     */
    public function getCateList()
    {
        $sysAttachmentModel = new SysAttachmentCate();
        $list = $sysAttachmentModel->getAllList()['data'];

        $list = !empty($list) ? $list->toArray() : [];
        return dataReturn(0, 'success', makeTree($list));
    }

    /**
     * 添加分类
     * @param $param
     * @return array
     */
    public function addCate($param)
    {
        if (empty($param['name'])) {
            return dataReturn(-1, '请输入分类名称');
        }

        $sysAttachmentModel = new SysAttachmentCate();
        $has = $sysAttachmentModel->findOne(['name' => $param['name']])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该分类名称已经存在');
        }

        $param['create_time'] = now();
        return $sysAttachmentModel->insertOne($param);
    }

    /**
     * 编辑分类
     * @param $param
     * @return array
     */
    public function editCate($param)
    {
        if (empty($param['name'])) {
            return dataReturn(-1, '请输入分类名称');
        }

        $sysAttachmentModel = new SysAttachmentCate();
        $where[] = ['name', '=', $param['name']];
        $where[] = ['id', '<>', $param['id']];
        $has = $sysAttachmentModel->findOne($where)['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该分类名称已经存在');
        }

        $param['update_time'] = now();
        return $sysAttachmentModel->updateById($param, $param['id']);
    }

    /**
     * 删除分类
     * @param $id
     * @return array
     */
    public function delCate($id)
    {
        $sysAttachmentModel = new SysAttachmentCate();
        $has = $sysAttachmentModel->findOne(['pid' => $id])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该分类下有子分类不可删除');
        }

        return $sysAttachmentModel->delById($id);
    }
}