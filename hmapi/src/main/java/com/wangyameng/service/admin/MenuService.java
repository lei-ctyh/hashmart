package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditMenuReq;

/**
 * @author wangyameng
 * @date 2024年05月23日 22:10
 */
public interface MenuService {

    AjaxResult getAllMenu();

    AjaxResult addMenu(AddOrEditMenuReq addOrEditMenuReq);

    AjaxResult editMenu(AddOrEditMenuReq addOrEditMenuReq);

    AjaxResult delMenu(Integer id);
}
