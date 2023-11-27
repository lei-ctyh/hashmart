package com.wangyameng;


import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

/**
 * @author wangyameng
 */
@SpringBootApplication
@MapperScan("com.wangyameng.dao")
public class HmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmApiApplication.class, args);
        HashMap<String, Object> payload = new HashMap<>(4);
        payload.put("id", "1");
        payload.put("role_id", "1");
        payload.put("nickname", "admin");
        payload.put("avatar", "");
        String token = PubfuncUtil.setJWT(payload);
        System.out.println("接口验证Token: Bearer "+token);
    }


}
