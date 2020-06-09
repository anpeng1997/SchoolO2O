package cn.pengan.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;



    @Test
    void StringTest() {
        redisTemplate.opsForValue().set("springtest","springtest");

    }
}
