package com.wangyameng.api.admin.order;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.BlindBoxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindBoxOrderController.java
 * @Description TODO
 * @createTime 2024-05-17 22:34:00
 */
@RestController
public class BlindBoxOrderController {
    @Autowired
    BlindBoxOrderService blindBoxOrderService;

    @GetMapping("/admin/order.blindBoxOrder/index")
    public AjaxResult index(String order_no, String pay_order_no, String user_name, String pay_status, Integer page, Integer limit){
        return blindBoxOrderService.getList(order_no, pay_order_no, user_name, pay_status, page, limit);
    }

    @GetMapping("/admin/order.blindBoxOrder/detail")
    public AjaxResult detail(String order_id){
        return blindBoxOrderService.detail(order_id);
    }
}
