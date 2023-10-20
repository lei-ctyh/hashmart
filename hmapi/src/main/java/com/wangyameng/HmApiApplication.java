package com.wangyameng;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author wangyameng
 */
@SpringBootApplication
@MapperScan("com.wangyameng.dao")
@EnableOpenApi
public class HmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmApiApplication.class, args);

    }


}
