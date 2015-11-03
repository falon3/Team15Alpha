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

import com.skilltradiez.skilltraderz.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private User currentUser;
    private List<User> users;
    private ChangeList toBePushed;

    UserDatabase() {

    }

    public User createUser(String username, String password) throws UserAlreadyExistsException {
        return null;
    }

    public User login(String username, String password) {
        return null;
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
        return null;
    }

    public User getAccountByNickname(String username) {
        return null;
    }

    public User getAccountByUserID(UserID id) {
        return null;
    }
}
