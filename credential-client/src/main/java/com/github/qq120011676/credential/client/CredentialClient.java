package com.github.qq120011676.credential.client;

import com.github.qq120011676.credential.common.entity.CredentialViewEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;

public class CredentialClient {
    private String baseUrl;

    public CredentialClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public CredentialViewEntity get() throws IOException {
        return this.get(null);
    }

    public CredentialViewEntity get(Duration timeout) throws IOException {
        Connection connection = Jsoup.connect(MessageFormat.format("{0}/credential/get", this.baseUrl));
        if (timeout != null) {
            connection.data("timeout", MessageFormat.format("{0}s", timeout.getSeconds()));
        }
        Connection.Response response = connection.ignoreContentType(true).ignoreHttpErrors(true).execute();
        String body = response.body();
        if (200 != response.statusCode()) {
            throw new RuntimeException(new Gson().fromJson(body, JsonObject.class).get("message").getAsString());
        }
        if (!StringUtil.isBlank(body)) {
            return new Gson().fromJson(body, CredentialViewEntity.class);
        }
        return null;
    }

    public boolean check(String token) throws IOException {
        Connection.Response response = Jsoup.connect(MessageFormat.format("{0}/credential/check", this.baseUrl)).data("token", token).method(Connection.Method.POST).ignoreContentType(true).ignoreHttpErrors(true).execute();
        return analysisStatus(response);
    }

    public boolean verify(String token) throws IOException {
        Connection.Response response = Jsoup.connect(MessageFormat.format("{0}/credential/verify", this.baseUrl)).data("token", token).method(Connection.Method.POST).ignoreContentType(true).ignoreHttpErrors(true).execute();
        return analysisStatus(response);
    }

    public boolean use(String unique) throws IOException {
        Connection.Response response = Jsoup.connect(MessageFormat.format("{0}/credential/use", this.baseUrl)).data("unique", unique).method(Connection.Method.POST).ignoreContentType(true).ignoreHttpErrors(true).execute();
        return analysisStatus(response);
    }

    private boolean analysisStatus(Connection.Response response) {
        String body = response.body();
        if (200 != response.statusCode()) {
            throw new RuntimeException(new Gson().fromJson(body, JsonObject.class).get("message").getAsString());
        }
        if (!StringUtil.isBlank(body)) {
            return new Gson().fromJson(body, JsonObject.class).get("status").getAsBoolean();
        }
        return false;
    }
}
