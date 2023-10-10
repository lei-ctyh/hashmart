<?php

namespace strategy\store;

interface StoreInterface
{
    /**
     * 上传
     * @param $path
     * @param $fileName
     * @return mixed
     */
    public function upload($path, $fileName);

    /**
     * 删除
     * @param $path
     * @return mixed
     */
    public function del($path);
}