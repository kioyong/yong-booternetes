package com.example;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {TestRedisConfiguration.class})
public class RedisTest {


    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test() {
        String value = redisTemplate.opsForValue().get("key");
        assertThat(value).isNull();
        redisTemplate.opsForValue().set("key", "value");
        value = redisTemplate.opsForValue().get("key");
        assertThat(value).isEqualTo("value");

    }
}
