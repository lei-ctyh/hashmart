package com.wangyameng.common.config;

import com.wangyameng.common.intercept.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName InterceptorConfig.java
 * @Description 拦截器配置
 * @createTime 2023-11-04 15:38:00
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user.login/**");
    }
}
