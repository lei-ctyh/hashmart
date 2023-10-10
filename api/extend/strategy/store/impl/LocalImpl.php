<?php

namespace strategy\store\impl;

use strategy\store\StoreInterface;

class LocalImpl implements StoreInterface
{
    public function __construct($config)
    {

    }

    public function upload($path, $fileName)
    {
        // TODO: Implement upload() method.
    }

    /**
     * 删除文件
     * @param $path
     * @return array|mixed
     */
    public function del($path)
    {
        $delPathMap = getRealPath($path);
        foreach ($delPathMap as $vo) {

            $filePath = app()->getRootPath() . 'public/' . $vo;
            @unlink($filePath);
            removeEmptyDir(dirname($filePath));
        }

        return dataReturn(0, '删除成功');
    }
}