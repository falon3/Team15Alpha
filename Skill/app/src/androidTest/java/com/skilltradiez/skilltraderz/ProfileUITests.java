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
