package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditBlindboxTagReq;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BindboxTagService.java
 * @Description TODO
 * @createTime 2024-05-14 00:28:00
 */
public interface BindboxTagService {
    AjaxResult getBlindboxTagList(Integer page, Integer limit, String name);

    AjaxResult addBlindboxTag(AddOrEditBlindboxTagReq name);

    AjaxResult editBlindboxTag(AddOrEditBlindboxTagReq req);

    AjaxResult deleteBlindboxTag(Integer id);
}
