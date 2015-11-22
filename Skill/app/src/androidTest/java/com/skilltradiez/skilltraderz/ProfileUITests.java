package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Stephen on 2015-11-21.
 */
public class ProfileUITests extends ActivityInstrumentationTestCase2 {
    ProfileUITests() {
        super(com.skilltradiez.skilltraderz.ProfileActivity.class);
    }

    //TODO Profile Tests
    public void testStart() throws Exception {
        Activity activity = getActivity();
        activity.finish();
    }

    public void testAddRemoveFriend() throws Exception {
        // Add/Remove a Friend
    }

    public void testInventory() throws Exception {
        // Open Inventory
    }

    public void testViewFriends() throws Exception {
        // See their Friends
    }

    public void testTrade() throws Exception {
        // Trade Screen
    }
}
