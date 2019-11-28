package com.github.qq120011676.token.server.controller;

import com.github.qq120011676.ladybird.web.exception.RestfulExceptionHelper;
import com.github.qq120011676.token.server.enitity.CredentialEntity;
import com.github.qq120011676.token.server.servicce.CredentialService;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("credential")
public class CredentialController {
    @Resource
    private CredentialService credentialService;
    @Resource
    private RestfulExceptionHelper restfulExceptionHelper;

    @RequestMapping("get")
    public CredentialEntity get(String timeout) {
        String key = UUID.randomUUID().toString();
        CredentialEntity credential = new CredentialEntity();
        credential.setToken(key);
        if (StringUtils.hasText(timeout)) {
            credential.setDuration(DurationStyle.SIMPLE.parse(timeout));
        }
        return this.credentialService.setCredential(key, credential);
    }

    @RequestMapping("check")
    public boolean check(String token) {
        if (!StringUtils.hasText(token)) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("token_null_error");
        }
        CredentialEntity credential = this.credentialService.getCredential(token);
        if (credential == null) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_not_found");
        }
        if (credential.getDuration() != null && ZonedDateTime.now().isAfter(credential.getZonedDateTime().plusSeconds(credential.getDuration().toSeconds()))) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_timeout_error");
        }
        return true;
    }

    @RequestMapping("verify")
    public boolean verify(String token) {
        boolean bol = this.check(token);
        if (bol) {
            this.credentialService.cleanCredential(token);
        }
        return bol;
    }
}
