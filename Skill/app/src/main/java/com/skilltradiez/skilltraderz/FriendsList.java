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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * In an application that is based upon users trading skills with other users... does it not
 * make complete sense to have some sort of list that is assocaited with all of the users
 * that this user is correlated with? This is what this FriendsList class is all about! Put
 * users together into friendship groups! Lovely!
 *
 * This is going to maintain a few attributes such as the current identifier for the current user
 * who actually "owns" this friendslist. This is what will actually associate the user to the
 * object.
 *
 * Then we have three attributes lists that are going to relate the user in question to
 * a list of friends, then a list of old friends and then a list of new friends. We're going to
 * then hand the methods that are going to allow the classes of the remove, add, get, check if
 * the user has, a friend. And then another method that is going to actually allow the user
 * to commit all of their changes to their friendslists to the database.
 *
 * FriendsList manages confirmed, pending and blocked friends for a single user.
 */

public class FriendsList extends Notification {
    /**Class Variables:
     * 1: owner, an ID Object that is identifying the owner of this FriendsList Object.
     * 2: friendsList, a list of ID Objects that are going to be the ID Objects corresponding
     *     to the friends of this user who owns this FriendsList Object.
     */
    private ID owner;
    private List<ID> friendsList;
    private ID mostRecentFriend;

    /**
     * Takes in the ID Object for the owner of this FriendsList Object and will assign the
     * local variable owner to the ID passed into the constructor. And then it will instantiate
     * a new ArrayList of ID objects.
     *
     * @param owner_id ID Object representing the owner of the FriendsList.
     */
    FriendsList(ID owner_id) {
        owner = owner_id;
        friendsList = new ArrayList<ID>();
    }

    /**
     * Returns the ID Object for the owner of the FriendsList Object.
     * @return ID Object
     */
    public ID getOwner() {
        return owner;
    }

    /**
     * When invoked, will return the list of friends from the FriendsList Object.
     * @return List of ID Objects.
     */
    public List<ID> getFriends() {
        return friendsList;
    }

    /**
     * Removes the passed in User Object parameter from the FriendsList. Notifies DB of change.
     * @param terrible_person User Object.
     */
    public void removeFriend(User terrible_person) {
        friendsList.remove(terrible_person.getUserID());
        mostRecentFriend = terrible_person.getUserID();
        notifyDB();
    }

    /**
     * Adds the passed in User Object parameter to the FriendsList. Notifies DB of change.
     * @param great_person User Object.
     */
    public void addFriend(User great_person) {
        if (hasFriend(great_person)) return;
        friendsList.add(great_person.getUserID());
        mostRecentFriend = great_person.getUserID();
        notifyDB();
    }

    /**
     * Will return true/false if the passed in User Object is present in the FriendsList.
     * @param that_guy User Object.
     * @return Boolean. True/False.
     */
    public boolean hasFriend(User that_guy) {
        return friendsList.contains(that_guy.getUserID());
    }

    /**
     * When invoked, this method will take in the user database and will update the elastic/DB.
     *
     * Assign an owner variable, obtained from the database controller method to get an account
     *     by a given ID-- where we supply the "getOwner()" method's ID.
     * We then attempt to update the document using the Elastic class's updateDocument method
     *     using the owner's username and a few other formatting details.
     * Then, for the sake of completeness, we have it so that this returns a boolean flag upon
     *     success = TRUE, or failure = FALSE. We also catch IOExceptions since the database
     *     may give those if there is some sort of problem with the connection.
     *
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
    public boolean commit(UserDatabase userDB) {
        User owner = DatabaseController.getAccountByUserID(getOwner());
        try {
            userDB.getElastic().updateDocument("user", owner.getProfile().getUsername(), this, "friendsList");
        } catch (IOException e) {
            // try again later
            return false;
        }
        return true;
    }

    /**
     * The most simple getter ever. Literally returns "FriendsList".
     * @return String "FriendsList"
     */
    public String getType() {
        return DatabaseController.getAccountByUserID(owner).getProfile().getName()+"'s FriendsList";
    }

    /**
     * Basic getter method that returns either "Removed Friend" or "Changed"
     * @return String "Removed Friend" or "Changed".
     */
    public String getStatus() {
        if (friendsList.isEmpty())
            return "Removed Friend";
        return "Changed";
    }

    /**
     * This method, when invoked, will return the description for the FriendsList.
     * @return String of the description.
     */
    public String getDescription() {
        if (mostRecentFriend == null) return "how did this happen???";
        return DatabaseController.getAccountByUserID(mostRecentFriend).getProfile().getName()+" was added or removed.";
    }

    public boolean relatesToUser(ID userID) {
        return userID.equals(owner);
    }
}
