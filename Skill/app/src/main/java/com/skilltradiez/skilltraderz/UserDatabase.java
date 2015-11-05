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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private User currentUser;
    private List<User> users;
    private ChangeList toBePushed;
    Elastic elastic;

    UserDatabase() {
        users = new ArrayList<User>();
        toBePushed = new ChangeList();
        elastic = new Elastic("http://cmput301.softwareprocess.es:8080/cmput301f15t15/");
    }

    public User createUser(String username) throws UserAlreadyExistsException {
        if (getAccountByUsername(username) != null)
            throw new UserAlreadyExistsException();

        User u = new User(UserID.generateRandomID(), username);
        users.add(u);
        currentUser = u;
        try {
            elastic.addDocument("user", username, u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u;
    }

    public User login(String username) {
        User u = getAccountByUsername(username);
        currentUser = u;
        return u;
    }

    public void deleteAllData() {
        try {
            elastic.deleteDocument("user", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pullUsers() {
        for (int i = 0; i < users.size(); i++) {
            users.set(i, getOnlineAccountByUsername(users.get(i).getProfile().getUsername()));
        }
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
        return getOnlineAccountByUsername(username);
    }

    private User getOnlineAccountByUsername(String username) {
        User u = null;
        try {
            u = elastic.getDocumentUser("user", username);
            if (u != null)
                users.add(u);
        } catch (IOException e) {
        }
        return u;
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
