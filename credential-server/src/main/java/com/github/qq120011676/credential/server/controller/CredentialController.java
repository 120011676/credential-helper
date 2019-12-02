package com.github.qq120011676.credential.server.controller;

import com.github.qq120011676.credential.common.entity.CredentialViewEntity;
import com.github.qq120011676.credential.server.entity.CredentialEntity;
import com.github.qq120011676.credential.server.entity.Result;
import com.github.qq120011676.credential.server.servicce.CredentialService;
import com.github.qq120011676.ladybird.web.exception.RestfulExceptionHelper;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("credential")
public class CredentialController {
    @Resource
    private CredentialService credentialService;
    @Resource
    private RestfulExceptionHelper restfulExceptionHelper;

    @RequestMapping("get")
    public CredentialViewEntity get(String timeout) {
        String key = UUID.randomUUID().toString();
        CredentialEntity credential = new CredentialEntity();
        credential.setToken(key);
        if (StringUtils.hasText(timeout)) {
            credential.setTimeout(DurationStyle.SIMPLE.parse(timeout));
        }
        this.credentialService.set(key, credential);
        return new CredentialViewEntity(credential.getToken());
    }

    @RequestMapping("gets")
    public List<CredentialViewEntity> get(int number, String timeout) {
        List<CredentialViewEntity> credentialViews = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            credentialViews.add(this.get(timeout));
        }
        return credentialViews;
    }

    @RequestMapping("check")
    public Result check(String token) {
        if (!StringUtils.hasText(token)) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("token_null_error");
        }
        CredentialEntity credential = this.credentialService.get(token);
        if (credential == null) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_not_found");
        }
        if (credential.getTimeout() != null && ZonedDateTime.now().isAfter(credential.getZonedDateTime().plusSeconds(credential.getTimeout().toSeconds()))) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_timeout_error");
        }
        return new Result(true);
    }

    @RequestMapping("verify")
    public Result verify(String token) {
        Result result = this.check(token);
        if (result.isStatus()) {
            this.credentialService.clean(token);
        }
        return result;
    }
}
