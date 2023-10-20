package com.wangyameng.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName ww.java
 * @Description 接口文档路径 http://localhost:8888/swagger-ui/index.html#/
 * @createTime 2023-10-19 18:23:00
 */
@Configuration
public class SwaggerConfig {
    @Bean
    Docket docket(){
        // 设置 swagger的版本
        return new Docket(DocumentationType.OAS_30)
                // 选择生成接口文档
                .select()
                // 包所在的路径
                .apis(RequestHandlerSelectors.basePackage("com.wangyameng.api"))
                // 当前包下所有接口都生成
                .paths(PathSelectors.any())
                .build()
                // 接口文档初始化，也就是设置接口文档的详细信息，
                .apiInfo(
                        new ApiInfoBuilder()
                                .description("用于hashmart项目的前后端接口联调")
                                // 联系人
                                .contact(new Contact("wangyameng","https://www.cnblogs.com/aaalei/","2468341590@qq.com"))
                                .version("v1.0")
                                .title("HashMart 项目接口文档")
                                .license("Apache 2.0")
                                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                                .build()
                );
    }
}

