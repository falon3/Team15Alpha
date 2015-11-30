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
import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
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
        String username = "Off U" +TestUtilities.getRandomString();
        String email = "Off E" + TestUtilities.getRandomString();
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
        onView(withId(R.id.radioButton)).perform(click());
        // Set visibility
        onView(withId(R.id.is_visible)).perform(click());

        // add skill to db
        onView(withId(R.id.add_skill_to_database)).perform(click());

        //resume connectivity on regular HTTPclient and see if things are still
        // same and not overwritten with what was on DB previously
        MasterController.getUserDB().setHttpClient(new HTTPClient());
        DatabaseController.refresh();

        // go back to to profile and inventory
        onView(withId(R.id.Go_Home_Menu)).perform(click());
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
        String username = "Fo" + TestUtilities.getRandomString();
        String email = "Em" + TestUtilities.getRandomString();
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());

        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());
        //assert it didn't go to the main screen because no new account was made
        onView(withId(R.id.usernameField)).check(matches(isDisplayed()));

    }
    @Test
    public void testBrowseFriendInventory() throws UserAlreadyExistsException, NoInternetException {
        String friend = "Fr_Of" + TestUtilities.getRandomString();
        String username = "User" + TestUtilities.getRandomString();
        String email = "eTest" + TestUtilities.getRandomString();
        //create friend
        DatabaseController.createUser(friend);

        //login
        onView(withId(R.id.usernameField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //browse all users then select friend and view inventory
        onView(withId(R.id.Go_Home_Menu)).perform(click());
        onView(withId(R.id.All_Users)).perform(click());
        onView(withId(R.id.search_bar)).perform(typeText(friend), closeSoftKeyboard());
        onView(withId(R.id.search_bar_button)).perform(click());
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
        onView(withId(R.id.search_bar_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.results_list)).atPosition(0).perform(click());

        //browse friend inventory now while offline
        onView(withId(R.id.inventory)).perform(click());
        //Doesn't break... success!
    }
    // should be able to start a trade offline
    @Test
    public void testStartTrade() throws UserAlreadyExistsException, NoInternetException {
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

        //lose connectivity and start trade
        //simulate going offline with bad HTTPClient now
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());
        DatabaseController.refresh();

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

        //resume connectivity on regular HTTPclient and make sure things don't break
        MasterController.getUserDB().setHttpClient(new HTTPClient());
        DatabaseController.refresh();

        //Also check again to see that local changes weren't overwritten by database info
        // so check if trade still exists

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
