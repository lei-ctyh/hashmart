<?php

namespace app\admin\service\system;

use app\model\system\SysSetting;

class SystemService
{
    /**
     * 保存配置
     * @param $param
     * @return array
     */
    public function saveSystem($param)
    {
        try {

            $sysSettingModel = new SysSetting();
            foreach ($param as $key => $vo) {
                $sysSettingModel->where('key', $key)->update([
                    'value' => $vo
                ]);
            }
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0);
    }
}