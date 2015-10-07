
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
