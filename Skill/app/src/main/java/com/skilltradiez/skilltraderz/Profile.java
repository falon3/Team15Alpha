package com.skilltradiez.skilltraderz;

import java.io.IOException;

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
public class Profile implements Notification {

    private String location = "";
    private Boolean shouldDownloadImages = true;
    private Image avatar;
    private String username = "";
    private String email = "";

    Profile(String username) {
        setUsername(username);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location.length() > 50)
            throw new IllegalArgumentException();
        this.location = location;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) throws IllegalArgumentException {
        //TODO URL LIMITATIONS
        //TODO: I JUST LEARNED THE HARD WAY THAT YOU CAN'T HAVE SPACES
        //TODO: I IMAGINE THERE ARE OTHER SPECIAL CHARS THAT WILL CAUSE ISSUES
        if (name.length() > 50)
            throw new IllegalArgumentException();
        this.username = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email.length() > 50)
            throw new IllegalArgumentException();
        this.email = email;
    }

    public void deleteAvatar() {
        avatar = new NullImage();
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Boolean getShouldDownloadImages() {
        return shouldDownloadImages;
    }

    public void setShouldDownloadImages(Boolean shouldDownloadImages) {
        this.shouldDownloadImages = shouldDownloadImages;
    }

    public void commit(UserDatabase userDB) {
        try {
            userDB.getElastic().updateDocument("user", username, this, "profile");
        } catch (IOException e) {
            //TODO Save Locally
            e.printStackTrace();
        }
    }
}
