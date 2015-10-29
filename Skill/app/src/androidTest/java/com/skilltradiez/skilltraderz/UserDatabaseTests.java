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

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseTests extends ActivityInstrumentationTestCase2 {
	public UserDatabaseTests() {
		super(com.skilltradiez.skilltraderz.UserDatabaseTests.class);
	}

	public void testCreateAccount() {
		UserDatabase db = new UserDatabase();
		User user;

		try {
			user = db.createUser("Username", "Password");
		} catch (UserAlreadyExistsException e) {

		}
		assertEquals(db.getAccountByUsername("Username"), user);
	}

	public void testCreateAccountFailCase() {
		UserDatabase db = new UserDatabase();
		User user;

		try {
			user = db.createUser("Username", "Password");
		} catch (UserAlreadyExistsException e) {

		}

		try {
			User user2 = db.createUser("Username", "Password");
			assert(false);
		} catch (UserAlreadyExistsException e) {
			assert(true);
		}
	}

	public void testLogin() {
		UserDatabase db = new UserDatabase();
		User user;

		try {
			user = db.createUser("Username", "Password");
		} catch (UserAlreadyExistsException e) {

		}

		assertTrue(db.login("Username", "Password") != null);
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
		tl.createTrade(user1, user2, new ArrayList<Skill>());

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
