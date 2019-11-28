package com.github.qq120011676.token.server.enitity;

import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
public class CredentialEntity implements Serializable {
    private String token;
    private ZonedDateTime zonedDateTime = ZonedDateTime.now();
    private Duration duration;
}
