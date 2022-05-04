package com.utils;

import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //序列化器
    private final ObjectMapper map = new ObjectMapper();

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param outTime 过期时间，单位为分钟
     * @throws JsonProcessingException
     */
    public void setObject(String key, Object value,Integer outTime)  {
        //手动序列化
        String json = null;
        try {
            json = map.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringRedisTemplate.opsForValue().set(key, json,outTime, TimeUnit.MINUTES);
    }

    /**
     * 获取值
     * @param key 键
     * @param clazz 类型
     * @param <T> 泛型
     * @return 返回值，如果为空则返回null
     */
    public <T>  T getObject(String key ,Class<T> clazz){
        //获取数据
        String json1 = stringRedisTemplate.opsForValue().get(key);
        //手动反序列化
        try {
            return map.readValue(json1, clazz);
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }

    }

    /**
     * 删除缓存
     * @param key
     */
    public void removeValue(String key){
        stringRedisTemplate.delete(key);
    }



}
