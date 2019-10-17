package com.loyer.example.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author kuangq
 * @projectName example
 * @title RedisUtil
 * @description redis工具类
 * @date 2019-08-02 10:08
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param redisConnectionFactory
     * @return org.springframework.data.redis.core.RedisTemplate
     * @author kuangq
     * @description 自定义序列化, 处理redisTemplate默认使用的jdkSerializeable存储二进制字节码问题
     * @date 2019-08-27 22:13
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置key和value的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * @param key
     * @return boolean
     * @author kuangq
     * @description 判断key值是否存在
     * @date 2019-08-02 10:35
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @return java.lang.Object
     * @author kuangq
     * @description 获取指定key值
     * @date 2019-08-02 10:35
     */
    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key
     * @param value
     * @return void
     * @author kuangq
     * @description 缓存key值
     * @date 2019-08-02 10:36
     */
    public void setValue(final String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @param key
     * @param expireTime
     * @return void
     * @author kuangq
     * @description 设置key值缓存时间
     * @date 2019-08-02 10:37
     */
    public void setExpireTime(final String key, Long expireTime) {
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param value
     * @param expireTime
     * @return void
     * @author kuangq
     * @description 缓存key值、设置缓存时间
     * @date 2019-08-02 10:36
     */
    public void setValue(final String key, Object value, Long expireTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param hashKey
     * @return java.lang.Object
     * @author kuangq
     * @description 获取哈希数据
     * @date 2019-08-02 10:39
     */
    public Object hashGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @return void
     * @author kuangq
     * @description 缓存哈希数据
     * @date 2019-08-02 10:39
     */
    public void hashSet(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }


    /**
     * @param key
     * @param start
     * @param end
     * @return java.util.List<java.lang.Object>
     * @author kuangq
     * @description 获取列表数据
     * @date 2019-08-02 10:40
     */
    public List<Object> listGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * @param key
     * @param value
     * @return void
     * @author kuangq
     * @description 缓存列表数据
     * @date 2019-08-02 10:40
     */
    public void listSet(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }


    /**
     * @param key
     * @return java.util.Set<java.lang.Object>
     * @author kuangq
     * @description 获取集合数据
     * @date 2019-08-02 10:40
     */
    public Set<Object> setGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @param key
     * @param value
     * @return void
     * @author kuangq
     * @description 缓存集合数据
     * @date 2019-08-02 10:41
     */
    public void setSet(String key, Object... value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @return java.util.Set<java.lang.Object>
     * @author kuangq
     * @description 获取有序集合数据
     * @date 2019-08-02 10:41
     */
    public Set<Object> zSetGet(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * @param key
     * @param value
     * @param score
     * @return void
     * @author kuangq
     * @description 缓存有序集合数据
     * @date 2019-08-02 10:41
     */
    public void zSetSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @param key
     * @return void
     * @author kuangq
     * @description 删除指定key值
     * @date 2019-08-02 10:42
     */
    public void remove(final String key) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * @param keys
     * @return void
     * @author kuangq
     * @description 批量删除key值
     * @date 2019-08-02 10:42
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
}
