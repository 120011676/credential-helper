package com.github.qq120011676.credential.client.autoconfigure;

import com.github.qq120011676.credential.client.CredentialClient;
import com.github.qq120011676.credential.client.properties.CredentialClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass(CredentialClient.class)
@EnableConfigurationProperties(CredentialClientProperties.class)
public class CredentialClientAutoconfigure {
    @Resource
    private CredentialClientProperties credentialClientProperties;

    @Bean
    @ConditionalOnMissingBean(CredentialClient.class)
    public CredentialClient credentialClient() {
        return new CredentialClient(this.credentialClientProperties.getBaseUrl());
    }
}
