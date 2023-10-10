<?php

namespace app\admin\service\system;

use app\model\system\SysAttachment;
use utils\Tools;

class UploadService
{
    /**
     * 上传图片
     * @param $file
     * @return array
     */
    public function uploadPicture($file)
    {
        // 上传到本地服务器
        try {

            $imageConf = config('images');
            $extMap = explode('|', $imageConf['ext']);
            $mineMap = explode(',', $imageConf['acceptMime']);

            // 检测文件类型
            $mine = $file->getMime();
            if (!in_array($mine, $mineMap)) {
                return dataReturn(-2, '上传图片类型有误');
            }

            // 检测文件后缀
            $ext = $file->getOriginalExtension();
            if (!in_array($ext, $extMap)) {
                return dataReturn(-3, '上传图片类型有误');
            }

            $imageModel = new SysAttachment();
            $has = $imageModel->findOne(['sha1' => $file->hash()])['data'];
            if (!empty($has)) {
                return dataReturn(0, '上传成功', ['url' => $has['url']]);
            }

            // 存到本地
            $saveName = \think\facade\Filesystem::disk('public')->putFile('local', $file);
            $saveName = str_replace('\\', '/', $saveName);

            $url = request()->domain() . '/storage/' . $saveName;
            $storeWay = getConfByType('store')['store_way'];
            if ($storeWay != 'local') {
                $url = Tools::storeOSS($storeWay, $saveName);
            }

            // 入库
            (new SysAttachment())->insertOne([
                'url' => $url,
                'cate_id' => input('post.cate_id'),
                'file_type' => 1,
                'filename' => $file->getOriginalName(),
                'file_size' => $file->getSize(),
                'mime_type' => $mine,
                'storage' => $storeWay,
                'sha1' => $file->sha1(),
                'create_time' => now()
            ]);

            // TODO 水印

        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0, '上传成功', ['url' => $url]);
    }

    /**
     * 上传视频
     * @param $file
     * @return array
     */
    public function uploadVideo($file)
    {
        // 上传到本地服务器
        try {

            $imageConf = config('images');
            $extMap = explode('|', $imageConf['video_ext']);

            // 检测文件后缀
            $ext = $file->getOriginalExtension();
            if (!in_array($ext, $extMap)) {
                return dataReturn(-3, '仅支持 ' . $imageConf['video_ext']. ' 格式的视频');
            }

            $imageModel = new SysAttachment();
            $has = $imageModel->findOne(['sha1' => $file->hash()])['data'];
            if (!empty($has)) {
                return dataReturn(0, '上传成功', ['url' => $has['url']]);
            }

            // 存到本地
            $saveName = \think\facade\Filesystem::disk('public')->putFile('local', $file);
            $saveName = str_replace('\\', '/', $saveName);

            $url = request()->domain() . '/storage/' . $saveName;
            $storeWay = getConfByType('store')['store_way'];
            if ($storeWay != 'local') {
                $url = Tools::storeOSS($storeWay, $saveName);
            }

            // 入库
            (new SysAttachment())->insertOne([
                'url' => $url,
                'cate_id' => input('post.cate_id'),
                'file_type' => 2,
                'filename' => $file->getOriginalName(),
                'file_size' => $file->getSize(),
                'mime_type' => $file->getMime(),
                'storage' => $storeWay,
                'sha1' => $file->sha1(),
                'create_time' => now()
            ]);

            // TODO 水印

        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }

        return dataReturn(0, '上传成功', ['url' => $url]);
    }
}