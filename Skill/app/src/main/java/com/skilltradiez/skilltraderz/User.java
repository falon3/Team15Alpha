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

/**
 * I suppose it might make quite a fascinating piece of contemporary art in which case we make
 * an application that is based around users without actually having any users present within
 * the application/.
 *
 * I, or rather we, are not planning to do modern art in this application. We are tryin to make an
 * application for ussers.... and so we have an ESSENTIAL CORE CLASS THAT IS GOING TO BE THE USERS
 * CLASS.
 *
 * This class is going to hold within itself all of the core functionality that the users may
 * actually need to have full functionality within our application. It holds a ton of attributes
 * all of which are PRIVATE that will store core essential data on the user, and it will
 * have methods (getters/setters) for all of these attributes that allow it so that the
 * user can have a proper flow tiwhtin the application itself.
 *
 * Code wise, think of this as a central hub for the user where this is the class that will
 * hold a bunch of other objects within itself that are associated with itself. This allows
 * very simple easy access reference throughout the rest of the program to access anything
 * related to this user. Life is simple, enjoy! :)
 *
 */

public class User {
    private Profile profile;
    private Inventory inventory;
    private FriendsList friendsList;
    private TradeList tradeList;
    private ID id = ID.generateRandomID();

    User(String username) {
        profile = new Profile(username);
        inventory = new Inventory(id); // Empty
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

    @Override
    public String toString() {
        return profile.getUsername();
    }
}
