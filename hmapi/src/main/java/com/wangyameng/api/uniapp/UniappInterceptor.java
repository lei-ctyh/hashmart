package com.wangyameng.api.uniapp;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BaseInterceptor.java
 * @Description 微信小程序拦截器
 * @createTime 2023-11-14 15:56:00
 */
public class UniappInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 检查站点是否正则维护
        // 通过所有OPTION请求
        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            return true;
        } else {
            // 获取请求头中的token
            String token = request.getHeader("authorization");
            int jwtStrMinLen = 7;
            if (StringUtils.isBlank(token) || token.length()<jwtStrMinLen) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(AjaxResult.dataReturn(401, "登录过期"));
                return false;
            }
            JWT jwt = PubfuncUtil.getJWT(token.substring(jwtStrMinLen));
            if (jwt == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(AjaxResult.dataReturn(401, "登录过期"));
                return false;
            }
            JWTPayload payload = jwt.getPayload();
            JSONObject data = JSON.parseObject(payload.getClaim("data").toString()) ;

            if (data != null) {
                UserSessionContext.set(data);
                return true;
            }
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除用户session
        UserSessionContext.remove();
    }
}
