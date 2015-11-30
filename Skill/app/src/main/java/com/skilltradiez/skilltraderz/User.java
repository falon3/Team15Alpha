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

    /**
     * Constructor accepts a String for the username variable for the object, will instantiate
     * a new Profile, Inventory, FriendsList and TradeList associated with this new User Object.
     * @param username String input.
     */
    User(String username) {
        profile = new Profile(username);
        inventory = new Inventory(id); // Empty
        friendsList = new FriendsList(id); // Empty
        tradeList = new TradeList(id); // Empty
    }

    /**
     * Basic getter method that returns the Profile Object.
     * @return Profile Object.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Basic getter method that returns the Inventory Object.
     * @return Inventory Object.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Basic getter method that returns the FriendsList Object.
     * @return FriendsList Object.
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }

    /**
     * Basic getter method that returns the TradeList Object.
     * @return TradeList Object.
     */
    public TradeList getTradeList() {
        return tradeList;
    }

    /**
     * Basic getter method that returns the ID Object for the User.
     * @return ID Object.
     */
    public ID getUserID() {
        return id;
    }

    /**
     * Special String method that will return a String of the username.
     * @return String of the username.
     */
    @Override
    public String toString() {
        return profile.getUsername();
    }

    /**
     * Will take in an Object (any ol object...) of the Object type; and will then compare the
     * current object with the object passed into the method. If they are equal return true. If
     * they are not equal, then return false.
     * @param inputObject Object Object.
     * @return Boolean. True/False.
     */
    @Override
    public boolean equals(Object inputObject) {
        if (this == inputObject) return true;
        if (inputObject == null || getClass() != inputObject.getClass()) return false;

        User user = (User) inputObject;

        return !(id != null ? !id.equals(user.id) : user.id != null);
    }

    /**
     * This method when called is going to give us a hash (horrendous hash function- I am deeply
     * offended) of the tradeID ID Object value. Allowing us to easily compare values.
     * Why is that? Because a hash will make comparison very easy and small.
     *
     * @return Integer. THIS integer represents the hash of the tradeID ID object.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
