package com.wangyameng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName LoginDto.java
 * @Description TODO
 * @createTime 2023-11-01 17:17:00
 */
@ApiModel(value = "后台登录请求体")
public class LoginDto {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不得为空")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "admin123")
    @NotBlank(message = "密码不得为空")
    private String password;
    @ApiModelProperty(value = "验证码", required = true, notes = "需要根据获取验证码接口返回值填入")
    @NotBlank(message = "验证码不得为空")
    private String captcha;
    @ApiModelProperty(value = "验证码存储在redis中的key值", required = true, notes = "需要根据获取验证码接口返回值填入")
    @NotBlank(message = "验证码存储在redis中的key不得为空")
    private String key;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
