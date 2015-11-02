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

import java.util.ArrayList;
import java.util.List;

/**
 * FriendsList manages confirmed, pending and blocked friends for a single user.
 */
public class FriendsList {
    private UserID owner;
    private List<UserID> friendsList,
            outgoingFriendRequests,
            incomingFriendRequests,
            blockedUsers;

    FriendsList(UserID owner_id) {
        owner = owner_id;
        friendsList = new ArrayList<UserID>();
        outgoingFriendRequests = new ArrayList<UserID>();
        incomingFriendRequests = new ArrayList<UserID>();
        blockedUsers = new ArrayList<UserID>();
    }

    /**
     * Gets the owner of this friend list.
     */
    public UserID getOwner() {
        return owner;
    }

    /**
     * Gets a list of confirmed friends.
     *
     * @return A list of their UserIDs.
     */
    public List<UserID> getFriends() {
        return friendsList;
    }

    /**
     * Initiates a friend request with another user.
     *
     * @param other_guy The user to initiate the request with.
     * @throws IllegalArgumentException if the user is already a confirmed friend, or is blocked.
     */
    public void requestAddFriend(User other_guy) {
        if (friendsList.contains(other_guy.getUserID())) {
            throw new IllegalArgumentException("User is already a friend, cannot send request.");
        }
        if (blockedUsers.contains(other_guy.getUserID())) {
            throw new IllegalArgumentException("User is blocked, cannot send request.");
        }
        outgoingFriendRequests.add(other_guy.getUserID());
        // TODO send request over the network to the other user
    }

    /**
     * Confirms a potential friend request initiated by another user.
     *
     * @param other_guy the user who initiated the friend request.
     * @throws IllegalArgumentException if the request doesn't exist.
     */
    public void confirmIncomingFriendRequest(User other_guy) {
        int idx = incomingFriendRequests.indexOf(other_guy.getUserID());
        if (idx == -1) {
            throw new IllegalArgumentException("Cannot confirm friend request, request doesn't exist");
        }
        UserID id = incomingFriendRequests.remove(idx);
        friendsList.add(id);
        // TODO send confirmation to the other user
    }

    /**
     * Confirms a friend request that has been accepted by the other user.
     *
     * @param other_guy the user the request was sent to.
     * @throws IllegalArgumentException if the request doesn't exist.
     */
    public void confirmOutgoingFriendRequest(User other_guy) {
        int idx = outgoingFriendRequests.indexOf(other_guy.getUserID());
        if (idx == -1) {
            throw new IllegalArgumentException("Cannot confirm friend request, request doesn't exist");
        }
        UserID id = outgoingFriendRequests.remove(idx);
        friendsList.add(id);
    }

    /**
     * Denies a friend request that was initiated by another user.
     *
     * @param other_guy the person who has not even been given a chance.
     */
    public void denyFriendRequest(User other_guy) {
        incomingFriendRequests.remove(other_guy.getUserID());
    }

    /**
     * Gets a friend by index into the friend list. The indices are probably arbitrary.
     * This shouldn't be used. Use getFriends instead.
     */
    @Deprecated
    public UserID getFriend(Integer index) {
        return friendsList.get(index);
    }

    /**
     * Remove a friend from this friend list.
     *
     * @param terrible_person the user to remove.
     */
    public void removeFriend(User terrible_person) {
        friendsList.remove(terrible_person.getUserID());
        // TODO remove the friend from the other user's friend list as well
    }

    /**
     * Block a user and remove them as a friend (if applicable).
     *
     * @param worst_person
     */
    public void blockUser(User worst_person) {
        removeFriend(worst_person);
        blockedUsers.add(worst_person.getUserID());
    }

    /**
     * Indicates if another user initiated a friend request with this user.
     *
     * @param that_guy the user that initiated the request.
     * @return a boolean indicating whether the request exists.
     */
    public Boolean hasIncomingFriendRequest(User that_guy) {
        return outgoingFriendRequests.contains(that_guy.getUserID());
    }

    /**
     * Indicates if there exists a pending friend request sent to another user by this one.
     *
     * @param that_guy the user that the request was sent to.
     * @return a boolean indicating whether the request exists.
     */
    public Boolean hasOutgoingFriendRequest(User that_guy) {
        return outgoingFriendRequests.contains(that_guy.getUserID());
    }

    /**
     * Indicates if another user is currently a friend of this user.
     *
     * @param that_guy the user to check
     * @return a boolean indicating if they are a friend
     */
    public Boolean hasFriend(User that_guy) {
        return friendsList.contains(that_guy.getUserID());
    }
}
