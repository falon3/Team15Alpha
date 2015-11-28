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

/**~~DESCRIPTION:
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
 *
 * ~~IMPORTS:
 *     Yes I realize FULLY that this isn't something in the rest of the application that I
 *     specify but that is because those imports are relatively self-explainitory. To make
 *     our elastic search we had to utilize imports that are much less conventional. So please
 *     forgive me for this minor digression.
 *
 *     1: android.util.Log:
 *         This imported functionality is going to allow this class to actually
 *     understand what is going on in this class, and helps A LOT during the debugging process.
 *     For us to be able to create a comprehensive list of the events that are going on within
 *     this class it was absolutely ESSENTIAL to our success to make use of this import.
 *
 *     2: com.google.gson.Gson:
 *         Our application is based off of object orriented programming, and in order to
 *         emphasize this concept in our project we are going to be making use of Gson/Json in
 *         order for us to create large objects and serialize them into a method that allows us
 *         to actually dip these objects into a savable form. This is very similar to how we
 *         make use of the local storage mechanisms. What Gson actually allows us to do in this
 *         application is it lets us take a large object (or I guess "small" object, but
 *         for all purposes I will call it "large" to stroke all of our egos) and then convert
 *         this object into their JSON representation.
 *
 *         "What the hell is a JSON representation", well I am glad you asked! This is a conversion
 *         of an object (which we could argue is ridiculously difficult for a human being
 *         to deal with in a DIRECT WAY/ sense, despite objects simplifying the process of coding)
 *         and then we convert this object into a series of statements that are actually
 *         relatively simple for a human-being to read. And allows us to make use of various
 *         libraries in our application that deal explicitly with JSON opposed to direct objects.
 *
 *         "Hey library, here, have a pointer to an object! Now do something!"
 *             --This doesn't work.
 *         Also JSON means Java Script Object Notation, and GSON means Google GSON.
 *         Which ... essentially I think is a play on JSON but making it google JSON.
 *
 *         If you want to know more on JSON objects then please read here:
 *             https://en.wikipedia.org/wiki/JSON
 *
 *     3: com.google.gson.reflect.TypeToken
 *         Ever wonder what a type of something is? Let's say it is a really nasty really
 *         convoluted object. Sort of like what we use and have here! Well we have the very
 *         very fortunate ability to be able to utilize this import to get the type of objects!
 *         Hallelujah, life is simple here!
 *
 *     4: java.io.IOException
 *         This is a more common import, this allows us to actually use IOExceptions. Very
 *         standard to see this imported into the class.
 *
 *     5: java.lang.reflect.Type
 *         This is used for converting java objects into JSON, where the typetoken is going
 *         to return a type of an object. And in our case this is going to be paired with the
 *         com.google.json.reflect.TypeToken
 *
 *     6: java.net.URLEncoder
 *         For Elastic we're going to actually be using a lot of the internet resources avalaible
 *         to us, because, well, elasticsearch is on the internet. (My security senses are so
 *         tingling here... :( ... ). So what better way to deal with something that is based
 *         on the internet than to actually have a method to actually encode URLs into our code?
 *         This likewise will make our lives A LOT easier, because the libraries that we attempted
 *         to.
 *
 *         More diectly put, we pass things into elasticsearch and we then need to have the
 *         things being passed using this special format. Why? Because elastic search seems
 *         to enjoy exploring sproadically when we pass it special characters.
 *
 *
 *  ~~ACCESS:
 *  Tis class is actually going to be public.
 *  Public? Public why? Why you ask?
 *  Because a database is absolutely CORE in our applicaiton, without it we could not even
 *  remotely hope to store data on users or transfer data (this is how WE are transferring data
 *  from user to user opposed to some other networking scheme). And so in our case we're going
 *  to allow ANY CLASS from ANY PART OF THIS PROGRAM to have complete access to (1) instantiate
 *  the object (2) use the methods provided from this class in various objects!
 *
 *  ~~CONSTRUCTOR:
 *  We're going to have a single constructor dedicated to this class. This is going to
 *  take in a string that is the base URL. Why the base URL you might ask? Because with
 *  elasticsearch we have the entire thing running on the web and then we have the divisons of
 *  what goes where in the dabatase pieced together/maintaned/diversified/kept in some sort of
 *  order by the various different URL's.
 *
 *      Example:
 *          URL 1: /fancy/skills/majestic/wow/doge
 *          URL 2: /fancy/skills/lame/boring/cow
 *          These two URL's are going to store completely seperate forms of data in our
 *          application and therefore having differentiable URLs is absolutely a darling tactic
 *          to achieve isolation.
 *  BACK TO THE CONSTRUCTOR THOUGH. When the constructor for this object is called we're going
 *  to assign the attribute httpClient with a brand NEW object of HTTPClient (no params)
 *  which will have allof it's own functionalities in correlation with that object.
 *  We will also then assign THIS object's attribute baseUrl (string) the value of the baseURL that
 *  is passed into the constructor. Allowing us reference to what is the exact URL that we're
 *  dealing with here.
 *
 *  Thirdly we in the constructor create a new Gson object that is going to be assigned to the
 *  attribute gson that will allow us to fully make functional use of the object. Pretty
 *  core considering this is how our application is actually going to be using the elasticsearch
 *  database.
 *
 *
 *  ~~ATTRIBUTES/METHODS:
 *  1: SIMPLEHTTPCLIENT:
 *      Let's say we want an object to isolate a bunch of attribtutes and methods that we don't
 *      want to deal with the extra complexity of dealing with outside of the object into
 *      a single object. This is what this simpleHTTPClient attribute is. We're going to
 *      during the instantiation of an elastic object create a brand new aggregated object
 *      that is the simpleHTTPClient object and then associate it with this elastic class.
 *      Please read up on what that does in the respective class, my fingers deny me the ability
 *      to type anymore than need be, please no.
 *
 *      WE PROVIDE THE METHODS TO:
 *      -NONE, this is assigned during the constructor and then never can be get, set, etc.
 *      It is just an object that is aggregated and thus paired together with this elastic object
 *      and therefore we're going to actually have this just be something that is maintained
 *      by this object that will send requests to this aggregated object. We just wanted to
 *      make this code more simple, if we had the attributes and methods for this mashed together
 *      with this we'd have a beast. ...A rather ugly, unpleasant and mad snarling beast.
 *
 *  2: BASEURL:
 *      This is going to be a string attribute that when the constructor is called will be assigned
 *      the value of the URL that the elasticsearch client is actually going to be managing to
 *      put data into  / getting data from and modifying the data within. Without this attribute
 *      we simply wouldn't have a point of reference (arguably ... an index...?) where our
 *      database is going to have somewhere to manage a lot of various functionalities of our
 *      application.
 *
 *      WE PROVIDE THE METHODS TO:
 *      -NONE, this is assigned when the object is instantiated. This is not allowed
 *      to be get, set, modified, or anything of the sort. Nothing. Once the object is
 *      created this is here to stay and here to stay permanently.
 *
 *
 *  3: GSON:
 *      It is relatively difficult to have a fully functioning elasticsearch database
 *      populated and supporting our object orriented designs of our application without
 *      first initially having a GSON object. This is a method to turn our objects into
 *      JSON standard and then we can feed the JSON modification into the various
 *      other libraries that are going to utilize here. Which in this class our main
 *      concern is turning it into an elastic search thing.
 *
 *
 * ~~MISC METHODS:
 *  1: ADDDOCUMENT:
 *      Let's say that we want to put a particular object associated with a user into the
 *      elasticsearch database. We're going to put this all together with a type, id and data
 *      of the object. So now we're actually going to put together into the object a comprehensive
 *      detail that is going to be modified that is going to be put into the database.
 *
 *  2: updateDocument:
 *      Let's say that we have a bunch of various things involved into the database that are going
 *      to be needing to be updated. Very fitting name for what the goal of this object is to
 *      actually do. We're going to pass it a type, id, data and the path of the data.
 *      This is going to actually delve deep into the database and then it will put into the
 *      database all of the data that is needed after turning it into a GSON object.
 *
 *
 * 3: GETDOCUMENTUSER:
 *     Taking the type, id, data, and the path of the object we're going to actually have the
 *     particular document that we have stored within the database get pulled out from the database
 *     and then it returns the document.
 *
 *
 *
 * 4: GETDOCUMENTSKILL
 *     Given the id of a particular user we're going to utilize this method in order to actually
 *     have the skill get retrieved from the database and return the skill.
 *
 * 5: GETDOCUMENTTRADE:
 *     Consdering how we have an application that is based all around trading skills from user
 *     to user and then we actually are going to have this have the database manage to take
 *     all of the User object associated with that trade. Bless objects.
 *
 *
 * 6: DELETEDOCUMENT:
 *     Suppose we don't want a document (AN OBJECT) present, what should we do? We should have
 *     something within the methods that will actually go into the database and then will actively
 *     delete the object.
 *     This allows us to take an arbitrary type and id of an object and then combust it out.
 */

public class Elastic {
    HTTPClient httpClient;
    String baseUrl;
    Gson gson;
    public Elastic(String baseUrl, HTTPClient client) {
        httpClient = client;
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
        Log.d("ELASTIC", httpClient.post(baseUrl + type + "/" + id, gson.toJson(data)));
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

    public List<User> getAllUsers() throws IOException {
        String resp = httpClient.get(baseUrl + "user/_search?size=9999999");
        Type getResponseType = new TypeToken<UserSearchResponse>() { }.getType();
        UserSearchResponse searchResponse = gson.fromJson(resp, getResponseType);
        List<User> hitsT = new ArrayList<User>();
        for (UserSearchResponse.Hit hit : searchResponse.hits.hits)
            hitsT.add(hit._source);
        return hitsT;
    }

    public List<Skill> getAllSkills() throws IOException {
        String resp = httpClient.get(baseUrl + "skill/_search?size=9999999");
        Type getResponseType = new TypeToken<SkillSearchResponse>() { }.getType();
        SkillSearchResponse searchResponse = gson.fromJson(resp, getResponseType);
        List<Skill> hitsT = new ArrayList<Skill>();
        for (SkillSearchResponse.Hit hit : searchResponse.hits.hits)
            if (hit._source.isVisible())
                hitsT.add(hit._source);
        return hitsT;
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

    public Image getDocumentImage(String id) throws IOException {
        id = URLEncoder.encode(id, "ISO-8859-1");
        String resp = httpClient.get(baseUrl + "image" + "/" + id);
        Type getResponseType = new TypeToken<GetResponse<Image.Imageb64>>() { }.getType();
        Image.Imageb64 b64 = ((GetResponse<Image.Imageb64>)gson.fromJson(resp, getResponseType))._source;
        byte[] decodedByte = Base64.decode(b64.data, 0);
        return new Image(BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length), new ID(Long.parseLong(id)));
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
