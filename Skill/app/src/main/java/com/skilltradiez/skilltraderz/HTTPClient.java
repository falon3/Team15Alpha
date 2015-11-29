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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**DESCRIPTION:
 * In this application we're making use of elasticsearch as our database. This class
 * in particular is going to be dealing with everything to do with the actual HTTP internet
 * dealings of our application and facilitates the use of elastic search in our application.
 *
 *
 * ~~ACCESS:
 * This is a public class meaning that any other part of the application can access
 * this and create an instance of the object and any part of the application can also access
 * and make use of any of the methods to any of the objects created within this application.
 *
 *
 * ~~CONSTRUCTOR:
 * This has a simple default constructor that takes in no parameters and creates a new
 * DefaultHttpClient object that will be saved as an attribute in the httpClient attribute of
 * this class.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: HTTPCLIENT:
 *     This is going to be what is storing the object of the httpclient in this object.
 *     This seems redundant maybe to make this but it is essential for elasticsearch to have
 *     a unique httpclient for each of the objects it is related towards.
 *
 *
 * ~~MISC METHODS:
 * 1: GET:
 *     Give a url as a string this method will actually retrieve a particular selection
 *     from the database, this returns a read response of the particular entity within question.
 *
 *
 * 2: POST:
 *     Given a url, and data this will put into the elasticsearch class (assumed instantiated)
 *     and then actually put INTO the database whatever is handed into this as data.
 *     At this point any data has been already converted into a String of data and so
 *     that was already handled (Thanks GSON/JSON!).
 *
 * 3: DELETE:
 *     When we want to remove something from the database this method is called, this is pretty
 *     essentiall because during the entire process of trading we will be removing A LOT of things.
 *
 * 4: READ:
 *     Sometimes we just want to scan the database and return what exactly is going on at any
 *     given particular point in time. This function will scan the database and turn it into a
 *     readable string. Convenient!
 */

/**
 * A simple class for doing http things, that will probably only be useful for elastic.
 */
public class HTTPClient {
    private HttpClient httpClient;
    private int timeout;
    private static final int DEFAULT_TIMEOUT = 500;

    public HTTPClient() {
        httpClient = new DefaultHttpClient();
        resetFailure();
    }

    /**
     * Every time it fails, we fail faster!
     */
    public void failureHappened() {
        timeout /= 2;
        setTimeout();
    }

    public void resetFailure() {
        timeout = DEFAULT_TIMEOUT;
        setTimeout();
    }

    private void setTimeout() {
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
    }

    /**
     * Does an HTTP GET to the given url and returns the response as a string.
     */
    public String get(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = httpClient.execute(get);
            String strresp = read(response.getEntity());
            return strresp;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Does an HTTP POST to the given url, with the given data (JSON string) and returns the
     * response as a string.
     */
    public String post(String url, String data) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(data));
        try {
            HttpResponse response = httpClient.execute(post);
            String strresp = read(response.getEntity());
            return strresp;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Does an HTTP DELETE on the given url and returns the response as a string.
     */
    public String delete(String url) throws IOException {
        HttpDelete del = new HttpDelete(url);
        try {
            HttpResponse response = httpClient.execute(del);
            String strresp = read(response.getEntity());
            return strresp;
        } catch (IOException e) {
            throw e;
        }
    }

    private String read(HttpEntity entity) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null)
            sb.append(line);
        return sb.toString();
    }
}
