<?php

namespace app\api\service\user;

use app\api\service\order\OrderService;
use app\api\validate\user\UserValidate;
use app\model\order\Order;
use app\model\order\OrderRecord;
use app\model\user\User;
use app\model\user\UserBoxDeliver;

class UserService
{
    /**
     * Notes: 获取用户信息
     * Author: LJS
     * @return mixed
     */
    public function getUserInfo()
    {
        if (!checkOpen()) {
            return dataReturn(404, '站点正在维护');
        }

        $token = getHeaderToken();
        $tokenData = getJWT($token);

        $userModel = new User();
        $userInfo = $userModel->findOne([
            ['id', '=', $tokenData['id']],
            ['status', '=', 1]
        ], 'id,nickname,phone,avatar,integral,balance,hash_key');

        if (! is_null($userInfo['data'])) {
            // 查询待付款订单数
            $userInfo['data']['unpaid_orders'] = (new Order())->where([
                ['id', '=', $tokenData['id']],
                ['pay_status', '=', 1]
            ])->count('id');
            // 待收货订单数
            $userInfo['data']['unreceived_orders'] = (new Order())->where([
                ['id', '=', $tokenData['id']],
                ['status', '=', 3]
            ])->count('id');
        }

        return $userInfo;
    }

    /**
     * Notes: 更新用户信息
     * Author: LJS
     * @param $param
     * @return array|mixed
     */
    public function setUserInfo($param)
    {
        $userValidate = new UserValidate();
        if (isset($param['avatar'])) {
            if (empty($param['avatar'])) {
                return dataReturn(-1, '参数错误');
            }else{
                if (! $userValidate->scene('edit-avatar')->check($param)) {
                    return dataReturn(-1, $userValidate->getError());
                }
            }
        }
        if (isset($param['nickname'])) {
            if (empty($param['nickname'])) {
                return dataReturn(-1, '参数错误');
            }else{
                if (! $userValidate->scene('edit-nickname')->check($param)) {
                    return dataReturn(-1, $userValidate->getError());
                }
            }
        }
        if (isset($param['phone'])) {
            if (empty($param['phone'])) {
                return dataReturn(-1, '参数错误');
            }else{
                if (! $userValidate->scene('edit-phone')->check($param)) {
                    return dataReturn(-1, $userValidate->getError());
                }
            }
        }

        $userModel = new User();
        $userInfo = $userModel->findOne([
            'id' => $param['user_info']['id']
        ])['data'];
        if (empty($userInfo)) {
            return dataReturn(-1, '该用户不存在');
        }
        if (isset($param['avatar'])) { // todo: 前端只提交固定的几个图片key，然后拼接
            $userModel->updateById([
                'avatar' => $param['avatar'],
                'update_time' => date('Y-m-d H:i:s')
            ], $userInfo['id']);
        }
        if (isset($param['nickname'])) {
            $userModel->updateById([
                'nickname' => $param['nickname'],
                'update_time' => date('Y-m-d H:i:s')
            ], $userInfo['id']);
        }
        if (isset($param['phone'])) {
            $userModel->updateById([
                'phone' => $param['phone'],
                'update_time' => date('Y-m-d H:i:s')
            ], $userInfo['id']);
        }

        return dataReturn(0, '操作成功');
    }

    /**
     * Notes: 用户开盒记录
     * Author: LJS
     * @param $param
     * @return mixed
     */
    public function orderRecordLog($param)
    {
        $orderRecordModel = new OrderRecord();
        $where = [];
        $where[] = ['user_id', '=', $param['user_id']];

        return $orderRecordModel->with(['box', 'detail'])
            ->field('id,blindbox_id,create_time')
            ->where($where)->order("id desc")->paginate($param['limit'] ?? 10);
    }

    /**
     * Notes: 我的订单列表
     * Author: LJS
     * @param $param
     * @return mixed
     */
    public function orderList($param)
    {
        // TODO 暂定1小时
        (new OrderService())->overdueClose(3600);

        $userInfo = getJWT(getHeaderToken());
        $where[] = ['user_id', '=', $userInfo['id']];

        // 待支付
        if ($param['status'] == 1) {

            $orderModel = new Order();
            $where[] = ['type', '=', 2];
            $where[] = ['status', '=', 1];
            $list = $orderModel->field('order_no,pay_price,status,total_num,blindbox_id')->with('blindbox')->where($where)->order('id desc')
                ->paginate($param['limit']);
            return pageReturn(dataReturn(0, '', $list));
        } else {

            if ($param['status'] == 2) {
                $where[] = ['status', '=', 1];
            } else {
                $where[] = ['status', '=', 2];
            }

            $deliverModel = new UserBoxDeliver();
            $list = $deliverModel->field('id,deliver_no,status,create_time as deliver_time')
                ->with(['detail.rewardSimple'])->where($where)->order('id desc')->paginate($param['limit']);

            return pageReturn(dataReturn(0, 'success', $list));
        }
    }

    /**
     * 更新hash种子
     * @param $token
     * @return array
     */
    public function updateToken($token)
    {
        if (empty($token) || mb_strlen($token) > 50) {
            return dataReturn(-1, '种子的长度应该小于50个字');
        }

        $userInfo = getJWT(getHeaderToken());
        $userModel = new User();
        return $userModel->updateByWehere(['hash_key' => $token], ['id' => $userInfo['id']]);
    }
}