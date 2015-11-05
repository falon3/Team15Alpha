package com.skilltradiez.skilltraderz;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.Buffer;

/**
 * Created by nweninge on 11/4/15.
 */
public class SimpleHTTPClient {
    HttpClient httpClient;
    public SimpleHTTPClient() {
        httpClient = new DefaultHttpClient();
    }
    public String get(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(get);
        return read(response.getEntity());
    }
    public String post(String url, String data) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(data));
        HttpResponse response = httpClient.execute(post);
        return read(response.getEntity());
    }
    public String delete(String url) throws IOException {
        HttpDelete del = new HttpDelete(url);
        HttpResponse response = httpClient.execute(del);
        return read(response.getEntity());
    }

    private String read(HttpEntity entity) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
