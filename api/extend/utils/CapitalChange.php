<?php

namespace utils;

use app\model\user\User;
use think\facade\Db;
use think\facade\Log;

class CapitalChange
{
    /**
     * 余额增长
     * @param $param
     * @return array
     */
    public static function balanceInc($param)
    {
        Db::startTrans();
        try {

            if ($param['amount'] < 0) {
                return dataReturn(-3, '余额信息错误');
            }

            $userModel = new User();
            $userInfo = $userModel->where('id', $param['user_id'])->lock(true)->find();

            // 增加余额
            $userModel->where('id', $param['user_id'])->inc('balance', $param['amount'])->update();

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('余额减少错误: ' . $e->getTraceAsString());
            return dataReturn(-2, '余额减少异常');
        }

        return dataReturn(0, 'success', $userInfo);
    }

    /**
     * 余额减少
     * @param $param
     * @return array
     */
    public static function balanceDec($param)
    {
        Db::startTrans();
        try {

            if ($param['amount'] < 0) {
                return dataReturn(-3, '余额信息错误');
            }

            $userModel = new User();
            $userInfo = $userModel->where('id', $param['user_id'])->lock(true)->find();
            if (empty($userInfo) || $userInfo['balance'] < $param['amount']) {
                Db::rollback();
                return dataReturn(-1, '余额不足');
            }

            // 扣减余额
            $userModel->where('id', $param['user_id'])->dec('balance', $param['amount'])->update();

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('余额减少错误: ' . $e->getTraceAsString());
            return dataReturn(-2, '余额减少异常');
        }

        return dataReturn(0, 'success', $userInfo);
    }

    /**
     * 哈希币增长
     * @param $param
     * @return array
     */
    public static function integralInc($param)
    {
        Db::startTrans();
        try {

            if ($param['amount'] < 0) {
                return dataReturn(-2, '哈希币金额错误');
            }

            $userModel = new User();
            $userInfo = $userModel->where('id', $param['user_id'])->lock(true)->find();

            // 增加余额
            $userModel->where('id', $userInfo['id'])->inc('integral', $param['amount'])->update();

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('哈希币增长错误: ' . $e->getTraceAsString());
            return dataReturn(-1, '哈希币扣除失败');
        }

        return dataReturn(0, 'success', $userInfo);
    }

    /**
     * 哈希币减少
     * @param $param
     * @return array
     */
    public static function integralDec($param)
    {
        Db::startTrans();
        try {

            if ($param['amount'] < 0) {
                return dataReturn(-1, '哈希币金额错误');
            }

            $userModel = new User();
            $userInfo = $userModel->where('id', $param['user_id'])->lock(true)->find();

            if ($userInfo['integral'] < $param['amount']) {
                Db::rollback();
                return dataReturn(-3, '您没有足够的哈希币');
            }

            // 扣除积分
            $userModel->where('id', $param['user_id'])->dec('integral', $param['amount'])->update();

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('哈希币减少错误: ' . $e->getTraceAsString());
            return dataReturn(-1, '哈希币扣除失败');
        }

        return dataReturn(0, 'success', $userInfo);
    }
}