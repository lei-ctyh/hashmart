package com.wangyameng.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangyameng
 * @version 1.0
 * @description: 为了解决跨域问题, 响应头添加Access-Control-Allow-Origin", "*"
 * @date 2023/10/19 0:17
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(false)
                .maxAge(3600)
                .allowedHeaders("*");
    }

}
