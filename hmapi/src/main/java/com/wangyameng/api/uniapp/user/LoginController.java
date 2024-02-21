package com.wangyameng.api.uniapp.user;

import com.wangyameng.common.core.AjaxResult;

import com.wangyameng.service.uniapp.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhanglei
 * @version 1.0
 * @description: TODO
 * @date 2024/2/20 21:17
 */

@RestController("uniappLoginController")
public class LoginController {
    @Resource(name = "uniappLoginService")
    LoginService loginService ;
    @PostMapping("uniapp/user/login/loginByWechat")
    public AjaxResult loginByWechat(String code, String phone_code) {
        return loginService.doLoginByWechat(code, phone_code);
    }
}
