package com.github.qq120011676.credential.server.servicce.impl;

import com.github.qq120011676.credential.common.entity.CredentialEntity;
import com.github.qq120011676.credential.server.servicce.CacheService;
import com.github.qq120011676.credential.server.servicce.CredentialService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@CacheConfig(cacheNames = "credential")
public class CredentialServiceImpl implements CredentialService {
    @Resource
    private CacheService cacheService;

    @Override
    public String set(String key, CredentialEntity credential) {
        JsonObject json = new JsonObject();
        json.addProperty("token", credential.getToken());
        json.addProperty("zonedDateTime", credential.getZonedDateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        if (credential.getTimeout() != null) {
            json.addProperty("timeout", credential.getTimeout().toString());
        }
        return this.cacheService.set(key, json.toString());
    }

    @Override
    public CredentialEntity get(String key) {
        String json = this.cacheService.get(key);
        if (StringUtils.hasText(json)) {
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            CredentialEntity credential = new CredentialEntity();
            credential.setToken(jsonObject.get("token").getAsString());
            credential.setZonedDateTime(ZonedDateTime.parse(jsonObject.get("zonedDateTime").getAsString(), DateTimeFormatter.ISO_ZONED_DATE_TIME));
            JsonElement timeoutJsonElement = jsonObject.get("timeout");
            if (timeoutJsonElement != null) {
                credential.setTimeout(Duration.parse(timeoutJsonElement.getAsString()));
            }
            return credential;
        }
        return null;
    }

    @Override
    public void clean(String key) {
        this.cacheService.clean(key);
    }
}
