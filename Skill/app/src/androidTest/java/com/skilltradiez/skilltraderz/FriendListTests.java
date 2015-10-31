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


import android.test.ActivityInstrumentationTestCase2;

public class FriendListTests extends ActivityInstrumentationTestCase2{
    public FriendListTests() {
        super(com.skilltradiez.skilltraderz.FriendListTests.class);
    }

    public void testAddFriend() {
        UserDatabase db = new UserDatabase();
        User user1;
        User user2;
        try {
            user1 = db.createUser("u", "p");
            user2 = db.createUser("u2", "p2");

        // test adding and confirming a friend
        user1.getFriendsList().requestAddFriend(user2);
        assertTrue(user1.getFriendsList().hasOutgoingFriendRequest(user2));
        assertTrue(user2.getFriendsList().hasIncomingFriendRequest(user1));

        user2.getFriendsList().confirmIncomingFriendRequest(user1);
        assertTrue(user1.getFriendsList().hasFriend(user2));
        assertTrue(user2.getFriendsList().hasFriend(user1));
        } catch (UserAlreadyExistsException e) {}
    }

    public void testRemoveFriend() {
        UserDatabase db = new UserDatabase();
        User user1;
        User user2;
        try {
            user1 = db.createUser("u", "p");
            user2 = db.createUser("u2", "p2");

        user1.getFriendsList().requestAddFriend(user2);
        assertTrue(user1.getFriendsList().hasOutgoingFriendRequest(user2));
        assertTrue(user2.getFriendsList().hasIncomingFriendRequest(user1));

        user2.getFriendsList().confirmIncomingFriendRequest(user1);
        assertTrue(user1.getFriendsList().hasFriend(user2));
        assertTrue(user2.getFriendsList().hasFriend(user1));

        // test removing a friend
        user2.getFriendsList().removeFriend(user1);
        assertFalse(user1.getFriendsList().hasFriend(user2));
        assertFalse(user2.getFriendsList().hasFriend(user1));
        } catch (UserAlreadyExistsException e) {}
    }

    public void testBlockUser() {
        UserDatabase db = new UserDatabase();
        User user1;
        User user2;
        try {
            user1 = db.createUser("u", "p");
            user2 = db.createUser("u2", "p2");

        user1.getFriendsList().requestAddFriend(user2);
        assertTrue(user1.getFriendsList().hasOutgoingFriendRequest(user2));
        assertTrue(user2.getFriendsList().hasIncomingFriendRequest(user1));

        user2.getFriendsList().confirmIncomingFriendRequest(user1);
        assertTrue(user1.getFriendsList().hasFriend(user2));
        assertTrue(user2.getFriendsList().hasFriend(user1));

        user2.getFriendsList().blockUser(user1);
        assertFalse(user1.getFriendsList().hasFriend(user2));
        assertFalse(user2.getFriendsList().hasFriend(user1));

        // can't send a friend request to a blocked person
        user1.getFriendsList().requestAddFriend(user2);
        assertFalse(user1.getFriendsList().hasOutgoingFriendRequest(user2));
        assertFalse(user2.getFriendsList().hasIncomingFriendRequest(user1));
        } catch (UserAlreadyExistsException e) {}
    }

    public void testGetFriends() {
        UserDatabase db = new UserDatabase();
        User user1;
        User user2;
        try {
            user1 = db.createUser("u", "p");
            user2 = db.createUser("u2", "p2");

        user1.getFriendsList().requestAddFriend(user2);
        user2.getFriendsList().confirmIncomingFriendRequest(user1);

        assertEquals(user1.getFriendsList().getFriends().get(0), user2.getUserID());
        assertEquals(user2.getFriendsList().getFriends().get(0), user1.getUserID());
        } catch (UserAlreadyExistsException e) {}
    }
}
