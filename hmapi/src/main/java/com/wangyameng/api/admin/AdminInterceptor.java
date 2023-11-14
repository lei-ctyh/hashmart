package com.wangyameng.api.admin;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.ApplicationContextHelper;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.redis.RedisCacheUtil;
import com.wangyameng.common.util.text.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BaseInterceptor.java
 * @Description 后台JWT 拦截器
 * @createTime 2023-11-14 15:56:00
 */
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
            JSONObject data = (JSONObject) payload.getClaim("data");

            if (data != null) {
                String id;
                id = data.getStr("id");
                RedisCacheUtil redisCacheUtil = ApplicationContextHelper.popBean(RedisCacheUtil.class);
                String authKey =  id+"_auth_map" ;
                Map<String, Object> authMap = redisCacheUtil.getCacheMap(authKey);
                String authPre = "/admin/";
                String routeUrl = request.getRequestURI().replaceFirst(authPre, "");
                String authVal = (String) authMap.get(StringUtils.lowerCase(routeUrl));
                String correctAuthVal = "1";
                if (StringUtils.equals(correctAuthVal, authVal)) {
                    return true;
                } else {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(AjaxResult.dataReturn(403, "没有权限"));
                    return false;
                }
            }
            return false;
        }
    }
}
