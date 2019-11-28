package com.github.qq120011676.credential.client;

import com.github.qq120011676.credential.common.entity.CredentialEntity;

import java.io.IOException;

public class CredentialClientTest {
    public static void main(String[] args) throws IOException {
        String baseUrl = "http://localhost:8080";
        CredentialClient credentialClient = new CredentialClient(baseUrl);
        CredentialEntity credential = credentialClient.get();
        System.out.println(credential);
        boolean bol = credentialClient.check(credential.getToken());
        System.out.println(bol);
        boolean bol2 = credentialClient.verify(credential.getToken());
        System.out.println(bol2);
    }
}
