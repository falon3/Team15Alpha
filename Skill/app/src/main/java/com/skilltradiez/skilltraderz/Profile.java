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

import java.io.IOException;

/** ~~DESCRIPTION:
 * A profile is the visible representation of any particular user of our application, this is the
 * particular method in where the other users of the application will learn more about this user
 * and will decide if they wish to pursue a trade or to deny a trade or change/modify a trade with
 * the other user. Essentially this is the HUB for each paritcular user.
 *
 * Impelentation wise this class has a large collection of various private variables that are
 * bunched together in a fashion that allows quick, easy and effecient access for the users
 * of this application! Essentially this is going to be the core hub of functionalities that
 * are related to the user's profile. We will allow the values within the profile to be modified,
 * deleted, updated, changed and represent the user mroe appropriately upon the fly!
 *
 * ~~ACCESS:
 * This class is a public class that will implement the notification interface, which the
 * notification interface is going to specify the requirements of various functions that are
 * within this application (detailed below).
 *
 * ~~CONSTRUCTORS:
 * This class is constructed by only one particular constructor (NO METHOD OVERLOARDING!)
 * In this instance when we are suppplied by the parameters of the string of the username
 * of the profile and then the string of the password for the user then we will create a brand
 * new shiny profile object! Glorious!
 *
 * ATTRIBUTES/METHODS:
 * In this object we specify a lot of attributes that are related to the core of what the user
 * wants to have represented about his/herself and so we have the following:
 *
 * 1: USERNAME.
 *     This is going to be the name of the user who is going to be presented by this particular
 *     profile! In this instance we FORCE the profile to have a particular username associated
 *     with the particular profile! In this case the username will be granted the status of being
 *     the single primary key that is used to identify the user (obviously following the rest
 *     of our code!). So this is manditory.
 *
 *     WE PROVIDE THE METHODS TO:
 *         None! THIS attribute (username) is obtained ONCE and ONLY ONCE and this is when we
 *         call the constructor to make a new instance of the profile class!
 *
 * 2: NICKNAME:
 *     This string value is going to be the potential representation that the user MAY OR MAY NOT
 *     opt to represent themselves with. This is NOT going to be ambiguous with the username.
 *     The username is THE key and is the primary identifier for any particular user of this
 *     application! This nickname is a relatively kind/soft method to allow the user to rename
 *     themselves in a fashion that they deem appropriate. We do not censor this, so we may
 *     have some... potentially hilarious names result from this chaos.
 *
 *
 *     WE PROVIDE THE METHODS TO:
 *         -Get the nickname of the particular user's profile      --getNickname
 *         -Set the nickname o the particular user's proile        --setNickname
 *
 * 3: EMAIL:
 *     This string value is the representation of how the other users will be able to contact the
 *     user through their profile page. Initially set by the CONSTRUCTOR that recieves the user's
 *     email during the process. The constructor calls upon the method of setEmail and then
 *     uses the string supplied by the user to set an e-mail for the user.
 *
 *     WE PROVIDE THE METHODS TO:
 *         -Get the email of the particular user's profile       -getEmail
 *         -Set the email of the user's profile (change email)   -setEmail
 *
 * 4: PASSWORD:
 *     This is the paramount and absolutely essential part of the user that we...
 *     are.... told... to... NOT.... DO!!!!!!!!!!!!!!!!
 *     PLS
 *
 * 5: SHOULDDOWNLOADIMAGES:
 *     This is going to be the private boolean value that the user has access to through
 *     their profile in which the user will allow the downloading of images from their profile
 *     (or.... not, although in reality in the world of screenshots goodluck honey!).
 *
 *     WE PROVIDE THE METHDOS TO:
 *        -get shouldDownloadImages                   --getShouldDownloadImages
 *        -set shouldDownload Images                  --setShouldDownloadImages
 *
 *  6: AVATAR:
 *      This is going to be one layer of the peronalization of the profile that is dear to many
 *      users of this application. This is the image that will represent the user to all other
 *      users of this application! Through the use of this image we will grant the user the
 *      ability to represent an image that will ideally capture their self envisioned self
 *      where the image is totally "them". And fabulous, cannot have a good image without
 *      it being downright fabulous.
 *
 *      WE PROVIDE THE METHODS TO:
 *          -get the avatar, to represent it          --getAvatar
 *          -set the avatar to something new          --setAvatar
 *          -delete the avatar (going to default)     --deteleAvatar
 *
 *
 *  METHODS NOT RELATED TO ATTRIBUTES:
 *
 *  1: COMMIT:
 *      This method is going to be the method that is called out by extrnal classes/parts of this
 *      application in order to fully "commit" the profile information to the database/local
 *      memory located within the device itself! Think of it like a "save button" that is linked
 *      to all the data within this class. It is glorious, it is beautiful. Love it.
 *
 */

public class Profile extends Stringeable {
    private String username, email, location;
    private Boolean shouldDownloadImages = true;
    private int avatar;
    private Rating rating;

    Profile(String username) {
        setUsername(username);
        rating = new Rating("user", username);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location.length() > 50)
            throw new IllegalArgumentException();
        this.location = location;
        notifyDB();
    }

    public String getUsername() {
        return this.username;
    }

    private void setUsername(String name) throws IllegalArgumentException {
        if (name.length() > 50)
            throw new IllegalArgumentException();
        this.username = name;
        notifyDB();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email.length() > 50)
            throw new IllegalArgumentException();
        this.email = email;
        notifyDB();
    }

    public void deleteAvatar() {
        avatar = new NullImage().getInt();
        notifyDB();
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
        notifyDB();
    }

    public Boolean getShouldDownloadImages() {
        return shouldDownloadImages;
    }

    public void setShouldDownloadImages(Boolean shouldDownloadImages) {
        this.shouldDownloadImages = shouldDownloadImages;
        notifyDB();
    }

    public int getRating() {
        return rating.getRating();
    }

    public void addRating(String username, int rate) {
        rating.changeRating(username, rate);
        notifyDB();
    }

    public boolean commit(UserDatabase userDB) {
        try {
            userDB.getElastic().updateDocument("user", username, this, "profile");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getName() {
        return getUsername();
    }

    public String getDescription() {
        return getLocation();
    }

    public int getImage() {
        //TODO Decide how images work
        return getAvatar();
    }
}
