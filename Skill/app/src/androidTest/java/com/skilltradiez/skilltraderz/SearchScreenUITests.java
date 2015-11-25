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
