<?php

namespace app\admin\service\log;

use app\model\user\UserLoginLog;

class LoginLogService
{
    /**
     * 写登录日志
     * @param $param
     * @return array
     */
    public function write($param)
    {
        $param['login_ip'] = request()->ip();
        $param['ua'] = isset(request()->header()['user-agent']) ? request()->header()['user-agent'] : '';
        $param['create_time'] = date('Y-m-d H:i:s');

        $loginLogModel = new UserLoginLog();
        return $loginLogModel->insertOne($param);
    }

    /**
     * 获取登录列表
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['nickname'])) {
            $where[] = ['nickname', '=', $param['nickname']];
        }

        if (!empty($param['user_id'])) {
            $where[] = ['user_id', '=', $param['user_id']];
        }

        try {
            $userLoginLogModel = new UserLoginLog();
            $list = $userLoginLogModel->getPageList($param['limit'], $where)['data'];

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}