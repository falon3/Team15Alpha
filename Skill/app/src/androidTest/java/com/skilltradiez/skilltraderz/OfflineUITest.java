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
    // Login.CreateAccount.... can't do this offline
    public void testCreateUser() {
        MasterController.getUserDB().setHttpClient(new BrokenHTTPClient());
        deleteDatabase();
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());

        //assert it didn't go to the main screen because no new account was made
        onView(withId(R.id.browse_skillz)).check(matches(not(isDisplayed())));
        //onView(withId(R.id.go_to_profile)).perform(click());
        //onView(withId(R.id.user_name)).check(matches(withText("Elyse")));
    }

}
