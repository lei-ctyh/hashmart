<?php

namespace app\admin\service\log;

use app\model\system\SysAdminLog;

class AdminLogService
{
    /**
     * 写操作日志
     * @param $param
     * @param $data
     * @return array
     */
    public function write($param, $data)
    {
        if (empty($data)) {
            return dataReturn(0);
        }

        if (empty($param)) {
            $userInfo = getJWT(getHeaderToken());
            if (empty($userInfo)) {
                return dataReturn(0);
            }
            $param['admin_id'] = $userInfo['id'];
            $param['username'] = $userInfo['nickname'];
            $param['title'] = '后台操作';
        }

        $param['url'] = request()->controller() . '/' . request()->action();
        $param['ip'] = request()->ip();
        $param['data'] = $data;
        $param['user_agent'] = isset(request()->header()['user-agent']) ? request()->header()['user-agent'] : '';
        $param['create_time'] = date('Y-m-d H:i:s');

        $sysAdminLogModel = new SysAdminLog();
        $sysAdminLogModel->insert($param);

        return dataReturn(0);
    }

    /**
     * Notes: 管理员日志列表
     * Author: LJS
     * @param $param
     * @return array
     */
    public function getList($param): array
    {
        $where = [];
        if (!empty($param['username'])) {
            $where[] = ['username', '=', $param['username']];
        }

        try {
            $adminLogModel = new SysAdminLog();
            $list = $adminLogModel->where($where)->order('id desc')->paginate($param['limit']);

            return pageReturn(dataReturn(0, 'success', $list));
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}