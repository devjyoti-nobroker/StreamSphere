package com.nobroker.streamSphere.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testSendMail() {
        redisTemplate.opsForValue().set("tree", "emamail");
        //automatically got connected
        String trial = redisTemplate.opsForValue().get("tree");
        System.out.println("Fetched from Redis: " + trial);
    }
}
