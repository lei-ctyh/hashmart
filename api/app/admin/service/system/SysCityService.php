<?php

namespace app\admin\service\system;

use app\model\system\SysCity;

class SysCityService
{
    /**
     * 获取省市区联动数据
     * @return array
     */
    public function areas(): array
    {
        $where = [];
        $where[] = ["is_show", "=", 1];
        $field = ['id', 'pid', 'name', 'level'];
        $order = 'id asc';
        try {
            $sysCityModel = new SysCity();
            $list = $sysCityModel->getAllList($where, $field, $order)['data'];

            $list = !empty($list) ? $list->toArray() : [];
            if (count($list) > 0) {
                foreach ($list as &$item) {
                    $item['value'] = $item['id'];
                    $item['label'] = $item['name'];
                    unset($item);
                }
            }
            return dataReturn(0, 'success', makeTree($list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}