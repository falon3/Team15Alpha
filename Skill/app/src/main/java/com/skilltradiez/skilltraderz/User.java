package com.skilltradiez.skilltraderz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class User {
    private Profile profile;
    private Inventory inventory;
    private FriendsList friendsList;
    private TradeList tradeList;
    private UserID id;

    User(UserID id, String username, String password) {
        this.id = id;
        profile = new Profile(username, password);
        inventory = new Inventory(); // Empty
        friendsList = new FriendsList(id); // Empty
        tradeList = new TradeList(id); // Empty
    }

    public Profile getProfile() {
        return profile;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }

    public TradeList getTradeList() {
        return tradeList;
    }

    public UserID getUserID() {
        return id;
    }
}
