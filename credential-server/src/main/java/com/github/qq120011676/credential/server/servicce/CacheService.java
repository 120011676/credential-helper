package com.github.qq120011676.credential.server.servicce;

public interface CacheService {
    String set(String key, String content);

    String get(String key);

    void clean(String key);
}
