package com.wangyameng.common.intercept;

import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName JwtInterceptor.java
 * @Description JWT 拦截器
 * @createTime 2023-11-04 15:40:00
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过所有OPTION请求
        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            return true;
        } else {
            // 获取请求头中的token
            String token = request.getHeader("token");
            PubfuncUtil.getJWT(token);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("{}");
            return false;
        }
    }
}
