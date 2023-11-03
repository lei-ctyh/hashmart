package com.wangyameng.common.config;

import cn.hutool.jwt.JWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName JwtConfig.java
 * @Description TODO
 * @createTime 2023-11-03 16:36:00
 */
@Configuration
public class JwtConfig {
    String key = "HashMart20221225!@#";
    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
