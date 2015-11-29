package com.skilltradiez.skilltraderz;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import static android.support.test.espresso.Espresso.onView;
import static org.hamcrest.Matchers.not;


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
    public void deleteDatabase() {
        DatabaseController.deleteAllData();
    }

    @After
    public void deleteDatabaseAgain() {
        DatabaseController.deleteAllData();
    }

    @Test
    //create user before and login, then go offline and do stuff, see if persists
    public void testAddSkillz() {
        deleteDatabase();
        //login
        onView(withId(R.id.usernameField)).perform(typeText("oFLINEUSER"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("off@line"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //simulate losing connectivity with bad HTTPClient now
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());

        // click add skill
        onView(withId(R.id.add_a_new_skill)).perform(click());


        // and set skill properties
        onView(withId(R.id.new_skill_name)).perform(typeText("Poodle"), closeSoftKeyboard());
        onView(withId(R.id.new_skill_description)).perform(typeText("Noodle"), closeSoftKeyboard());
        //onView(withId(R.id.new_category)).perform(typeText("Moodle"), closeSoftKeyboard());
        // Set visibility
        //onView(withId(R.id.is_visible)).perform(click());

        // add skill to db
        onView(withId(R.id.add_skill_to_database)).perform(click());

        // go back to home screen and then to profile and inventory
        pressBack();

        //resume connectivity on regular HTTPclient and see if things are still
        // same and not overwritten with what was on DB previously
        MasterController.getUserDB().setHttpClient(new HTTPClient());
        DatabaseController.refresh();

        onView(withId(R.id.Go_Profile_Menu)).perform(click());
        onView(withId(R.id.inventory)).perform(click());

        // click on the skill
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        // assert that the stuff is set current on screen
        onView(withId(R.id.skillTitle)).check(matches(withText("Poodle")));
        onView(withId(R.id.skill_description)).check(matches(withText("Noodle")));

        //Also check directly in the database that it is the same as local
        assertTrue(((Skill)(MasterController.getUserDB().getSkillz().toArray()[0])).getName().equals("Poodle"));
        deleteDatabase();
    }

    @Test
    // Login.CreateAccount.... can't do this offline
    public void testCreateUser() {
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());
        deleteDatabase();
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());
        //assert it didn't go to the main screen because no new account was made
        onView(withId(R.id.usernameField)).check(matches(isDisplayed()));
        deleteDatabase();
    }
    @Test
    public void testBrowseFriendInventory() throws UserAlreadyExistsException {
        //create friend
        DatabaseController.createUser("Friend for a minute");

        //login
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //find friend
        onView(withId(R.id.browse_users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText("Friend for a minute"), closeSoftKeyboard());
        onView(withId(R.id.search_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //add friend
        onView(withId(R.id.add_friend)).perform(click());

        //simulate going offline with bad HTTPClient now
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());
        DatabaseController.refresh();

        //browse friend inventory now while offline
        

    }
}
