package com.skilltradiez.skilltraderz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class User {
    private Profile profile;
    private Inventory inventory;
    private List<UserID> friendsList;

    User(String username, String password) {
        profile = new Profile(username, password);
        inventory = new Inventory(); // Empty
        friendsList = new ArrayList<UserID>(); // Empty
    }

    public Profile getProfile() {
        return profile;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<UserID> getFriendsList() {
        return friendsList;
    }
}
