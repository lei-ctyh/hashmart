package com.wangyameng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName LoginDto.java
 * @Description TODO
 * @createTime 2023-11-01 17:17:00
 */
@ApiModel(value = "后台登录请求体")
public class LoginDto {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)

    private String captcha;
    @ApiModelProperty(value = "获取验证码图片返回的对应的redis中存储的key", required = true)
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
