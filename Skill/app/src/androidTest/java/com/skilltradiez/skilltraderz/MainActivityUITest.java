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
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Cole on 2015-11-17.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2 {

    private EditText nameField;
    private EditText emailField;
    private Button makeUserButton;

    //Initial constructor
    public MainActivityUITest() {
        super(com.skilltradiez.skilltraderz.MainActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
        activity.finish();
    }

    public void testLogin() throws Exception {
        // When you call getActivity() android will start up the app and the activity
        Activity myActivity = getActivity();
        MainActivity activity = (MainActivity)myActivity;

        // Reset the app to known state
        activity.clearFields();

        // Fill in Username
        nameField = activity.getNameField();
        emailField = activity.getEmailField();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                nameField.setText("So Lonely");
                emailField.setText("lonely@person.sad");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Activate OnClickListener
        makeUserButton = activity.getLoginButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                makeUserButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Make sure the tweet was actually saved
        assertEquals("So Lonely", activity.getMasterController().getCurrentUserUsername());
        assertEquals("lonely@person.sad", activity.getMasterController().getCurrentUserEmail());

        activity.finish();
    }
}
