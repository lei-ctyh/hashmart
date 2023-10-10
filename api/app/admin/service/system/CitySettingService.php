<?php

namespace app\admin\service\system;

use app\admin\validate\CitySettingValidate;
use app\model\system\SysCity;
use think\exception\ValidateException;

class CitySettingService
{
    /**
     * 获取城市列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        if (!empty($param['pid'])) {
            $where[] = ['pid', '=', $param['pid']];
        }
        if (!empty($param['level'])) {
            $where[] = ['level', '=', $param['level']];
        }

        try {
            $sysCityModel = new SysCity();
            $list = $sysCityModel->getAllList($where, "id,pid,name,level,is_show", "id asc")['data'];
            foreach ($list as $key => $vo) {
                if ($param['level'] <= 2) {
                    $list[$key]['hasChildren'] = true;
                    $list[$key]['children'] = [];
                }
            }

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 添加城市
     * @param $param
     * @return array
     */
    public function addCity($param)
    {
        // 检验完整性
        try {
            validate(CitySettingValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $sysCityModel = new SysCity();
        $has = $sysCityModel->checkUnique([
            'name' => $param['name'],
            'pid' => $param['pid'],
        ])['data'];

        if (!empty($has)) {
            return dataReturn(-2, '名称已经存在');
        }

        return $sysCityModel->insertOne($param);
    }

    /**
     * 编辑城市
     * @param $param
     * @return array
     */
    public function editCity($param)
    {
        // 检验完整性
        try {
            validate(CitySettingValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $sysCityModel = new SysCity();
        $where[] = ['id', '<>', $param['id']];
        $where[] = ['name', '=', $param['name']];
        $has = $sysCityModel->checkUnique($where)['data'];

        if (!empty($has)) {
            return dataReturn(-2, '名称已经存在');
        }

        return $sysCityModel->updateById($param, $param['id']);
    }

    /**
     * 删除城市
     * @param $id
     * @return array
     */
    public function delCity($id): array
    {
        $sysCityModel = new SysCity();

        $has = $sysCityModel->where('pid', $id)->find();
        if (!empty($has)) {
            return dataReturn(-1, '该地区下还存在地区，不可删除');
        }

        return $sysCityModel->delById($id);
    }
}