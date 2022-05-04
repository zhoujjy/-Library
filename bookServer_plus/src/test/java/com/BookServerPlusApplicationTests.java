package com;

import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.RecordService;
import com.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class BookServerPlusApplicationTests {


    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RecordService recordService;
    @Test
    void test() {
        System.out.println(recordService.indexShowData());
    }

}
