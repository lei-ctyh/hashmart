package com.wangyameng.common.config;

import com.wangyameng.api.admin.AdminInterceptor;
import com.wangyameng.api.uniapp.UniappInterceptor;
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
        // 商户后台拦截
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user.login/**");

        registry.addInterceptor(new UniappInterceptor())
                .addPathPatterns("/uniapp/**")
                //
                .excludePathPatterns("/uniapp/user/login/loginByWechat","/uniapp/user/address/option");
    }
}
