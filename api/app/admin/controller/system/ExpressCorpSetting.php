<?php

namespace app\admin\controller\system;

use app\admin\controller\Base;
use app\admin\service\system\ExpressCorpSettingService;

class ExpressCorpSetting extends Base
{
    /**
     * 物流公司列表
     */
    public function index()
    {
        $ExpressCorpSettingService = new ExpressCorpSettingService();
        return json($ExpressCorpSettingService->getList(input('param.')));
    }

    /**
     * 物流公司添加
     */
    public function add()
    {
        $ExpressCorpSettingService = new ExpressCorpSettingService();
        return json($ExpressCorpSettingService->addExpressCorp(input('post.')));
    }

    /**
     * 物流公司编辑
     */
    public function edit()
    {
        $ExpressCorpSettingService = new ExpressCorpSettingService();
        return json($ExpressCorpSettingService->editExpressCorp(input('post.')));
    }

    /**
     * 物流公司删除
     */
    public function del()
    {
        $ExpressCorpSettingService = new ExpressCorpSettingService();
        return json($ExpressCorpSettingService->delExpressCorp(input('param.id')));
    }
}