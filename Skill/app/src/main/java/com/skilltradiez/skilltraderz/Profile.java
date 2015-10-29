package com.skilltradiez.skilltraderz;

/**
 * Created by sja2 on 10/28/15.
 */
public class Profile {
    private String username;
    private Object password;
    Profile(String username, String password) {
        this.username = username;
        // Encrypt it?
        this.password = password.getBytes();
    }
}
