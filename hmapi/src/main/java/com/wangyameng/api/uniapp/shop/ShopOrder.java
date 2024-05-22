package com.wangyameng.api.uniapp.shop;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName ShopOrder.java
 * @Description 商品订单
 * @createTime 2023-12-04 11:51:00
 */
@RestController
public class ShopOrder {
    @Autowired
    private ShopOrderService shopOrderService;

    @PostMapping("uniapp/order/shop/trail")
    public AjaxResult trail(int goods_id, String sku, int num, String address) {
        return shopOrderService.trail(goods_id, sku, num, address);
    }

    @PostMapping("uniapp/order/shop/createOrder")
    public AjaxResult createOrder(int goods_id, String sku, int num, String address_id, int pay_way) {
        return shopOrderService.createOrder(goods_id, sku, num, address_id, pay_way);
    }

    @PostMapping("uniapp/order/shop/payOrder")
    public AjaxResult payOrder(String platform, @RequestParam("pay_order_no") String payOrderNo) {
        return shopOrderService.payOrder(platform, payOrderNo);
    }

}
