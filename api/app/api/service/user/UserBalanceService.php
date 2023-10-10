<?php

namespace app\api\service\user;

use app\model\user\User;
use app\model\user\UserBalanceChangeLog;
use app\model\user\UserBalanceRechargeLog;
use strategy\pay\PayProvider;
use think\facade\Db;
use think\facade\Log;

class UserBalanceService
{
    /**
     * 获取余额记录
     * @param $param
     * @return array
     */
    public function getBalanceLogList($param)
    {
        $where = [];
        if (!empty($param['type'])) {
            if ($param['type'] == 1) {
                $where[] = ['type', '=', 1];
            } else {
                $where[] = ['type', 'in', [2, 3]];
            }
        }

        if (!empty($param['month'])) {
            $lastDay = date('t', strtotime($param['month']));
            $where[] = ['create_time', 'between', [$param['month'] . '-01 00:00:00', $param['month'] . '-' . $lastDay . ' 23:59:59']];
        }

        $userInfo = getJWT(getHeaderToken());
        $where[] = ['user_id', '=', $userInfo['id']];

        $userBalanceLogModel = new UserBalanceChangeLog();
        $list = $userBalanceLogModel->getPageList($param['limit'], $where);

        return pageReturn($list);
    }

    /**
     * 获取余额记录
     * @return array
     */
    public function getBalanceInfo()
    {
        $userInfo = getJWT(getHeaderToken());
        $userBalanceLogModel = new UserBalanceChangeLog();

        // 累计消费
        $totalSpend = $userBalanceLogModel->where('user_id', $userInfo['id'])->where('type', 1)->sum('balance');
        // 累计充值
        $totalRecharge = $userBalanceLogModel->where('user_id', $userInfo['id'])->whereIn('type', [2, 3])->sum('balance');
        // 当前余额
        $balance = (new User())->findOne(['id' => $userInfo['id']], 'balance')['data']->balance;

        return dataReturn(0, 'success', compact('totalSpend', 'totalRecharge', 'balance'));
    }

    /**
     * 创建余额充值订单
     * @param $param
     * @return array
     */
    public function createOrder($param)
    {
        if (empty($param['amount']) || $param['amount'] < 0) {
            return dataReturn(-1, '充值金额应大于0');
        }

        if (empty($param['pay_way'])) {
            return dataReturn(-2, '请选择支付方式');
        }

        $userInfo = getJWT(getHeaderToken());

        Db::startTrans();
        try {

            $rechargeModel = new UserBalanceRechargeLog();
            $orderNo = makeOrderNo('R');
            $payNo = makeOrderNo('R');
            $rechargeModel->insertOne([
                'recharge_no' => $orderNo,
                'pay_no' => $payNo,
                'user_id' => $userInfo['id'],
                'username' => $userInfo['nickname'],
                'amount' => $param['amount'],
                'pay_way' => $param['pay_way'],
                'status' => 1, // 待支付
                'create_time' => now()
            ]);

            // 拼装参数执行支付
            $payProvider = (new PayProvider($param['pay_way']))->getStrategy();
            $param['host'] = getConfByType('api_url')['api_url'];

            $orderParam = [
                'user_id' => $userInfo['id'],
                'pay_price' => $param['amount'],
                'host' => $param['host'],
                'pay_order_no' => $payNo,
                'subject' => '余额充值'
            ];

            if ($param['platform'] == 'miniapp') {
                $res = $payProvider->miniPay($orderParam);
            } else {
                $res = $payProvider->appPay($orderParam);
            }

            Db::commit();
            return $res;
        } catch (\Exception $e) {
            Db::rollback();
            Log::error('余额充值订单创建失败：' . $e->getMessage() . '>>>>' . $e->getTraceAsString());
            return dataReturn(-1, '订单创建失败');
        }
    }
}