package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.UserBoxDeliverReq;

/**
 * @author wangyameng
 * @date 2024年05月16日 22:49
 */
public interface UserBoxDeliverService {
    AjaxResult getList(String deliverNo, String status, String userId, Integer page, Integer limit);

    AjaxResult deliver(UserBoxDeliverReq userBoxDeliverReq);

    AjaxResult getOrderInfo(Integer id);
}
