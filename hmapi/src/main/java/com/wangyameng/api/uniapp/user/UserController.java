package com.wangyameng.api.uniapp.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.LoginService;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2024-02-21 14:55:00
 */


@RestController("uniappUserController")
public class UserController {
    @Resource(name = "uniappUserService")
    UserService userService;
    @PostMapping("uniapp/user/getInfo")
    public AjaxResult getUserInfo(@RequestHeader("authorization") String token) {
        return userService.getUserInfo(token);
    }
}
