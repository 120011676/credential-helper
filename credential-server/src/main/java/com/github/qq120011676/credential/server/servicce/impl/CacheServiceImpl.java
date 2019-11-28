package com.github.qq120011676.credential.server.servicce.impl;

import com.github.qq120011676.credential.server.servicce.CacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "cache")
public class CacheServiceImpl implements CacheService {
    @CachePut(key = "#key", unless = "#result == null")
    @Override
    public String set(String key, String content) {
        return content;
    }

    @Cacheable(key = "#key", unless = "#result == null")
    @Override
    public String get(String key) {
        return null;
    }

    @CacheEvict(key = "#key")
    @Override
    public void clean(String key) {
    }
}
