<?php

namespace app\admin\service\user;

use app\admin\validate\UserAddressValidate;
use app\model\system\SysCity;
use app\model\user\UserAddress;
use think\exception\ValidateException;

class UserAddressService
{
    /**
     * 获取会员地址列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['user_id'])) {
            $where[] = ['user_id', '=', $param['user_id']];
        }

        try {
            $userAddressModel = new UserAddress();
            $list = $userAddressModel->where($where)->order('id desc')->paginate($param['limit']);

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }


    public function editUserAddress($param)
    {
        try {
            validate(UserAddressValidate::class)->scene('edit')->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }
        $sysCityModel = new SysCity();
        $where[] = ['id', 'in', [$param['province_code'], $param['city_code'], $param['area_code']]];
        $res = $sysCityModel->where($where)->field(['id', 'name'])->select()->toArray();
        if (empty($res)) {
            return dataReturn(-1, '省市区数据错误');
        }
        $codeNames = [];
        foreach ($res as $re) {
            $codeNames[$re['id']] = $re['name'];
        }
        // 赋值省市区名称
        $param['province_name'] = $codeNames[$param['province_code']] ?? '';
        $param['city_name'] = $codeNames[$param['city_code']] ?? '';
        $param['area_name'] = $codeNames[$param['area_code']] ?? '';
        $param['update_time'] = date('Y-m-d H:i:s');

        $userAddressModel = new UserAddress();
        $res = $userAddressModel->updateById($param, $param['id']);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, '编辑成功');
    }

    /**
     * 删除会员地址
     * @param $id
     * @return array
     */
    public function delUserAddress($id): array
    {
        $userAddressModel = new UserAddress();
        return $userAddressModel->delById($id);
    }

}