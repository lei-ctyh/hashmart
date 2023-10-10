<?php

namespace app\api\service\user;

use app\model\user\UserBalanceRechargeLog;
use strategy\pay\PayProvider;
use think\facade\Db;
use think\facade\Log;

class UserBalanceRechargeService
{
    /**
     * 用户充值记录
     * @param $param
     * @return array
     */
    public function getList($param)
    {
        // TODO 暂定1小时
        $this->overdueClose(3600);

        $orderModel = new UserBalanceRechargeLog();
        $userInfo = getJWT(getHeaderToken());
        $where[] = ['user_id', '=', $userInfo['id']];

        if ($param['type'] == 1) {
            $where[] = ['status', '=', 1];
        } else if ($param['type'] == 2) {
            $where[] = ['status', '=', 2];
        }

        $list = $orderModel->getPageList($param['limit'], $where, 'recharge_no,amount,pay_way,status,create_time');
        return pageReturn($list);
    }

    /**
     * 取消订单
     * @param $param
     * @return array
     */
    public function cancelOrder($param)
    {
        $userInfo = getJWT(getHeaderToken());

        $orderModel = new UserBalanceRechargeLog();
        $orderInfo = $orderModel->findOne([
            'user_id' => $userInfo['id'],
            'recharge_no' => $param['recharge_no'],
            'status' => 1
        ])['data'];

        if (empty($orderInfo)) {
            return dataReturn(-1, '订单信息异常');
        }

        return $orderModel->updateById([
            'status' => 5, // 取消
            'update_time' => now()
        ], $orderInfo['id']);
    }

    /**
     * 重新支付
     * @param $param
     * @return array
     */
    public function repay($param)
    {
        if (empty($param['recharge_no'])) {
            return dataReturn(-1, '订单号不能为空');
        }

        if (empty($param['pay_way'])) {
            return dataReturn(-2, '请选择支付方式');
        }

        $userInfo = getJWT(getHeaderToken());

        Db::startTrans();
        try {

            $rechargeModel = new UserBalanceRechargeLog();
            $orderInfo = $rechargeModel->where([
                'recharge_no' => $param['recharge_no'],
                'user_id' => $userInfo['id'],
                'status' => 1
            ])->find();

            $payNo = makeOrderNo('R');
            $rechargeModel->where('id', $userInfo['id'])->update([
                'pay_no' => $payNo
            ]);

            // 拼装参数执行支付
            $payProvider = (new PayProvider($param['pay_way']))->getStrategy();
            $param['host'] = getConfByType('api_url')['api_url'];

            $orderParam = [
                'user_id' => $userInfo['id'],
                'pay_price' => $orderInfo['amount'],
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

    /**
     * 超时订单关闭
     * @param $time
     * @return array
     */
    public function overdueClose($time)
    {
        $orderModel = new UserBalanceRechargeLog();
        return $orderModel->updateByWehere([
            'update_time' => now(),
            'status' => 4, // 过期
        ],[
            ['status', '=', 1],
            ['create_time', '<', date('Y-m-d H:i:s', time() - $time)]
        ]);
    }
}