package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @date 2024年05月16日 22:19
 */
public interface BoxExchangeService {
    AjaxResult getList(Integer userId, String username, String exchangeNo, Integer page, Integer limit);

    AjaxResult getDetail(Integer exchangeId);
}
