package com.skilltradiez.skilltraderz;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
/**
 * Created by Elyse on 11/21/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(
            MainActivity.class);

    @Before
    public void deleteDatabase(){
        new MasterController().crazyDatabaseDeletion();
    }

    @After
    public void deleteDatabaseAgain() {
        new MasterController().crazyDatabaseDeletion();
    }

    @Test
    // Login.CreateAccount
    public void testCreateUser() {
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());
        onView(withId(R.id.go_to_profile)).perform(click());
        onView(withId(R.id.user_name)).check(matches(withText("Elyse")));
    }

    // Inventory.AddSkillz + ExamineSkillz (examines after adding)
    @Test
    public void testAddSkillz() {
        //login
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        // click add skill
        onView(withId(R.id.add_a_new_skill)).perform(click());

        // set skill properties
        onView(withId(R.id.new_skill_name)).perform(typeText("Sname"), closeSoftKeyboard());
        onView(withId(R.id.new_skill_description)).perform(typeText("Sdesc"), closeSoftKeyboard());
        onView(withId(R.id.new_category)).perform(typeText("Scate"), closeSoftKeyboard());
        // Set visibility
        //onView(withId(R.id.is_visible)).perform(click());

        // add skill to db
        onView(withId(R.id.add_skill_to_database)).perform(click());

        // go back to home screen and then to profile and inventory
        pressBack();
        onView(withId(R.id.go_to_profile)).perform(click());
        onView(withId(R.id.inventory)).perform(click());

        // click on the skill
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        // assert that the stuff is set current
        onView(withId(R.id.skillTitle)).check(matches(withText("Sname")));
        onView(withId(R.id.skill_description)).check(matches(withText("Sdesc")));
    }

    @Test
    // Inventory.RemoveSkillz
    public void testRemoveSkillz() {
        // start by adding a skill
        testAddSkillz();
        onView(withId(R.id.add_remove_skill)).perform(click());
        pressBack();
        // Not sure how espresso really works, this seems like a hack but it works.
        // if an exception is not thrown getting the first item, then there was an item and
        // therefore it wasn't removed successfully.
        try {
            onData(anything()).inAdapterView(allOf(withId(R.id.results_list), isDisplayed())).atPosition(0).check(matches(isDisplayed()));
        } catch (Exception e) {
            return;
        }
        assertTrue(false);
    }

    @Test
    // Friends.AddFriend
    public void testAddFriend() {
        //create friend
        new MasterController().createNewUser("Friend", "fri@end.ly");

        //login
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //find friend
        onView(withId(R.id.browse_users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText("Friend"), closeSoftKeyboard());
        onView(withId(R.id.search_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //add friend
        onView(withId(R.id.add_friend)).perform(click());

        //check that friend was added
        MasterController mc = new MasterController();
        assertTrue(mc.getUserByName("Elyse").getFriendsList().hasFriend(mc.getUserByName("Friend")));
    }

    @Test
    // Friends.RemoveFriend
    public void testRemoveFriend() {
        //add friend first
        testAddFriend();

        //remove friend
        onView(withId(R.id.add_friend)).perform(click());

        //check that friend was removed
        MasterController mc = new MasterController();
        assertFalse(mc.getUserByName("Elyse").getFriendsList().hasFriend(mc.getUserByName("Friend")));
    }

    @Test
    public void testBrowseFriendProfile() {
        //add friend first
        testAddFriend();

        // Check profile details
        onView(withId(R.id.user_name)).check(matches(withText("Friend")));
    }
}