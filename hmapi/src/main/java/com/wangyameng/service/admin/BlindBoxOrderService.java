package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author wangyameng
 * @date 2024年05月17日 22:37
 */
public interface BlindBoxOrderService {
    AjaxResult getList(String orderNo, String payOrderNo, String userName, String payStatus, Integer page, Integer limit);

    AjaxResult detail(String orderId);
}
