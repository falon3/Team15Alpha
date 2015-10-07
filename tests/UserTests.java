/*
 * <one line to give the program's name and a brief idea of what it does.>
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


public class UserTests {
	User user;
	
	public void testCreateAccount() {
		user = new User();
		
		user.createAccount("Username", "Password");
		assertTrue(user.isAccountCreated());
	}
	
	public void testCreateAccountFailCase() {
		user = new User();
		User user2 = new User();
		
		user.createAccount("Username", "Password");
		user2.createAccount("Username", "Password");
		
		assertFalse(user2.isAccountCreated());
	}
	
	public void testSetName() {
		user = new User();
		
		user.createAccount("Username", "Password");
		user.setName("Jacob");
		assertTrue(user.getName().equals("Jacob"));
	}
	
	public void testSetNickName() {
		user = new User();
		
		user.createAccount("Username", "Password");
		user.setName("Jacob");
		user.setNickname("J.");
		assertTrue(user.getNickname().equals("J."));
	}
	
	public void testMakeSkillz() {
		user = new User();
		
		Boolean vid, pic;
		
		user.createAccount("Username", "Password");
		vid = user.makeSkillz("Name Of Skill", new Video());
		pic = user.makeSkillz("Name Of Skill", new Picture());
		assertTrue(vid && pic);
	}
	
	public void testSetLocation() {
		user = new User();
		
		user.createAccount("Username", "Password");
		user.setLocation("Edmonton");
		assertTrue(user.getLocation().equals("Edmonton"));
	}
	
	/* The user will send their skill to a
	 * database that any user can view
	 */
	public void testMakeOffer() {
		 
	}
	
	/* The user will send their written request to a
	 * database that any user can view
	 */
	public void testMakeRequest() {
		 
	}
}
