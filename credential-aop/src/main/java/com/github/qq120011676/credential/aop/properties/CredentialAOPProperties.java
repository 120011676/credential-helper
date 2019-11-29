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
     * credential参数名称
     */
    private String parameterName = "credentialToken";
}
