<?php

namespace app\admin\service\system;

use app\model\system\SysAttachment;
use think\facade\App;
use think\facade\Filesystem;
use utils\Tools;

class PaySettingService
{
    /**
     * 上传配置文件
     * @param $file
     * @return array
     */
    public function uploadTxtFile($file)
    {
        // 上传到本地服务器
        try {

            $imageConf = config('images');
            $extMap = explode('|', $imageConf['txt']);

            // 检测文件后缀
            $ext = $file->getOriginalExtension();
            if (!in_array($ext, $extMap)) {
                return dataReturn(-3, '仅支持 ' . $imageConf['txt']. ' 格式的文件');
            }

            $imageModel = new SysAttachment();
            $has = $imageModel->findOne(['sha1' => $file->hash()])['data'];
            if (!empty($has)) {
                return dataReturn(0, '上传成功', ['url' => $has['url']]);
            }

            // 存到本地
            $saveName = Filesystem::disk('public')->putFile('local', $file);
            $saveName = str_replace('\\', '/', $saveName);

            $root = str_replace('\\', '/', App::getRootPath());
            return dataReturn(0, '上传成功', [
                'url' => $root . 'public/storage/' . $saveName,
                'name' => rtrim($file->getOriginalName(), '.zip')
            ]);
        } catch (\Exception $e) {
            return dataReturn(-1, $e->getMessage());
        }
    }
}