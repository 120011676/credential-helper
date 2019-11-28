package com.github.qq120011676.credential.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "credential.client")
public class CredentialClientProperties {
    private String baseUrl;
}
