package com.wangyameng.api.uniapp.order;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.OrderService;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhanglei
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
    public AjaxResult trail(Integer blindbox_id, Integer num,Integer use_integral) {
        return orderService.trail(blindbox_id,num,use_integral,1);
    }
}
