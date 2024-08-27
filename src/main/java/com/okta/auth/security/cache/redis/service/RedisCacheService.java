package com.okta.auth.security.cache.redis.service;

import com.okta.auth.security.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService implements ICacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheService(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void putInCache(String key, Object value, long expiration) {
        redisTemplate.opsForValue().set(key,value,expiration,TimeUnit.SECONDS);
    }

    @Override
    public void delKeyFromCache(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void updateKeyExpiration(String key, long expiration) {
        redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
    }
}
