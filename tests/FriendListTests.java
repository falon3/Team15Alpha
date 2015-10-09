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


public class FriendListTests {
	public void testAddRemoveFriend() {
		UserDatabase db = new UserDatabase();
		User user1 = db.createUser("u", "p");
		User user2 = db.createUser("u2", "p2");

		user1.getFriendList().requestAddFriend(user2);
		assertTrue(user1.getFriendList().hasPendingFriendRequest(user2));
		assertTrue(user2.getFriendList().hasPendingFriendRequest(user1));

		user2.getFriendList().confirmFriendRequest(user1);
		assertTrue(user1.getFriendList().hasFriend(user2));
		assertTrue(user2.getFriendList().hasFriend(user1));

		user2.getFriendList().removeFriend(user1);
		assertFalse(user1.getFriendList().hasFriend(user2));
		assertFalse(user2.getFriendList().hasFriend(user1));
	}

	public void testBlockUser() {
		UserDatabase db = new UserDatabase();
		User user1 = db.createUser("u", "p");
		User user2 = db.createUser("u2", "p2");

		user1.getFriendList().requestAddFriend(user2);
		assertTrue(user1.getFriendList().hasPendingFriendRequest(user2));
		assertTrue(user2.getFriendList().hasPendingFriendRequest(user1));

		user2.getFriendList().confirmFriendRequest(user1);
		assertTrue(user1.getFriendList().hasFriend(user2));
		assertTrue(user2.getFriendList().hasFriend(user1));

		user2.getFriendList().blockFriend(user1);
		assertFalse(user1.getFriendList().hasFriend(user2));
		assertFalse(user2.getFriendList().hasFriend(user1));

		// can't send a friend request to a blocked person
		user1.getFriendList().requestAddFriend(user2);
		assertFalse(user1.getFriendList().hasPendingFriendRequest(user2));
		assertFalse(user2.getFriendList().hasPendingFriendRequest(user1));
	}

	public void testGetFriends() {
		UserDatabase db = new UserDatabase();
		User user1 = db.createUser("u", "p");
		User user2 = db.createUser("u2", "p2");

		user1.getFriendList().requestAddFriend(user2);
		user2.getFriendList().confirmFriendRequest(user1);

		assertEquals(user1.getFriendList().get(0), user2);
		assertEquals(user2.getFriendList().get(0), user1);
	}
}
