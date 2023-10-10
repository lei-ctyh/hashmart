<?php

namespace strategy\store\impl;

use Qcloud\Cos\Client;
use strategy\store\StoreInterface;

class QCloudImpl implements StoreInterface
{
    protected $qCloudObj = null;
    protected $config = [];

    public function __construct($config)
    {
        $this->config = $config;

        $this->qCloudObj = new Client(
            [
                'region' => $config['tencent_endpoint'],
                'credentials'=> [
                    'appId'      => $config['tencent_appid'],
                    'secretId'  => $config['secret_id'] ,
                    'secretKey' => $config['secret_key']
                ]
            ]
        );
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

            $file = fopen($path, 'rb');
            if ($file) {
                $this->qCloudObj->Upload($this->config['tencent_bucket'], $fileName, $file);
            }
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
            $delPathMaps = [];
            foreach ($delPathMap as $vo) {
                $delPathMaps[] = [
                    'Key' => $vo
                ];
            }

            $this->qCloudObj->deleteObjects([
                'Bucket' => $this->config['tencent_bucket'],
                'Objects' => $delPathMaps
            ]);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0, '删除成功');
    }
}