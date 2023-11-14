package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.LoginDto;
import com.wangyameng.service.admin.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0
 * @description: TODO
 * @date 2023/10/18 21:42
 */

@Api(tags = "管理员登录")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;


    @PostMapping("admin/user.login/login")
    @ApiOperation(value = "发起登录")
    public AjaxResult login(@RequestBody @Validated LoginDto loginDto) throws Exception {
        return loginService.doLogin(loginDto);
    }

    @ApiOperation(value = "获取登录验证码")
    @GetMapping("admin/user.login/captcha")
    public AjaxResult captcha() {
        return loginService.createCaptcha();
    }
}
