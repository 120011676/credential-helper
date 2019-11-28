package com.github.qq120011676.token.server.servicce.impl;

import com.github.qq120011676.token.server.enitity.CredentialEntity;
import com.github.qq120011676.token.server.servicce.CredentialService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "credential")
public class CredentialServiceImpl implements CredentialService {
    @CachePut(key = "#token", unless = "#result == null")
    @Override
    public CredentialEntity setCredential(String token, CredentialEntity credential) {
        return credential;
    }

    @Cacheable(key = "#token", unless = "#result == null")
    @Override
    public CredentialEntity getCredential(String token) {
        return null;
    }

    @CacheEvict(key = "#token")
    @Override
    public CredentialEntity cleanCredential(String token) {
        return null;
    }
}
