package com.skilltradiez.skilltraderz;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
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

    // Inventory.AddSkillz
    @Test
    public void testAddSkillz() {
        onView(withId(R.id.usernameField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText("Elyse"), closeSoftKeyboard());
        onView(withId(R.id.beginApp)).perform(click());
        onView(withId(R.id.add_a_new_skill)).perform(click());
        onView(withId(R.id.new_skill_name)).perform(typeText("Sname"), closeSoftKeyboard());
        onView(withId(R.id.new_skill_description)).perform(typeText("Sdesc"), closeSoftKeyboard());
        onView(withId(R.id.new_category)).perform(typeText("Scate"), closeSoftKeyboard());
        onView(withId(R.id.add_skill_to_database)).perform(click());
        pressBack();
        onView(withId(R.id.go_to_profile)).perform(click());
        onView(withId(R.id.inventory)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.inventory_list)).atPosition(0).perform(click());
        onView(withId(R.id.skillTitle)).check(matches(withText("Sname")));
        onView(withId(R.id.skill_description)).check(matches(withText("Sdesc")));
    }

    @Test
    public void testRemoveSkillz() {
        // start by adding a skill
        testAddSkillz();
        onView(withId(R.id.add_remove_skill)).perform(click());
        pressBack();
        // Not sure how espresso really works, this seems like a hack but it works.
        // if an exception is not thrown getting the first item, then there was an item and
        // therefore it wasn't removed successfully.
        try {
            onData(anything()).inAdapterView(allOf(withId(R.id.inventory_list), isDisplayed())).atPosition(0).check(matches(isDisplayed()));
        } catch (Exception e) {
            return;
        }
        assertTrue(false);
    }

    /*@Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.changeTextBt)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard());
        onView(withId(R.id.activityChangeTextBtn)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.show_text_view)).check(matches(withText(STRING_TO_BE_TYPED)));
    }*/
}