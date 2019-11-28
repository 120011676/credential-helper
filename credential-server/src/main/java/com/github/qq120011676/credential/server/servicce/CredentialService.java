package com.github.qq120011676.credential.server.servicce;

import com.github.qq120011676.credential.common.entity.CredentialEntity;

public interface CredentialService {
    String set(String key, CredentialEntity credential);

    CredentialEntity get(String key);

    void clean(String key);

}
