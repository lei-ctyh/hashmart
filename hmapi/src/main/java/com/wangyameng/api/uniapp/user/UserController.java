package com.wangyameng.api.uniapp.user;

import com.alibaba.fastjson2.JSONArray;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.service.uniapp.LoginService;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wangyameng
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
    public AjaxResult getUserInfo() {
        return userService.getUserInfo();
    }

    @PostMapping("uniapp/user/setInfo")
    public AjaxResult setUserInfo(String avatar, String nickname, String phone) {
        return userService.setUserInfo(avatar, nickname, phone);
    }

    @GetMapping("uniapp/user/orderRecordLog")
    public AjaxResult orderRecordLog( int page, int limit) {
        return userService.orderRecordLog(page, limit);
    }

    @GetMapping("uniapp/user/order/list")
    public AjaxResult orderList( String token, int page, int limit, int status) {

        return userService.orderList(token, page, limit, status);
    }

    @PostMapping("uniapp/user/updatehash")
    public AjaxResult updateHash(@RequestParam("token") String hash) {

        return userService.updateHash(hash);
    }

}
