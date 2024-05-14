package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditBindboxReq;

/**
 * @author zhanglei
 * @date 2024年05月14日 21:34
 */
public interface BindboxService {
    AjaxResult getBlindboxList(Integer page, Integer limit, String name);

    AjaxResult addBlindbox(AddOrEditBindboxReq req);

    AjaxResult editBlindbox(AddOrEditBindboxReq req);

    AjaxResult delBlindbox(Integer id);
}
