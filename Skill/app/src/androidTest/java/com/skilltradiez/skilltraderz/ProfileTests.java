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


import android.test.ActivityInstrumentationTestCase2;

public class ProfileTests extends ActivityInstrumentationTestCase2 {

    public ProfileTests() {
        super(com.skilltradiez.skilltraderz.ProfileTests.class);
    }
    // tests for ConfigureProfileDetails

    public void testSetNickname() {
        try {
            UserDatabase db = new UserDatabase();
            User user = db.createUser("Username", "Password");
            try {
                user.getProfile().setNickname("Foobar");
            } catch (IllegalArgumentException e) {
            }
            assertEquals(user.getProfile().getNickname(), "Foobar");
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testSetPassword() {
        try {
            UserDatabase db = new UserDatabase();
            User user = db.createUser("Username", "Password");
            try {
                user.getProfile().setPassword("hunter2");
            } catch (IllegalArgumentException e) {
            }
            assertTrue(user.getProfile().isPassword("hunter2"));
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testSetEmail() {
        try {
            UserDatabase db = new UserDatabase();
            User user = db.createUser("Username", "Password");
            try {
                user.getProfile().setEmail("apersonsname@awebsite.com");
            } catch (IllegalArgumentException e) {
            }
            assertEquals(user.getProfile().getEmail(), "apersonsname@awebsite.com");
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testFieldsTooLong() {
        try {
            UserDatabase db = new UserDatabase();
            User user = db.createUser("Username", "Password");
            try {
                user.getProfile().setEmail("apersonsname@awebsite.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
            try {
                user.getProfile().setNickname("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
            try {
                user.getProfile().setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testAvatar() {
        try {
            UserDatabase db = new UserDatabase();
            User user = db.createUser("Username", "Password");
            Image avatar = new Image("hello.jpeg");
            Image avatar2 = new Image("hello.jpg");

            //avatar should be empty to begin with
            assertNull(user.getProfile().getAvatar());

            //test setting avatar
            user.getProfile().setAvatar(avatar);
            assertEquals(user.getProfile().getAvatar(), avatar);

            //test changing avatar
            user.getProfile().setAvatar(avatar2);
            assertEquals(user.getProfile().getAvatar(), avatar2);

            //test deleting avatar
            user.getProfile().deleteAvatar();
            assertNull(user.getProfile().getAvatar());
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testSetDownloadImages() {
        try {
            UserDatabase db = new UserDatabase();
            User user = db.createUser("Username", "Password");

            user.getProfile().setShouldDownloadImages(true);
            assertTrue(user.getProfile().getShouldDownloadImages());
        } catch (UserAlreadyExistsException e) {
        }
    }
}
