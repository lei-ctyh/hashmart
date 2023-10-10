<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\AttachmentService;
use app\admin\service\system\UploadService;

class Attachment extends Base
{
    /**
     * 资源列表
     */
    public function index()
    {
        $attachmentService = new AttachmentService();
        return json($attachmentService->getAttachmentList(input('param.')));
    }

    /**
     * 上传图片
     */
    public function uploadPicture()
    {
        $file = request()->file('file');

        $uploadService = new UploadService();
        return json($uploadService->uploadPicture($file));
    }

    /**
     * 上传视频
     */
    public function uploadVideo()
    {
        $file = request()->file('file');

        $uploadService = new UploadService();
        return json($uploadService->uploadVideo($file));
    }

    /**
     * 删除资源
     */
    public function del()
    {
        $attachmentService = new AttachmentService();
        return json($attachmentService->delAttachmentByIds(input('param.ids')));
    }
}