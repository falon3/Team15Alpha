package com.skilltradiez.skilltraderz;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

/**
 * Created by Falon3 on 2015-11-28.
 */
public class BrokenHTTPClient extends HTTPClient{
        public String get(String url) throws IOException {
            Log.d("get string", "GET" + url);
            throw new IOException();
        }

        /**
         * Does an HTTP POST to the given url, with the given data (JSON string) and returns the
         * response as a string.
         */
        public String post(String url, String data) throws IOException {
            Log.d("post string", "POST" + url);
            throw new IOException();
        }

        /**
         * Does an HTTP DELETE on the given url and returns the response as a string.
         */
        public String delete(String url) throws IOException {
            Log.d("delete string", "DELETE" + url);
            throw new IOException();
        }
}
