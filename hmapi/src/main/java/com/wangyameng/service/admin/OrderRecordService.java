package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

import java.util.List;

/**
 * @author zhanglei
 * @date 2024年05月16日 23:29
 */
public interface OrderRecordService {
    AjaxResult getList(Integer userId, String username, List<String> createTime, Integer page, Integer limit);
}
