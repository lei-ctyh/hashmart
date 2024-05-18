package com.wangyameng.api.admin.order;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.IntegralOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName IntegralOrderController.java
 * @Description TODO
 * @createTime 2024-05-17 23:04:00
 */
@RestController
public class IntegralOrderController {
    @Autowired
    private IntegralOrderService integralOrderService;
    @GetMapping("/admin/order.integralOrder/index")
    // status=&user_name=&order_no=&pay_order_no=&user_id=&page=1&limit
    public AjaxResult index(Integer status, String user_name, String order_no, String pay_order_no, Integer user_id, Integer page, Integer limit) {

        return integralOrderService.getList(status, user_name, order_no, pay_order_no, user_id, page, limit);
    }



}
