package com.wangyameng;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangyameng
 */
@SpringBootApplication
@MapperScan("com.wangyameng.dao")
public class HmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmApiApplication.class, args);

    }


}
