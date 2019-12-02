package com.github.qq120011676.credential.client;

import com.github.qq120011676.credential.common.entity.CredentialViewEntity;

import java.io.IOException;

public class CredentialClientTest {
    public static void main(String[] args) throws IOException {
        String baseUrl = "http://localhost:8080";
        CredentialClient credentialClient = new CredentialClient(baseUrl);
        CredentialViewEntity credentialView = credentialClient.get();
        System.out.println(credentialView);
        boolean bol = credentialClient.check(credentialView.getToken());
        System.out.println(bol);
        boolean bol2 = credentialClient.verify(credentialView.getToken());
        System.out.println(bol2);
    }
}
