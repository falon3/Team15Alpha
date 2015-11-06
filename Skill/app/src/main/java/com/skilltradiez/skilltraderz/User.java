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

import java.util.Observable;

/**
 * Created by sja2 on 10/28/15.
 */
public class User extends Observable {
    private Profile profile;
    private Inventory inventory;
    private FriendsList friendsList;
    private TradeList tradeList;
    private ID id = ID.generateRandomID();

    User(String username) {
        profile = new Profile(username);
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

    public ID getUserID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
