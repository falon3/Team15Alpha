package com.skilltradiez.skilltraderz;
/*
 *    Team15Alpha
 *    AppName: SkillTradiez (Subject to change)
 *    Copyright (C) 2015  Stephen Andersen, Falon Scheers, Elyse Hill, Noah Weninger, Cole Evans
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import java.net.URLEncoder;

/**
 * A simple class for doing http things, that will probably only be useful for elastic.
 */
public class SimpleHTTPClient {
    HttpClient httpClient;
    public SimpleHTTPClient() {
        httpClient = new DefaultHttpClient();
    }

    /**
     * Does an HTTP GET to the given url and returns the response as a string.
     */
    public String get(String url) throws IOException {
        url = URLEncoder.encode(url, "ISO-8859-1");
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(get);
        return read(response.getEntity());
    }
    /**
     * Does an HTTP POST to the given url, with the given data (JSON string) and returns the
     * response as a string.
     */
    public String post(String url, String data) throws IOException {
        url = URLEncoder.encode(url, "ISO-8859-1");
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(data));
        HttpResponse response = httpClient.execute(post);
        return read(response.getEntity());
    }

    /**
     * Does an HTTP DELETE on the given url and returns the response as a string.
     */
    public String delete(String url) throws IOException {
        url = URLEncoder.encode(url, "ISO-8859-1");
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
