package com.skilltradiez.skilltraderz;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.Espresso.onView;



/**
 * Created by Falon3 on 2015-11-28.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class OfflineUITest {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(
            MainActivity.class);

    @Before
    public void deleteLocal() throws IOException {
        MasterController.getUserDB().getLocal().deleteFile();
    }

    @After
    public void deleteLocalAgain() throws IOException {
        MasterController.getUserDB().getLocal().deleteFile();
    }

    @Test
    //create user before and login, then go offline and do stuff, see if persists
    public void testAddSkillz() throws IOException {
        String username = TestUtilities.getRandomString();
        String email = TestUtilities.getRandomString();
        //login
        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //simulate losing connectivity with bad HTTPClient now
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());

        // click add skill
        onView(withId(R.id.Go_Make_Skill)).perform(click());


        // and set skill properties
        onView(withId(R.id.new_skill_name)).perform(typeText("Poodle"), closeSoftKeyboard());
        onView(withId(R.id.new_skill_description)).perform(typeText("Noodle"), closeSoftKeyboard());
        //onView(withId(R.id.new_category)).perform(typeText("Moodle"), closeSoftKeyboard());
        // Set visibility
        //onView(withId(R.id.is_visible)).perform(click());

        // add skill to db
        onView(withId(R.id.add_skill_to_database)).perform(click());

        //resume connectivity on regular HTTPclient and see if things are still
        // same and not overwritten with what was on DB previously
        MasterController.getUserDB().setHttpClient(new HTTPClient());
        DatabaseController.refresh();

        // go back to to profile and inventory
        onView(withId(R.id.Go_Profile_Menu)).perform(click());
        onView(withId(R.id.inventory)).perform(click());

        // click on the skill
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        // assert that the stuff is set current on screen
        onView(withId(R.id.skillTitle)).check(matches(withText("Poodle")));
        onView(withId(R.id.skill_description)).check(matches(withText("Noodle")));

        //Also check directly in the database that it is the same as local
        assertTrue(((Skill)(MasterController.getUserDB().getSkillz().toArray()[0])).getName().equals("Poodle"));
    }

    @Test
    // Login.CreateAccount.... can't do this offline
    public void testCreateUser() {
        String username = TestUtilities.getRandomString();
        String email = TestUtilities.getRandomString();
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());

        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());
        //assert it didn't go to the main screen because no new account was made
        onView(withId(R.id.usernameField)).check(matches(isDisplayed()));

    }
    @Test
    public void testBrowseFriendInventory() throws UserAlreadyExistsException {
        String friend = TestUtilities.getRandomString();
        String username = TestUtilities.getRandomString();
        String email = TestUtilities.getRandomString();
        //create friend
        DatabaseController.createUser(friend);

        //login
        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //browse all users then select friend and view inventory
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.All_Users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText("Friend for a minute"), closeSoftKeyboard());
        onView(withId(R.id.search_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //add friend
        onView(withId(R.id.right_button)).perform(click());

        //simulate going offline with bad HTTPClient now
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());
        DatabaseController.refresh();

        //browse all users then select friend and view inventory
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.All_Users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText("Friend for a minute"), closeSoftKeyboard());
        onView(withId(R.id.search_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //browse friend inventory now while offline
        onView(withId(R.id.inventory)).perform(click());
        //Doesn't break... success!
    }
    @Test
    public void testStartTrade(){

    }

}
