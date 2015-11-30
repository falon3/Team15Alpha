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
/**
 * In this application we're making use of elasticsearch as our database. This class
 * in particular is going to be dealing with everything to do with the actual HTTP internet
 * dealings of our application and facilitates the use of elastic search in our application.
 *
 * Core in communicating with the Elastic Search database.
 */

public class HTTPClient {
    private int timeout;
    private static final int DEFAULT_TIMEOUT = 500;

    /**Class Variables:
     * 1: httpClient, when the constructor is invoked for this class we will assign this variable
     *    a new DefaultHttpClient object created the the httpParams given in the constructor.
     */
    private HttpClient httpClient;

    /**
     * This constructor when invoked will:
     * 1: Assign new basic HTTP parameters.
     * 2: Set a connection timeout to 5000 ms.
     * 3: Set it so that there will be a timeout at 500 ms.
     * 4: Create a new DefaultHttpClient object kept by this class in the variable httpClient, by
     *    passing in the newly created httpParams specified above.
     */
    public HTTPClient() {
        httpClient = new DefaultHttpClient();
        resetFailure();
    }

    /**
     * Every time it fails, we fail faster! YAY!~ WHEEEE!!!!!!!!!!!!~
     */
    public void failureHappened() {
        timeout /= 2;
        if (timeout < 100)
            timeout = 100;
        setTimeout();
    }

    /**
     * When invoked, this method will assign the timeout variable to be equal to the default
     * timeout for connection timeouts- the value of 500.
     */
    public void resetFailure() {
        timeout = DEFAULT_TIMEOUT;
        setTimeout();
    }

    /**
     * This method sets the amount of time till timeout of connection to the variable timeout
     * that is in the HTTPClient Object.
     */
    private void setTimeout() {
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
    }


    /**
     * Given a URL String, will return a String of whatever is at the given URL.
     *
     * Creates a new HttpGet type variable called get and assigned it a new HttpGet Object with the
     *    passed in URL String.
     * Creates a new HttpResponse type variable called response where we assign it the value
     *    obtained from calling the execute method om the httpClient passing into it the get
     *    variable assigned above.
     * Returns the String of the response's getEntity method.
     *
     * @param url TheURL to be used by the HTTPClient.
     * @return String of the entity at the given URL.
     * @throws IOException
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
     *
     * Creates a new HttpPost Object with the passed in URL String.
     * Sets the header and entity of the post just created.
     * Obtains the HttpResponse from the httpClient's execute method on the post object passed
     *    into the method.
     * Returns the string of the HttpResponse Object's getEntity method.
     *
     * @param url String of the URL.
     * @param data String of the data.
     * @return String of the response from the database.
     * @throws IOException
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
     *
     * Creates a new HttpDelete Object using the URL passed into this method.
     * Creates a new HttpResponse Object using the HttpClient's execute method being passed
     *    the HttpDelete Object created above.
     * Returns the String of the response from the database- using the getEntity method on the
     *    HttpResponse object created directly above.
     *
     * @param url String of the URL to be utilized by this method.
     * @return String of the response from the database.
     * @throws IOException
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

    /**
     * Reads the string of bytes from the database from a given HttpEntity object.
     *
     * Makes a new BufferedReader Object using a new InputStreamReader created with the
     *    getContent method called from the HttpEntity Object passed into the method.
     * Makes a new StringBuilder Object, and a new String (Called Line.)
     * Loops over the BufferedReader Object created above, and if it is not equal to null (and
     *    therefore has no data) then we will append it to the StringBuilder Object made above.
     * Returns the StringBuidler Object as a String by invoking the toString method on it.    *
     *
     * @param entity HttpEntity Object to read.
     * @return String of the bytes obtained from the database.
     * @throws IOException
     */
    private String read(HttpEntity entity) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null)
            sb.append(line);
        return sb.toString();
    }
}
