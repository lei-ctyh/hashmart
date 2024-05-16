package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @date 2024年05月16日 23:02
 */
public interface UserBoxDeliverDetailService {
    AjaxResult getList(String deliverId, Integer page, Integer limit);
}
