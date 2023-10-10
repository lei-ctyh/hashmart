<?php

namespace app\admin\service\system;

use app\model\user\UserAgree;

class PactSettingService
{
    /**
     * 获取协议设置
     * @param $param
     * @return array
     */
    public function getPactConfig($param): array
    {
        try {
            $UserAgreeModel = new UserAgree();
            $data = $UserAgreeModel->select();
            foreach ($data as $value){
                if($value->type == '1'){
                    $list['userform'] = $value['content'];
                }
                if($value->type == '2'){
                    $list['privacyform'] = $value['content'];
                }
            }

            return dataReturn(0, 'success', $list);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 保存配置
     * @param $param
     * @return array
     */
    public function save($param): array
    {
        $where[] = ["type", "=", $param['type']];

        try {
            $UserAgreeModel = new UserAgree();
            $UserAgreeModel->where($where)->update([
                'content' => $param['content']
            ]);

            return dataReturn(0, '更新成功');
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}