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
import java.util.List;

/**
 * FriendsList manages confirmed, pending and blocked friends for a single user.
 */
public class FriendsList extends Notification {
    private ID owner;
    private List<ID> friendsList;
    private List<ID> newFriends;
    private List<ID> oldFriends;

    FriendsList(ID owner_id) {
        owner = owner_id;
        friendsList = new ArrayList<ID>();
        newFriends = new ArrayList<ID>();
        oldFriends = new ArrayList<ID>();
    }

    /**
     * Gets the owner of this friend list.
     */
    public ID getOwner() {
        return owner;
    }

    /**
     * Gets a list of confirmed friends.
     *
     * @return A list of their UserIDs.
     */
    public List<ID> getFriends() {
        return friendsList;
    }

    /**
     * Remove a friend from this friend list.
     *
     * @param terrible_person the user to remove.
     */
    public void removeFriend(User terrible_person) {
        friendsList.remove(terrible_person.getUserID());
        oldFriends.add(terrible_person.getUserID());
        notifyDB();
    }

    public void addFriend(User great_person) {
        if (hasFriend(great_person)) return;
        friendsList.add(great_person.getUserID());
        newFriends.add(great_person.getUserID());
        notifyDB();
    }

    /**
     * Indicates if another user is currently a friend of this user.
     *
     * @param that_guy the user to check
     * @return a boolean indicating if they are a friend
     */
    public boolean hasFriend(User that_guy) {
        return friendsList.contains(that_guy.getUserID());
    }

    public boolean commit(UserDatabase userDB) {
        for (ID id : newFriends) {
            User newFriend = userDB.getAccountByUserID(id);
            newFriend.getFriendsList().addFriend(userDB.getAccountByUserID(owner));
            try {
                userDB.getElastic().addDocument("user", newFriend.getProfile().getUsername(), newFriend);
            } catch (IOException e) {
                // try again later
                return false;
            }
        }
        newFriends.clear();
        
        for (ID id : oldFriends) {
            User deadFriend = userDB.getAccountByUserID(id);
            deadFriend.getFriendsList().removeFriend(userDB.getAccountByUserID(owner));
            try {
                userDB.getElastic().addDocument("user", deadFriend.getProfile().getUsername(), deadFriend);
            } catch (IOException e) {
                // try again later
                return false;
            }
        }
        oldFriends.clear();
        return true;
    }
}
