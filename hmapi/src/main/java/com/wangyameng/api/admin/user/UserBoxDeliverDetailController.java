package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.UserBoxDeliverDetailService;
import com.wangyameng.service.admin.UserBoxDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserBoxDeliverDetailController.java
 * @Description TODO
 * @createTime 2024-05-16 23:01:00
 */
@RestController
public class UserBoxDeliverDetailController {
    @Autowired
    private UserBoxDeliverDetailService userBoxDeliverDetailService;
    // /admin/user.userBoxDeliverDetail/index?deliver_id=75&page=1&limit=15
    @GetMapping("/admin/user.userBoxDeliverDetail/index")
    public AjaxResult index(String deliver_id,Integer page, Integer limit) {

        return userBoxDeliverDetailService.getList(deliver_id, page, limit);
    }
}
