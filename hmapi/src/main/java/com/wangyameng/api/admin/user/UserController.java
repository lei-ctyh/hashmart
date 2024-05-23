package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrDelBalenceReq;
import com.wangyameng.dto.AddOrDelIntegralReq;
import com.wangyameng.dto.AddOrEditUserReq;
import com.wangyameng.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2024-05-24 00:28:00
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/user.user/index")
    public AjaxResult index(String user_id, String nickname, String phone, @RequestParam(value = "create_time[]", required = false) List<String> create_time, String source_type, Integer page, Integer limit) {
        return userService.getList(user_id, nickname, phone, create_time, source_type, page, limit);
    }

    @PostMapping("/admin/user.user/add")
    public AjaxResult add(@RequestBody AddOrEditUserReq addOrEditUserReq) {
        return userService.add(addOrEditUserReq);
    }
    @PostMapping("/admin/user.user/edit")
    public AjaxResult edit(@RequestBody AddOrEditUserReq addOrEditUserReq) {
        return userService.edit(addOrEditUserReq);
    }
    @PostMapping("/admin/user.user/integral")
    public AjaxResult integral(@RequestBody AddOrDelIntegralReq  addOrDelIntegralReq) {
        return userService.addOrDelIntegral(addOrDelIntegralReq);
    }

    @PostMapping("/admin/user.user/balance")
    public AjaxResult balance(@RequestBody AddOrDelBalenceReq addOrDelBalenceReq) {
        return userService.addOrDelBalance(addOrDelBalenceReq);
    }
}
