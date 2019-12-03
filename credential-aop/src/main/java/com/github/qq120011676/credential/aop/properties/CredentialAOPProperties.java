package com.github.qq120011676.credential.aop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "credential.aop")
public class CredentialAOPProperties {
    /**
     * aop表达式
     */
    private String before;
    /**
     * token参数名称
     */
    private String parameterTokenName = "credentialToken";
    /**
     * token header名称
     */
    private String headerTokenName = "Credential-Token";
    /**
     * 唯一参数名称
     */
    private String parameterUniqueName = "credentialUnique";
    /**
     * token header名称
     */
    private String headerUniqueName = "Credential-Unique";
}
