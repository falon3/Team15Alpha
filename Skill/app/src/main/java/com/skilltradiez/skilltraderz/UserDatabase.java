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

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.skilltradiez.skilltraderz.User;

import org.apache.http.client.params.HttpClientParamConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.indices.mapping.PutMapping;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private User currentUser;
    private List<User> users;
    private ChangeList toBePushed;

    UserDatabase() {
        users = new ArrayList<User>();
        toBePushed = new ChangeList();
    }

    public User createUser(String username) throws UserAlreadyExistsException {
        User u = new User(UserID.generateRandomID(), username);
        users.add(u);
        currentUser = u;
        return u;
    }

    public User login(String username) {
        User u = getAccountByUsername(username);
        currentUser = u;
        return u;
    }

    public void pullUsers() {
        users = new ArrayList<User>();
        /*TODO
         * Get The Users (Who Are Cached/Friends) By ElasticSearch
         */
    }

    public void save() {
        // Saves locally and pushes changes if connected to the internet
    }

    public User getAccountByUsername(String username) {
        for (User u : users) {
            if (u.getProfile().getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User getAccountByUserID(UserID id) {
        for (User u : users) {
            if (u.getUserID().equals(id)) {
                return u;
            }
        }
        return null;
    }
}
