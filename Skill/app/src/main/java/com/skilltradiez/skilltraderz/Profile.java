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

/**
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
 */

public class Profile extends Stringeable {
    private String username, email, location;
    private Boolean shouldDownloadImages;
    private int successfulTrades;

    /**
     * The constructor for a profile will take in a string as a parameter and assign it to be the
     *    username of the Profile, by default will set the ability to download images to be true and
     *    will set successfulTrades to zero.
     * @param username String input
     */
    Profile(String username) {
        setUsername(username);
        setShouldDownloadImages(true);
        successfulTrades = 0;
    }

    /**
     * Basic getter method to obtain the location variable from a Profile Object.
     * @return String of the Location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Basic setter method to set the location variable within this Profile Object to the passed
     *    in parameter.
     * @param location String input
     */
    public void setLocation(String location) {
        if (location.length() > 50)
            throw new IllegalArgumentException();
        this.location = location;
        notifyDB();
    }

    /**
     * Basic getter method that gets the username from the particular Profile Object.
     * @return String of the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Basic setter method that sets the username for the Profile Object
     * @param name String input
     * @throws IllegalArgumentException
     */
    private void setUsername(String name) throws IllegalArgumentException {
        if (name.length() > 50)
            throw new IllegalArgumentException();
        this.username = name;
        notifyDB();
    }

    /**
     * Basic getter method that gets the String of the email from the particular Profile Object.
     * @return String of the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Basic setter method that sets the email of the profile Object to the String passed as
     * a parameter to this method.
     * @param email String input.
     * @throws IllegalArgumentException.
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if (email.length() > 50)
            throw new IllegalArgumentException();
        this.email = email;
        notifyDB();
    }

    /**
     * Basic getter from the user Profile's setting if images should be downloaded or not.
     * True = should be able to download images.
     * False = should NOT be able to download images.
     * @return Boolean. True/False.
     */
    public Boolean getShouldDownloadImages() {
        return shouldDownloadImages;
    }

    /**
     * Basic setter for if the user's profile should allow image downloading or not.
     * @param shouldDownloadImages Boolean flag.
     */
    public void setShouldDownloadImages(Boolean shouldDownloadImages) {
        this.shouldDownloadImages = shouldDownloadImages;
        notifyDB();
    }

    /**
     * When evoked, will commit the Profile Object to the database.
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
    public boolean commit(UserDatabase userDB) {
        try {
            userDB.getElastic().updateDocument("user", username, this, "profile");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Basic getter method that will return the username of the Profile Object.
     * @return String of the username.
     */
    public String getName() {
        return getUsername();
    }

    /**
     * When evoked, will look at the database and see if the current user and the profile
     * being viewed are friends, not friends, or the user themselves. Returns string values of
     * strict definition to allow all three options.
     *
     * @return String of either "You", "Friend", or "Non-Friend" for identification.
     */
    public String getCategory(){
        MasterController masterController = new MasterController();
        if (masterController.getCurrentUser().getProfile().getName().equals(getUsername())) {
            return "You";
        } else if (masterController.hasFriend(masterController.getUserByName(getUsername()))) {
            return "Friend";
        }
        return "Non-Friend";
    }

    /**
     * Basic getter method that returns a String of the Profile Object's description.
     * @return String of the description.
     */
    public String getDescription() {
        return getLocation();
    }

    /**
     * Basic getter method that returns a new default image. (Default is NullImage).
     * @return Image Object.
     */
    public Image getImage() {
        return new NullImage();
    }

    /**
     * Basic getter method that returns the number of successful trades this profile has had.
     * @return Integer
     */
    public int getTop() {
        return getSuccessfulTrades();
    }

    /**
     * Basic getter method that returns the number of successful trades this profile has had.
     * @return Integer
     */
    public int getSuccessfulTrades() {
        return successfulTrades;
    }

    /**
     * This method, when invoked, will add 1 to the counter of successful trades that this
     * particular Profile Object has had.
     */
    public void tradeSuccess() {
        successfulTrades++;
        notifyDB();
    }
}
