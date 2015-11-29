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

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Suppose we have an application where it is useful for us to transfer data between users.
 * Suppose we want this data to also be maintained indefinately even if every user in the world
 * turned off their devices and / or deleted their application.
 *
 * How do we achieve persistence, transfer of information, etc?
 * Databases.
 * In our application we're making use of the database type that is called ElasticSearch and
 * this is going to be very different from SQL. In this instance we're going to have a mostly
 * web-browser based database type that interacts with the application to store and maintain
 * and allow modifications to the data of our various users. JSON objects, GSON objects are
 * going to be the primary way that we're going to interact with this application.
 *
 * This likely is going to be quite a daunting experience for people new to elastic search
 * (Like us. Ow. The pain.) so I will go into detail here and explain what every step along
 * the path of this application is actually doing in this class right here and right now.
 */

public class Elastic {
    HTTPClient httpClient;
    String baseUrl;
    Gson gson;

    /**
     * When the constructor is invoked, will assign the given HTTPClient Object as the one for
     * this Elastic Object created to use. And the baseURL String given as the baseURL to be
     * used by this Object. Will create a new Gson Object to be used.
     *
     * @param baseUrl The string of the Elastic database URL to be used in the application.
     * @param client The HTTPClient Object this Elastic Object will use.
     */
    public Elastic(String baseUrl, HTTPClient client) {
        httpClient = client;
        this.baseUrl = baseUrl;
        gson = new Gson();
    }

    /**
     * Pushes a complete document to elasticsearch, overwriting anything with the same ID.
     *
     * Firstly, we assign the type variable the URLEncoder class's encode method with the given
     * type string following "ISO-8859-1" standards.
     * Secondly, assign the id variable (using the same method) the "ISO-8859-1" encoded value.
     * Third, we used the HTTPClient class's post method to put the given document into the database.
     *
     * @param type Elastic type.
     * @param id Elastic ID, can probably be empty to generate a random one.
     * @param data The actual object to push
     * @throws IOException if there's some sort of error with internet things or parsing.
     */
    public <T> void addDocument(String type, String id, T data) throws IOException {
        type = URLEncoder.encode(type, "ISO-8859-1");
        id = URLEncoder.encode(id, "ISO-8859-1");
        Log.d("ELASTIC", httpClient.post(baseUrl + type + "/" + id, gson.toJson(data)));
    }

    /**
     * Pushes a partial update to a document, overwriting all included data starting at a specified
     * json object path.
     *
     * Using the URLEncoder class's encode method we used standard "ISO-8859-1" to assign the type
     * and id variables values. We obtained the path from the path String passed into the function
     * and merely added "doc/" to the front of the string, this identifies where in the database
     * this will be.
     *
     * Creating a new StringBuilder object, we then formatted the path to a proper format that the
     * database will understand.
     *
     * Transforming the data into a json String through the gson.toJson method we turned the
     * data given itno the method into something the database can properly handle.
     *
     * Finally we assign the uri and then pass this into the HTTPClient class's post method
     * along with the StringBuilder object (sb) that we created earlier. The data is now in the DB.
     *
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

    /**
     * Returns the List of all User Objects.
     *
     * Obtain response string from the HTTPClient Class's get method using max search size.
     * Assign a response type expected from the database for a UserSearchResponse.
     * Return the searchResponse from the database, transformed using the gson.fromJson method.
     * Create a new list of User Objects and then iterate through the searchResponse for "hits",
     *    in more common tongue- the actual successful User objects obtained are put into a new list
     * Return this list.
     *
     * @return List of User Objects.
     * @throws IOException
     */
    public List<User> getAllUsers() throws IOException {
        String resp = httpClient.get(baseUrl + "user/_search?size=9999999");
        Type getResponseType = new TypeToken<UserSearchResponse>() { }.getType();
        UserSearchResponse searchResponse = gson.fromJson(resp, getResponseType);
        if (searchResponse.hits == null) {
            httpClient.failureHappened();
            throw new IOException("Invalid response from elastic!");
        }
        List<User> hitsT = new ArrayList<User>();
        for (UserSearchResponse.Hit hit : searchResponse.hits.hits)
            hitsT.add(hit._source);
        httpClient.resetFailure();
        return hitsT;
    }

    /**
     * Return the list of ALL Skill objects for this application.
     *
     * Obtain response string from the HTTPClient Class's get method using max size of search.
     * Assign a response type expected from the database for a Skill.
     * Assign a searchResponse variable with the gson.fromJson response from the database along
     *     with the type assigned to the getResponseType variable as described above.
     * Obtaining a list of Skill Objects, we iterated through the searchResponse for successful
     *     "hits", more commonly known as successful Skill Objects retrieved from the database.
     * Return this list of Skill Objects.
     *
     * @return List of Skill Objects.
     * @throws IOException
     */
    public List<Skill> getAllSkills() throws IOException {
        String resp = httpClient.get(baseUrl + "skill/_search?size=9999999");
        Type getResponseType = new TypeToken<SkillSearchResponse>() { }.getType();
        SkillSearchResponse searchResponse = gson.fromJson(resp, getResponseType);
        List<Skill> hitsT = new ArrayList<Skill>();
        if (searchResponse.hits == null) {
            httpClient.failureHappened();
            throw new IOException("Invalid response from elastic!");
        }
        for (SkillSearchResponse.Hit hit : searchResponse.hits.hits)
            if (hit._source.isVisible())
                hitsT.add(hit._source);
        httpClient.resetFailure();
        return hitsT;
    }

    //The next three methods need to be separate because java's generics aren't real generics,
    //they are actually fake. But anyways, they get different types of documents from the database.

    /**
     * Will return the User Object related to the string ID given to the method.
     *
     * Encode the id string passed into the method.
     * Obtain response string from the HTTPClient Class's get method.
     * Assign a response type expected from the database for a User.
     * Return the User from the database, transformed using the gson.fromJson method.
     *
     * @param id String input.
     * @return User Object.
     * @throws IOException
     */
    public User getDocumentUser(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "user" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<User>>() { }.getType();
        GetResponse<User> response = gson.fromJson(resp, getResponseType);
        if (response == null) {
            httpClient.failureHappened();
            throw new IOException("Invalid response from elastic!");
        }
        httpClient.resetFailure();
        return response._source;
    }

    /**
     * Will return the Skill Object related to the string ID given to the method.
     *
     * Encode the id string passed into the method.
     * Obtain response string from the HTTPClient Class's get method.
     * Assign a response type expected from the database for a Skill.
     * Return the Skill from the database, transformed using the gson.fromJson method.
     *
     * @param id String input.
     * @return Skill Object.
     * @throws IOException
     */
    public Skill getDocumentSkill(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "skill" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Skill>>() { }.getType();
        GetResponse<Skill> response = gson.fromJson(resp, getResponseType);
        if (response == null) {
            httpClient.failureHappened();
            throw new IOException("Invalid response from elastic!");
        }
        httpClient.resetFailure();
        return response._source;
    }

    /**
     * Will return the Trade Object related to the string ID given to the method.
     *
     * Encode the id string passed to the method.
     * Obtain response from the HTTPClient Class's get method.
     * Get the type of the response returned from the database for a Trade.
     * Return from this method the Trade document requested, transformed using gson.fromJson.
     *
     * @param id String input.
     * @return Trade Object.
     * @throws IOException
     */
    public Trade getDocumentTrade(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "trade" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Trade>>() { }.getType();
        GetResponse<Trade> response = gson.fromJson(resp, getResponseType);
        if (response == null) {
            httpClient.failureHappened();
            throw new IOException("Invalid response from elastic!");
        }
        httpClient.resetFailure();
        return response._source;
    }

    /**
     * Returns the Image Object related to the string ID given to the method.
     *
     * Encode the passed in id String in "ISO-8859-1" format.
     * Obtain a response String from the HTTPClient's get method using the baseURL and id
     *    along with some specific formatting concerns.
     * Obtain the response type using gson.fromJson method on the response and type above.
     * Assign a byte array with the Base64 class's decode method on the b.64 data method.
     * Return the Image, after using the BitmapFactory class's decodeByteArray method on
     *    the decodedByte array created above. Also, assign it a new ID in this same line.
     *
     * Finally, in the last line of this method we used the BitmapFactory class's decodeByteArray
     * method upon our decodedByte variable- and obtained a new Image Object.
     * @param id String input.
     * @return Image Object.
     * @throws IOException
     */
    public Image getDocumentImage(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "image" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Image.Imageb64>>() { }.getType();
        GetResponse<Image.Imageb64> response = gson.fromJson(resp, getResponseType);
        if (response == null || response._source == null) {
            httpClient.failureHappened();
            throw new IOException("Invalid response from elastic!");
        }
        Image.Imageb64 b64 = response._source;
        byte[] decodedByte = Base64.decode(b64.data, 0);
        httpClient.resetFailure();
        return new Image(BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length), new ID(Long.parseLong(id)));
    }

    /**
     * Deletes a document from elastic.
     *
     * Encode the given type and id strings following "ISO-8859-1" standard.
     * Pass the baseURL, encoded type and id into the HTTPClient Object's delete method.
     * The particular document associated with the given type and id from the database.
     *
     * @param type String input for the type of the document.
     * @param id String input for the ID of the document.
     * @throws IOException
     */
    public void deleteDocument(String type, String id) throws IOException {
        type = URLEncoder.encode(type, "ISO-8859-1");
        id = URLEncoder.encode(id, "ISO-8859-1");
        httpClient.delete(baseUrl + type + "/" + id);
    }
}
