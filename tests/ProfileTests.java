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


public class ProfileTests {
	public void testSetNickname() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		user.setNickname("Foobar");
		assertEquals(user.getNickname(), "Foobar");
	}

	public void testSetPassword() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		user.setPassword("hunter2");
		assertEquals(user.getPassword(), "hunter2");
	}

	public void testSetEmail() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		user.setEmail("apersonsname@awebsite.com");
		assertEquals(user.getEmail(), "apersonsname@awebsite.com");
	}

	public void testFieldsTooLong() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		try {
			user.setEmail("apersonsname@awebsite.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
			assertTrue(false); // if we got here, the exception didn't happen
		} catch (InvalidArgumentExecption e) {
		}
		try {
			user.setNickname("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
			assertTrue(false); // if we got here, the exception didn't happen
		} catch (InvalidArgumentExecption e) {
		}
		try {
			user.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
			assertTrue(false); // if we got here, the exception didn't happen
		} catch (InvalidArgumentExecption e) {
		}
	}
}
