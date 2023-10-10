<?php

namespace app\api\service\user;

use app\api\validate\user\AddressValidate;
use app\model\system\SysCity;
use app\model\user\UserAddress;
use think\facade\Db;

class AddressService
{
    /**
     * Notes: 省市区数据
     * Author: LJS
     * @return mixed
     */
    public function getOption()
    {
        $where = [];
        $where[] = ["is_show", "=", 1];
        $field = ['id', 'pid', 'name', 'level'];
        $order = 'id asc';

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
    }

    /**
     * Notes: 获取用户收货地址列表
     * Author: LJS
     * @param $userInfo
     * @return mixed
     */
    public function getList($userInfo)
    {

        $where[] = ['user_id', '=', $userInfo['id']];
        $field = ['id', 'receiver', 'phone', 'province_code', 'province_name', 'city_code', 'city_name', 'area_code', 'area_name', 'address', 'default_flag'];
        $list = (new UserAddress())->getAllList($where, $field)['data'];

        return dataReturn(0, 'success', $list);
    }

    /**
     * Notes: 添加收货地址
     * Author: LJS
     * @param $param
     * @return array|mixed
     */
    public function add($param)
    {
        $addressValidate = new AddressValidate();
        if (!$addressValidate->scene('add')->check($param)) {
            return dataReturn(-1, $addressValidate->getError());
        }

        $userId = $param['user_info']['id'] ?? 0;
        if ($userId == 0) {
            return dataReturn(-1, '参数错误');
        }
        $isDefault = null;
        if (isset($param['is_default'])) {
            $isDefault = $param['is_default'];
            unset($param['is_default']);
        }
        unset($param['user_info']);

        Db::startTrans();
        try {
            $userAddressModel = new UserAddress();
            $where = [];
            $where[] = ['id', 'in', [$param['province_code'], $param['city_code'], $param['area_code']]];
            $res = (new SysCity())->getAllList($where, ['id', 'name'])['data'];
            if (empty($res)) {
                return dataReturn(-2, '省市区数据错误');
            }
            $codeNames = [];
            foreach ($res as $re) {
                $codeNames[$re['id']] = $re['name'];
            }
            $param['user_id'] = $userId;
            // 赋值省市区名称
            $param['province_name'] = $codeNames[$param['province_code']] ?? '';
            $param['city_name'] = $codeNames[$param['city_code']] ?? '';
            $param['area_name'] = $codeNames[$param['area_code']] ?? '';
            $param['create_time'] = date('Y-m-d H:i:s');
            $param['update_time'] = date('Y-m-d H:i:s');

            $res = $userAddressModel->insertOne($param);
            if ($res['code'] == 0) {
                $addressId = $res['data'];
            } else {
                throw new \Exception('添加失败');
            }

            if ($isDefault == 1) {
                // 查询目前的默认地址
                $defaultWhere[] = ['user_id', '=', $userId];
                $defaultWhere[] = ['default_flag', '=', 1];
                $defaultAddress = $userAddressModel->findOne($defaultWhere)['data'];
                if (!is_null($defaultAddress)) {
                    // 给原来的默认地址更新不默认
                    $oldParam['default_flag'] = 2;
                    $oldParam['update_time'] = date('Y-m-d H:i:s');
                    $userAddressModel->updateById($oldParam, $defaultAddress['id']);
                }
                $newParam['default_flag'] = 1;
                $newParam['update_time'] = date('Y-m-d H:i:s');
                $userAddressModel->updateById($newParam, $addressId);
            }

            Db::commit();
            return dataReturn(0, '添加成功');
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-4, '添加失败' . $e->getMessage());
        }
    }

    /**
     * Notes: 编辑用户收货地址
     * Author: LJS
     * @param $param
     * @return array|mixed
     */
    public function edit($param)
    {
        $addressValidate = new AddressValidate();
        if (!$addressValidate->scene('edit')->check($param)) {
            return dataReturn(-1, $addressValidate->getError());
        }

        $userId = $param['user_info']['id'] ?? 0;
        $addressId = $param['id'] ?? 0;
        $isDefault = null;
        if (isset($param['is_default'])) {
            $isDefault = $param['is_default'];
            unset($param['is_default']);
        }
        if ($userId == 0 || $addressId == 0) {
            return dataReturn(-1, '参数错误');
        }
        $userAddress = (new UserAddress())->findOne(['id' => $addressId])['data'];
        if (empty($userAddress)) {
            return dataReturn(-2, '该地址不存在');
        }
        if ($userAddress['user_id'] != $userId) {
            return dataReturn(-3, '该地址不属于当前用户');
        }

        unset($param['user_info']);
        $where[] = ['id', 'in', [$param['province_code'], $param['city_code'], $param['area_code']]];
        $res = (new SysCity())->getAllList($where, ['id', 'name'])['data'];
        if (empty($res)) {
            return dataReturn(-2, '省市区数据错误');
        }
        $codeNames = [];
        foreach ($res as $re) {
            $codeNames[$re['id']] = $re['name'];
        }

        Db::startTrans();
        try {

            $userAddressModel = new UserAddress();
            if ($isDefault == 1) {// 设置了默认
                // 查询目前的默认地址
                $defaultWhere[] = ['user_id', '=', $userId];
                $defaultWhere[] = ['default_flag', '=', 1];
                $defaultAddress = $userAddressModel->findOne($defaultWhere)['data'];
                if (!is_null($defaultAddress)) {
                    // 给原来的默认地址更新不默认
                    $oldParam['default_flag'] = 2;
                    $oldParam['update_time'] = date('Y-m-d H:i:s');
                    $userAddressModel->updateById($oldParam, $defaultAddress['id']);
                }
                $newParam['default_flag'] = 1;
            } else {
                $newParam['default_flag'] = $isDefault;
            }
            $newParam['update_time'] = date('Y-m-d H:i:s');
            $userAddressModel->updateById($newParam, $addressId);

            // 赋值省市区名称
            $param['province_name'] = $codeNames[$param['province_code']] ?? '';
            $param['city_name'] = $codeNames[$param['city_code']] ?? '';
            $param['area_name'] = $codeNames[$param['area_code']] ?? '';
            $param['update_time'] = date('Y-m-d H:i:s');
            (new UserAddress())->updateById($param, $addressId);
            Db::commit();
            return dataReturn(0, '编辑成功');
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-4, '编辑失败' . $e->getMessage());
        }
    }

    /**
     * Notes: 设置默认地址
     * Author: LJS
     * @param $param
     * @return mixed
     */
    public function setDefault($param)
    {
        $userId = $param['user_info']['id'] ?? 0;
        $addressId = $param['id'] ?? 0;
        if ($userId == 0 || $addressId == 0) {
            return dataReturn(-1, '参数错误');
        }
        $userAddressModel = new UserAddress();
        $where[] = ['id', '=', $addressId];
        $userAddress = $userAddressModel->findOne($where)['data'];
        if (empty($userAddress)) {
            return dataReturn(-2, '该地址不存在');
        }
        if ($userAddress['user_id'] != $userId) {
            return dataReturn(-3, '该地址不属于当前用户');
        }
        // 查询目前的默认地址
        $defaultWhere[] = ['user_id', '=', $userId];
        $defaultWhere[] = ['default_flag', '=', 1];
        $defaultAddress = $userAddressModel->findOne($defaultWhere)['data'];

        Db::startTrans();
        try {
            if (!is_null($defaultAddress)) {
                // 给原来的默认地址更新不默认
                $oldParam['default_flag'] = 2;
                $oldParam['update_time'] = date('Y-m-d H:i:s');
                $userAddressModel->updateById($oldParam, $defaultAddress['id']);
            }
            $newParam['default_flag'] = 1;
            $newParam['update_time'] = date('Y-m-d H:i:s');
            $userAddressModel->updateById($newParam, $addressId);

            Db::commit();
            return dataReturn(0, '设置成功');
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-4, '设置失败' . $e->getMessage());
        }
    }

    /**
     * Notes: 删除收货地址
     * Author: LJS
     * @param $param
     * @return mixed
     */
    public function delete($param)
    {
        $userId = $param['user_info']['id'] ?? 0;
        $addressId = $param['id'] ?? 0;
        if ($userId == 0 || $addressId == 0) {
            return dataReturn(-1, '参数错误');
        }
        $userAddressModel = new UserAddress();
        $where[] = ['id', '=', $addressId];
        $userAddress = $userAddressModel->findOne($where)['data'];
        if (empty($userAddress)) {
            return dataReturn(-2, '该地址不存在');
        }
        if ($userAddress['user_id'] != $userId) {
            return dataReturn(-3, '该地址不属于当前用户');
        }

        return $userAddressModel->delById($addressId);
    }
}