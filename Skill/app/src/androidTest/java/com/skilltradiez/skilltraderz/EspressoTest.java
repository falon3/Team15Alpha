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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.test.suitebuilder.annotation.LargeTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
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
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static android.support.test.runner.lifecycle.Stage.RESUMED;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

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
    // Login.CreateAccount
    public void testCreateUser() {
        String username = TestUtilities.getRandomString();
        String email = TestUtilities.getRandomString();

        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());
        onView(withId(R.id.Go_Profile_Menu)).perform(click());
        onView(withId(R.id.user_name)).check(matches(withText(username)));
    }

    // Inventory.AddSkillz + ExamineSkillz (examines after adding)
    @Test
    public void testAddSkillz() {
        String username = "Drew" + TestUtilities.getRandomString();
        String email = "E" + TestUtilities.getRandomString();
        //login
        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        // click add skill
        onView(withId(R.id.Go_Make_Skill)).perform(click());

        // set skill properties
        onView(withId(R.id.new_skill_name)).perform(typeText("Sname"), closeSoftKeyboard());
        onView(withId(R.id.new_skill_description)).perform(typeText("Sdesc"), closeSoftKeyboard());
        onView(withId(R.id.radioButton)).perform(click());
        // Set visibility
        //onView(withId(R.id.is_visible)).perform(click());

        // add skill to db
        onView(withId(R.id.add_skill_to_database)).perform(click());

        // go back to home screen and then to profile and inventory
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.Go_Profile_Menu)).perform(click());
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

    public String usernameAddfriend;
    public String friendAddfriend;
    public String emailAddfriend;

    @Test
    // Friends.AddFriend
    public void testAddFriend() throws UserAlreadyExistsException {
        friendAddfriend = "temp_f" + TestUtilities.getRandomString();
        usernameAddfriend = "Sam" + TestUtilities.getRandomString();
        emailAddfriend = "E" + TestUtilities.getRandomString();
        //create friend
        DatabaseController.createUser(friendAddfriend);

        //login
        onView(withId(R.id.usernameField)).perform(typeText(usernameAddfriend), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(emailAddfriend), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //return home to browse users find friend
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.All_Users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText(friendAddfriend), closeSoftKeyboard());
        onView(withId(R.id.search_bar_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //add friend
        onView(withId(R.id.right_button)).perform(click());

        //check that friend was added
        MasterController mc = new MasterController();
        assertTrue(mc.getUserByName(usernameAddfriend).getFriendsList().hasFriend(mc.getUserByName(friendAddfriend)));
    }

    @Test
    // Friends.RemoveFriend
    public void testRemoveFriend() throws UserAlreadyExistsException {
        //add friend first
        testAddFriend();

        //remove friend
        onView(withId(R.id.right_button)).perform(click());

        //check that friend was removed
        MasterController mc = new MasterController();
        assertFalse(mc.getUserByName(usernameAddfriend).getFriendsList().hasFriend(mc.getUserByName(friendAddfriend)));
    }

    @Test
    public void testBrowseFriendInventory() throws UserAlreadyExistsException {
        //add friend first
        testAddFriend();

        // Check profile details
        onView(withId(R.id.user_name)).check(matches(withText(friendAddfriend)));

    }
    @Test
    public void testStartTrade() throws UserAlreadyExistsException {
        String tradeFriend = "Al" + TestUtilities.getRandomString();
        String tradeUsername = "Bo" + TestUtilities.getRandomString();
        String tradeEmail = "Em" + TestUtilities.getRandomString();
        //create friend, make sure they have a skill, add them
        DatabaseController.createUser(tradeFriend);
        GeneralMenuActivity g_activity = (GeneralMenuActivity) getActivityInstance();
        g_activity.getMasterController().makeNewSkill("miscellaneous", "stupid trade test", "High", true, new ArrayList<Image>());

        //login and add friend
        onView(withId(R.id.usernameField)).perform(typeText(tradeUsername), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(tradeEmail), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //return home to browse users find friend
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.All_Users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText(tradeFriend), closeSoftKeyboard());
        onView(withId(R.id.search_bar_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //add friend
        onView(withId(R.id.right_button)).perform(click());

        // start trade
        onView(withId(R.id.left_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.other_invList)).atPosition(0).perform(click());
        onView(withId(R.id.sendTrade)).perform(click());

        //return home and view trade history
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.Trade_History)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        // check that a trade was added
        onData(anything()).inAdapterView(allOf(withId(R.id.requestList), isDisplayed())).atPosition(0).check(matches(isDisplayed()));

    }

    Activity currentActivity;
    public Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }
}