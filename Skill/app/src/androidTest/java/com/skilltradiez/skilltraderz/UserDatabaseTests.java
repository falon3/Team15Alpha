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


public class UserDatabaseTests {

	public void testCreateAccount() {
		UserDatabase db = new UserDatabase();

		User user = db.createUser("Username", "Password");
		assertEquals(db.getAccountByUsername("Username"), user);
	}

	public void testCreateAccountFailCase() {
		UserDatabase db = new UserDatabase();

		User user = db.createUser("Username", "Password");
		try {
			User user2 = db.createUser("Username", "Password");
			assertTrue(false);
		} catch (UserAlreadyExistsException e) {
			assertTrue(true);
		}
	}

	public void testLogin() {
		UserDatabase db = new UserDatabase();

		User user = db.createUser("Username", "Password");
		assertEquals(db.login("Username", "Password"), true);
	}

	public void testDatabasePersistence() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		db.save();

		// The new database should contain all the previous changes
		db = new UserDatabase();
		assertEquals(db.getAccountByUsername("Username"), user);
	}

	public void testTradelistPersistence() {
		UserDatabase db = new UserDatabase();
		User user1 = db.createUser("Username1", "Password"),
		     user2 = db.createUser("Username2", "Password");

		TradeList tl = user1.getTradeList();
		tl.createTrade(user1, user2, new List<Skill>())

		// The new database should contain all the previous changes
		db = new UserDatabase();
		user1 = db.getAccountByUsername("Username1");
		user2 = db.getAccountByUsername("Username2");

		tl = user1.getTradeList();
		assertEquals(tl.getMostRecentTrade().getActor2(), user2);
	}

	public void testFriendListPersistence() {
		UserDatabase db = new UserDatabase();
		User user1 = db.createUser("Username1", "Password"),
		     user2 = db.createUser("Username2", "Password");

		FriendList fl = user1.getFriendList();

		// The new database should contain all the previous changes
		db = new UserDatabase();
		user1 = db.getAccountByUsername("Username1");
		user2 = db.getAccountByUsername("Username2");

		fl = user1.getFriendList();
		assertEquals(fl.getAccountByNickname("Username2"), user2);
	}
}
