<?php

namespace app\admin\service\system;

use app\model\system\SysAttachment;
use strategy\store\StoreProvider;

class AttachmentService
{
    /**
     * 获取资源列表
     * @param $param
     * @return array
     */
    public function getAttachmentList($param)
    {
        $where = [];
        if (!empty($param['cate_id'])) {
            $where[] = ['cate_id', '=', $param['cate_id']];
        }

        $attachmentModel = new SysAttachment();
        $list = $attachmentModel->getPageList($param['limit'], $where)['data'];

        return pageReturn(dataReturn(0, 'success', $list));
    }

    /**
     * 删除资源
     * @param $ids
     * @return array
     */
    public function delAttachmentByIds($ids)
    {
        $attachmentModel = new SysAttachment();
        $where[] = ['id', 'in', $ids];
        $resourceList = $attachmentModel->getAllList($where)['data'];

        $storeWayMap = [];
        foreach ($resourceList as $file) {
            $storeWayMap[$file['storage']][] = $file['url'];
        }

        $storeConfigMap = config('shop.store_config');
        foreach ($storeWayMap as $key => $vo) {
            $config = getConfByType($storeConfigMap[$key]);
            $provider = new StoreProvider($key, $config);
            $provider->getStrategy()->del($vo);
        }

        $attachmentModel->delByIds($ids);

        return dataReturn(0, '删除成功');
    }
}