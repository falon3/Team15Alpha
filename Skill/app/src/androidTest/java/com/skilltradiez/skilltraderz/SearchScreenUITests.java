package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Stephen on 2015-11-21.
 */
public class SearchScreenUITests extends ActivityInstrumentationTestCase2 {
    SearchScreenUITests() {
        super(com.skilltradiez.skilltraderz.SearchScreenActivity.class);
    }

    //TODO SearchScreen Tests
    public void testStart() throws Exception {
        Activity activity = getActivity();
        activity.finish();
    }

    public void testUsers() throws Exception  {
        //Give it Users
    }

    public void testSearchUsers() throws Exception {
        //Search for Godfrey
    }

    public void testCategorySearchUsers() throws Exception {
        //Friends
    }

    public void testSkillz() throws Exception {
        //Give it Skillz
    }

    public void testSearchSkillz() throws Exception {
        //Search for Ability To Survive Long Falls
    }

    public void testCategorySearchSkillz() throws Exception {
        //Physical, Stealth, Science, Math, Whatever, ..., Misc
    }

    public void testTrades() throws Exception {
        //Give it Trade
    }

    public void testSearchTrades() throws Exception {
        //Search for that violin teacher
    }

    public void testCategorySearchTrades() throws Exception {
        //Active, Inactive
    }
}
