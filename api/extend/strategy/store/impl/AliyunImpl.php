<?php

namespace strategy\store\impl;

use OSS\OssClient;
use strategy\store\StoreInterface;

class AliyunImpl implements StoreInterface
{
    protected $ossObj = null;
    protected $bucket = null;

    public function __construct($config)
    {
        $this->bucket = $config['bucket'];
        $this->ossObj = new OssClient($config['accesskey_id'], $config['accesskey_secret'], $config['endpoint']);
    }

    /**
     * 上传
     * @param $path
     * @param $file
     * @return array|mixed
     */
    public function upload($path, $fileName)
    {
        try {

            $this->ossObj->uploadFile($this->bucket, $fileName, $path);
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
            $this->ossObj->deleteObjects($this->bucket, $delPathMap);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0, '删除成功');
    }
}