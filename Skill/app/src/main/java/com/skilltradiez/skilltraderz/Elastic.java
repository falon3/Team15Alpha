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

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

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
        type = URLEncoder.encode(type, "ISO-8859-1");
        id = URLEncoder.encode(id, "ISO-8859-1");
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
        type = URLEncoder.encode(type, "ISO-8859-1");
        id = URLEncoder.encode(id, "ISO-8859-1");
        path = "doc/" + path;
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
        String uri = baseUrl + type + "/" + id + "/_update";
        Log.d("SKILLLLZ!", httpClient.post(uri, sb.toString()));
    }

    //The next three methods need to be separate because java's generics aren't real generics,
    //they are actually fake. But anyways, they get different types of documents from the database.
    public User getDocumentUser(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "user" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<User>>() { }.getType();
        return ((GetResponse<User>)gson.fromJson(resp, getResponseType))._source;
    }

    public Skill getDocumentSkill(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "skill" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Skill>>() { }.getType();
        return ((GetResponse<Skill>)gson.fromJson(resp, getResponseType))._source;
    }

    public Trade getDocumentTrade(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
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
        type = URLEncoder.encode(type, "ISO-8859-1");
        id = URLEncoder.encode(id, "ISO-8859-1");
        httpClient.delete(baseUrl + type + "/" + id);
    }
}
