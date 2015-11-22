package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Stephen on 2015-11-21.
 */
public class InventoryUITests extends ActivityInstrumentationTestCase2 {
    InventoryUITests() {
        super(com.skilltradiez.skilltraderz.InventoryActivity.class);
    }

    //TODO Inventory Tests
    public void testStart() throws Exception {
        Activity activity = getActivity();
        activity.finish();
    }

    public void testClickSkill() throws Exception {
        // Click on one skill in the list
    }

    public void testSearchSkillz() throws Exception {
        // Filter skillz
    }

    public void testStartTrade() throws Exception {
        // Press the button
    }
}
