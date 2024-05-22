package com.wangyameng.api.uniapp.order;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.OrderService;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName OrderController.java
 * @Description TODO
 * @createTime 2024-04-11 10:34:00
 */
@RestController
public class OrderController {
    @Resource(name = "uniappOrderService")
    OrderService orderService;

    @PostMapping("uniapp/order/trail")
    public AjaxResult trail(Integer blindbox_id, Integer num, Integer use_integral) {
        return orderService.trail(blindbox_id, num, use_integral, 1);
    }

    @PostMapping("/uniapp/order/createOrder")
    public AjaxResult createOrder(Integer blindbox_id, Integer num, Integer use_integral, Integer pay_way) {
        return orderService.createOrder(blindbox_id, num, use_integral, pay_way);
    }

    @PostMapping("/uniapp/order/payOrder")
    public AjaxResult payOrder(@RequestParam("order_no") String orderNo, String platform) {
        return orderService.payOrder(orderNo, platform);
    }

    @PostMapping("/uniapp/order/result")
    public AjaxResult result(@RequestParam("order_no") String orderNo, String platform) {
        return orderService.getResult(orderNo);
    }
}
