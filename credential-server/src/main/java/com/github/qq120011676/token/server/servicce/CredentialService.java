package com.github.qq120011676.token.server.servicce;

import com.github.qq120011676.token.server.enitity.CredentialEntity;

public interface CredentialService {
    CredentialEntity setCredential(String key, CredentialEntity credential);

    CredentialEntity getCredential(String key);

    CredentialEntity cleanCredential(String key);
}
