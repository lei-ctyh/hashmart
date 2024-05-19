package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.IntegralOrderDeliverReq;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName IntegralOrderService.java
 * @Description TODO
 * @createTime 2024-05-17 23:09:00
 */
public interface IntegralOrderService {
    AjaxResult getList(Integer status, String userName, String orderNo, String payOrderNo, Integer userId, Integer page, Integer limit);

    AjaxResult getExpressList();

    AjaxResult getDetail(String orderId);

    AjaxResult deliver(IntegralOrderDeliverReq integralOrderDeliverReq);
}
