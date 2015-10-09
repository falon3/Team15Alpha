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
		user.getProfile().setNickname("Foobar");
		assertEquals(user.getProfile().getNickname(), "Foobar");
	}

	public void testSetPassword() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		user.getProfile().setPassword("hunter2");
		assertEquals(user.getProfile().getPassword(), "hunter2");
	}

	public void testSetEmail() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		user.getProfile().setEmail("apersonsname@awebsite.com");
		assertEquals(user.getProfile().getEmail(), "apersonsname@awebsite.com");
	}

	public void

	public void testFieldsTooLong() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		try {
			user.getProfile().setEmail("apersonsname@awebsite.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
			assertTrue(false); // if we got here, the exception didn't happen
		} catch (InvalidArgumentExecption e) {
		}
		try {
			user.getProfile().setNickname("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
			assertTrue(false); // if we got here, the exception didn't happen
		} catch (InvalidArgumentExecption e) {
		}
		try {
			user.getProfile().setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
			assertTrue(false); // if we got here, the exception didn't happen
		} catch (InvalidArgumentExecption e) {
		}
	}

	public void testAvatar() {
		UserDatabase db = new UserDatabase();
		User user = db.createUser("Username", "Password");
		Picture avatar = new Picture("hello.jpeg");
		Picture avatar2 = new Picture("hello.jpg");

		user.getProfile().setAvatar(avatar);
		assertEquals(user.getProfile().getAvatar(), avatar);

		user.getProfile().setAvatar(avatar2);
		assertEquals(user.getProfile().getAvatar(), avatar2);

		user.getProfile().deleteAvatar();
		assertNull(user.getProfile().getAvatar());
	}
}
