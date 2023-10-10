<?php

namespace strategy\store\impl;

use Qiniu\Auth;
use Qiniu\Storage\UploadManager;
use strategy\store\StoreInterface;

class QiniuImpl implements StoreInterface
{
    protected $config = null;

    public function __construct($config)
    {
        $this->config = $config;
    }

    /**
     * 上传
     * @param $path
     * @param $fileName
     * @return array|mixed
     */
    public function upload($path, $fileName)
    {
        try {

            // 构建鉴权对象
            $auth = new Auth($this->config['accesskey'], $this->config['secretkey']);
            // 生成上传 Token
            $token = $auth->uploadToken($this->config['qiniu_bucket']);
            // 初始化 UploadManager 对象并进行文件的上传。
            $uploadMgr = new UploadManager();
            // 调用 UploadManager 的 putFile 方法进行文件的上传。
            $uploadMgr->putFile($token, $fileName, $path, null, 'application/octet-stream', true, null, 'v2');
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0, '上传成功');
    }

    /**
     * 删除
     * @param $path
     * @return array|mixed
     */
    public function del($path)
    {
        try {

            $delPathMap = getRealPath($path);
            $auth = new Auth($this->config['accesskey'], $this->config['secretkey']);
            $config = new \Qiniu\Config();
            $bucketManager = new \Qiniu\Storage\BucketManager($auth, $config);

            $ops = $bucketManager->buildBatchDelete($this->config['qiniu_bucket'], $delPathMap);
            $bucketManager->batch($ops);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0, '删除成功');
    }
}