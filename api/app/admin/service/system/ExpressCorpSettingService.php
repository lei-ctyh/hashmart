<?php

namespace app\admin\service\system;

use app\admin\validate\SysExpressValidate;
use app\model\system\SysExpress;
use think\exception\ValidateException;

class ExpressCorpSettingService
{
    /**
     * 获取物流公司列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['code'])) {
            $where[] = ['code', '=', $param['code']];
        }

        try {
            $SysExpressModel = new SysExpress();
            $list = $SysExpressModel->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }

    /**
     * 添加物流公司
     * @param $param
     * @return array
     */
    public function addExpressCorp($param)
    {
        try {
            validate(SysExpressValidate::class)->scene('add')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $param['create_time'] = date('Y-m-d H:i:s');

        $sysExpressModel = new SysExpress();
        return $sysExpressModel->insertOne($param);
    }

    /**
     * 编辑物流公司
     * @param $param
     * @return array
     */
    public function editExpressCorp($param)
    {
        try {
            validate(SysExpressValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $param['update_time'] = date('Y-m-d H:i:s');

        $sysExpressModel = new SysExpress();
        return $sysExpressModel->updateById($param, $param['id']);
    }

    /**
     * 删除物流公司
     * @param $id
     * @return array
     */
    public function delExpressCorp($id)
    {
        $SysExpressModel = new SysExpress();
        return $SysExpressModel->delById($id);
    }


}