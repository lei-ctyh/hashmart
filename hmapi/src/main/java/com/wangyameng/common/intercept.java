package com.wangyameng.common;

import com.wangyameng.common.util.text.StringUtils;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName intercept.java
 * @Description 权限校验拦截器
 * @createTime 2023-11-04 14:44:00
 */
@Component
public class intercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("authorization");
        if (StringUtils.isNotBlank(token)) {
            token = token.substring(7);
        }
        return false;
    }
}
