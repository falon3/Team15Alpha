package com.skilltradiez.skilltraderz;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Elastic
 */
public class Elastic {
    SimpleHTTPClient httpClient;
    String baseUrl;
    Gson gson;
    public Elastic(String baseUrl) {
        httpClient = new SimpleHTTPClient();
        this.baseUrl = baseUrl;
        gson = new Gson();
    }

    /**
     * Pushes a complete document to elasticsearch, overwriting anything with the same ID.
     * @param type Elastic type.
     * @param id Elastic ID, can probably be empty to generate a random one.
     * @param data The actual object to push
     * @throws IOException if there's some sort of error with internet things or parsing.
     */
    public <T> void addDocument(String type, String id, T data) throws IOException {
        httpClient.post(baseUrl + type + "/" + id, gson.toJson(data));
    }

    /**
     * Pushes a partial update to a document, overwriting all included data starting at a specified
     * json object path
     * @param type Elastic type
     * @param id Elastic ID, must be something.
     * @param data the partial data to push
     * @param path A JSON object path, seperated by slashes.
     *             For example,
     *             "foo/bar/baz" puts data into elasticsearch, at the given type and ID, where the
     *             actual data sent is `{"foo":{"bar":{"baz":data}}}`
     * @throws IOException if internet things fail
     */
    public <T> void updateDocument(String type, String id, T data, String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        String[] paths = path.split("/");
        for (String s : paths) {
            sb.append("{\"");
            sb.append(s);
            sb.append("\":");
        }
        String json = gson.toJson(data);
        sb.append(json);
        for (String s : paths) {
            sb.append("}");
        }
        Log.d("SKILLLLZ", sb.toString());
        httpClient.post(baseUrl + type + "/" + id + "/_update", sb.toString());
    }

    //The next three methods need to be separate because java's generics aren't real generics,
    //they are actually fake. But anyways, they get different types of documents from the database.
    public User getDocumentUser(String id) throws IOException {
        String resp = httpClient.get(baseUrl + "user" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<User>>() { }.getType();
        return ((GetResponse<User>)gson.fromJson(resp, getResponseType))._source;
    }

    public Skill getDocumentSkill(String id) throws IOException {
        String resp = httpClient.get(baseUrl + "skill" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Skill>>() { }.getType();
        return ((GetResponse<Skill>)gson.fromJson(resp, getResponseType))._source;
    }

    public Trade getDocumentTrade(String id) throws IOException {
        String resp = httpClient.get(baseUrl + "trade" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Trade>>() { }.getType();
        return ((GetResponse<Trade>)gson.fromJson(resp, getResponseType))._source;
    }

    /**
     * Deletes a document from elastic.
     * @param type The type
     * @param id The ID. If you don't know what these are by now, you should read the elastic docs.
     * @throws IOException if the internet breaks.
     */
    public void deleteDocument(String type, String id) throws IOException {
        httpClient.delete(baseUrl + type + "/" + id);
    }
}
