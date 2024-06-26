package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.UserBoxDeliverReq;
import com.wangyameng.entity.UserBoxDeliver;
import com.wangyameng.service.admin.UserBoxDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserBoxDeliverController.java
 * @Description TODO
 * @createTime 2024-05-16 22:46:00
 */
@RestController
public class UserBoxDeliverController {
    @Autowired
    private UserBoxDeliverService userBoxDeliverService;

    @GetMapping("/admin/user.userBoxDeliver/index")
    public AjaxResult index(String deliver_no, String status, String user_id, Integer page, Integer limit) {

        return userBoxDeliverService.getList(deliver_no, status, user_id, page, limit);
    }

    @PostMapping("/admin/user.userBoxDeliver/deliver")
    public AjaxResult deliver(@RequestBody UserBoxDeliverReq userBoxDeliverReq) {

        return userBoxDeliverService.deliver(userBoxDeliverReq);
    }

    @GetMapping("/admin/user.userBoxDeliver/expressOrder")
    public AjaxResult expressOrder(Integer id) {
        return userBoxDeliverService.getOrderInfo(id);
    }


}
