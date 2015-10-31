package com.skilltradiez.skilltraderz;

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
