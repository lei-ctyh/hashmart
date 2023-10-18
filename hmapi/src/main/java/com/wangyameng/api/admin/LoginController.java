package com.wangyameng.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.wangyameng.redis.RedisCache;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @version 1.0
 * @description: TODO
 * @date 2023/10/18 21:42
 */

@RestController
public class LoginController {
    @Autowired
    private RedisCache redisCache;

    @GetMapping("admin/user.login/captcha")
    public String captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisCache.setCacheObject(key,verCode,30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        JSONObject resp = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("key", key);
        data.put("img", specCaptcha.toBase64());
        resp.put("code", 0);
        resp.put("data", data);
        resp.put("msg", "success");
        return resp.toString();
    }
}
