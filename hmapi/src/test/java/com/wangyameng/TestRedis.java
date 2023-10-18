package com.wangyameng;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author admin
 * @version 1.0
 * @description: TODO
 * @date 2023/10/18 23:22
 */
@SpringBootTest
public class TestRedis {
    private static final Logger logger = LoggerFactory.getLogger(TestRedis.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void contextLoads() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        // valueOperations.set("name", "lisi");
        System.out.println(valueOperations.get("name"));
    }
}

