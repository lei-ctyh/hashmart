package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName ShopOrderService.java
 * @Description TODO
 * @createTime 2024-04-29 00:28:00
 */
public interface ShopOrderService {
    public AjaxResult trail(int goodsId, String sku, int num, String address);

    AjaxResult createOrder(int goodsId, String sku, int num, String address, int payWay);

    AjaxResult payOrder(String platform,String payOrderNo);
}
