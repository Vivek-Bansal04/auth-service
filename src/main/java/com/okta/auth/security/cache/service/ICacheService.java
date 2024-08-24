package com.okta.auth.security.cache.service;

import org.springframework.stereotype.Service;

@Service
public interface ICacheService {
    Object getFromCache(String key);
    void putInCache(String key, Object value, long expiration);
    void delKeyFromCache(String key);
    void updateKeyExpiration(String key, long expiration);
}
