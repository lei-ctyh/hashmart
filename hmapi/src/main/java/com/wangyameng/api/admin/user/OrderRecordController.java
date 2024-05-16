package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName OrderRecordController.java
 * @Description TODO
 * @createTime 2024-05-16 23:26:00
 */

@RestController
public class OrderRecordController {
    @Autowired
    private OrderRecordService orderRecordService;
    @GetMapping("/admin/user.orderRecord/index")
    public AjaxResult index(Integer user_id, String username, @RequestParam(value = "create_time[]", required = false) List<String> create_time, Integer page, Integer limit) {
        return orderRecordService.getList(user_id, username, create_time, page, limit);
    }
}
