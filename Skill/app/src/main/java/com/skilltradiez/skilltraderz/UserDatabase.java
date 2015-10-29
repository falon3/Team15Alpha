package com.skilltradiez.skilltraderz;

import com.skilltradiez.skilltraderz.User;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private List<User> users;

    UserDatabase() {

    }

    public User createUser(String username, String password) throws UserAlreadyExistsException{
        return null;
    }

    public User login(String username, String password) {
        return null;
    }

    public void save() {
        // Saves locally and pushes changes if connected to the internet
    }

    public User getAccountByUsername(String username) {
        return null;
    }
}
