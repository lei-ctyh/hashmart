<?php

namespace app\admin\controller\box;

use app\admin\controller\Base;
use app\admin\service\box\BlindboxService;

class Blindbox extends Base
{
    /**
     * 盲盒列表
     */
    public function index()
    {
        $blindboxService = new BlindboxService();
        return json($blindboxService->getBlindboxList(input('param.')));
    }

    /**
     * 添加盲盒
     */
    public function add()
    {
        $blindboxService = new BlindboxService();
        return json($blindboxService->addBlindbox(input('post.')));
    }

    /**
     * 编辑盲盒
     */
    public function edit()
    {
        $blindboxService = new BlindboxService();
        return json($blindboxService->editBlindbox(input('post.')));
    }

    /**
     * 删除盲盒
     */
    public function del()
    {
        $blindboxService = new BlindboxService();
        return json($blindboxService->delBlindbox(input('param.id')));
    }
}