package com.wangyameng.api.uniapp.user;

import com.alibaba.fastjson2.JSONArray;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.service.uniapp.LoginService;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("uniapp/user/setInfo")
    public AjaxResult setUserInfo(String avatar, String nickname, String phone, @RequestHeader("authorization") String token) {
        return userService.setUserInfo(avatar, nickname, phone, token);
    }
}
