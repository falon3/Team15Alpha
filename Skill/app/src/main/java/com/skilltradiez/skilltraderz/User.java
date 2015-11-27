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

/**~~DESCRPTION:
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
 * ~~CONSTRUCTORS:
 * We have a SINGLE constructor here, nothing fancy, which will take into itself a
 * userID, a username, and a password. All of these must match with a particular user, and they
 * will be essential with the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: Profile:
 *     We have a profile class within this application which will be called upon and then will
 *     be associated with the user and permanently tied to this user! So it is fabulous. A user
 *     without a profile would obviosuly be pretty useless on an application where the user's
 *     are going to be interacting through their profiles and demonstrating that they are
 *     going to be interacting with eachother through the profiles and advertising what they
 *     offer through their profile, that being said we actually basically need a profile.
 *
 *     WE PROVIDE THE METHODS TO:
 *         -Get the profile, this will yield the user's profile                     --getProfile
 *         *NOTE: I did not say we have a setter here, it is in the constructor!
 *
 * 2: INVENTORY:
 *     We are an application where we have the core "thing" about us being the trading of skills.
 *     And so having an inventory of things to trade (skillZ for our application!) is rather core
 *     without this we just simply couldn't function. Period.
 *     So we instantiate an inventory object in which we tie it to the user in particular.
 *
 *      WE PROVIDE THE METHODS TO:
 *          -Get the inventory, yielding the user's inventory!                    --getInventory
 *         *NOTE: I did not say we have a setter here, it is in the constructor!
 *
 * 3: FRIENDSLIST:
 *     This is going to be a new object that is instantiated in which we have the particular
 *     user that we have that is now going to have a friendslist object associated with themselves.
 *     And then we actually have the friendslist involved with the user.
 *     Without a friendslist we actually will have the user in isolation, which is the exact
 *     opposite of what we want!
 *
 *     WE PROVIDE THE METHODS TO:
 *         -Get the friendslist for the user!                                    --getFriendsList
 *
 *
 * 4: TRADELIST:
 *     This is going to be the actual list of trades that the user has, suppose that the user
 *     themselves are going to have a tradelist object associated with just the user themselves.
 *     And so now we're going to put the entire trade list within this user object.
 *     Keep the access simple, and associated directly with the user themselves.
 *
 *     WE PROVIDE THE METHDOS TO:
 *         -Obtain the list of current trades for this user                   --getTradeList
 *
 *
 * 5: USERID:
 *     This is going to be the unique numerical identifier for the particular user in which the
 *     rest of the application may very easily access and identify this particular user
 *     using a numerical value. Alternatively, the userID can also be returned as a hash value
 *     which allows a secure comparison of users without yielding the actual userID of the
 *     particular users in question.
 *
 *     WE PROVIDE THE METHODS TO:
 *         -Obtain the userID (object)                      --getUserID
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
