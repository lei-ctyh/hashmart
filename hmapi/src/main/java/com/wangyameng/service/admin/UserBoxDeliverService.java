package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @date 2024年05月16日 22:49
 */
public interface UserBoxDeliverService {
    AjaxResult getList(String deliverNo, String status, String userId, Integer page, Integer limit);
}
