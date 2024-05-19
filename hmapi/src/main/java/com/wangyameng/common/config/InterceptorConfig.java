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
        registry.addInterceptor(new UniappInterceptor())
                .addPathPatterns("/uniapp/**")
                .excludePathPatterns(
                        // 微信登录
                        "/uniapp/user/login/loginByWechat",
                        // 商城首页
                        "/uniapp/goods/shop/**",
                        // 支付回调地址
                        "/uniapp/notify/**",
                        // 首页
                        "/uniapp/home/index",
                        // 城市数据，音乐资源，客服联系方式等
                        "/uniapp/common/**",
                        // 盲盒货品详情
                        "/uniapp/goods/**",
                        // 省市区数据
                        "/uniapp/user/address/option");

        // 商户后台拦截
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user.login/**");

    }
}
