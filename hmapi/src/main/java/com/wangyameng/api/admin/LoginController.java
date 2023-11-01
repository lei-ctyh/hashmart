package com.wangyameng.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.ServiceException;
import com.wangyameng.common.util.redis.RedisCacheUtil;
import com.wangyameng.dto.LoginDto;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @version 1.0
 * @description: TODO
 * @date 2023/10/18 21:42
 */

@Api(tags = "用户登录")
@RestController
public class LoginController {
    @Autowired
    private RedisCacheUtil redisCacheUtil;


    @PostMapping("admin/user.login/login")
    @ApiOperation(value = "用户登录请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginDto", value = "登录dto", required = true, dataType = "String", dataTypeClass = LoginDto.class, paramType = "body"),
    }
    )
    public AjaxResult login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getKey();

        // 获取redis中的验证码
        String redisCode = redisCacheUtil.getCacheObject(key);
        // 判断验证码
        if (captcha == null || !redisCode.equals(captcha.trim().toLowerCase())) {
            throw new ServiceException("验证码错误!");
        }
        return AjaxResult.success();
    }

    @ApiOperation(value = "获取登录验证码")
    @GetMapping("admin/user.login/captcha")
    public AjaxResult captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisCacheUtil.setCacheObject(key, verCode, 30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        JSONObject data = new JSONObject();
        data.put("key", key);
        data.put("img", specCaptcha.toBase64());
        return AjaxResult.success(data);
    }
}
