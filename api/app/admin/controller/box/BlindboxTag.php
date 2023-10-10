<?php

namespace app\admin\controller\box;

use app\admin\controller\Base;
use app\admin\service\box\BlindboxTagService;

class BlindboxTag extends Base
{
    /**
     * 盲盒标签列表
     */
    public function index()
    {
        $bindboxTagService = new BlindboxTagService();
        return json($bindboxTagService->getBlindboxTagList(input('param.')));
    }

    /**
     * 添加标签
     */
    public function add()
    {
        $bindboxTagService = new BlindboxTagService();
        return json($bindboxTagService->addBlindboxTag(input('post.')));
    }

    /**
     * 编辑标签
     */
    public function edit()
    {
        $bindboxTagService = new BlindboxTagService();
        return json($bindboxTagService->editBlindboxTag(input('post.')));
    }

    /**
     * 删除标签
     */
    public function del()
    {
        $bindboxTagService = new BlindboxTagService();
        return json($bindboxTagService->delBlindboxTag(input('param.id')));
    }
}