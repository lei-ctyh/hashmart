<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\AttachmentCateService;

class AttachmentCate extends Base
{
    /**
     * 附件分类列表
     */
    public function index()
    {
        $attachmentCateService = new AttachmentCateService();
        return json($attachmentCateService->getCateList());
    }

    /**
     * 添加附件分类
     */
    public function add()
    {
        $attachmentCateService = new AttachmentCateService();
        return json($attachmentCateService->addCate(input('post.')));
    }

    /**
     * 编辑附件分类
     */
    public function edit()
    {
        $attachmentCateService = new AttachmentCateService();
        return json($attachmentCateService->editCate(input('post.')));
    }

    /**
     * 删除附件分类
     */
    public function del()
    {
        $attachmentCateService = new AttachmentCateService();
        return json($attachmentCateService->delCate(input('param.id')));
    }
}