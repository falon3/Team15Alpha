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

import java.util.HashSet;
import java.util.Set;

/**
 * A user database is going to be the object that is going to be a massive force in this
 * application. This is the massive heaving beast of a class that will be burdened with a great
 * deal of functionality associated directly with the database itself. In order for this to
 * properly function we will be making use of the popular database ElasticSearch.
 *
 * Now suppose that we have a bunch of users, we want to be able to successfully manage
 * all of the user's individually, all the users within a list like format, and then a list of the
 * modifications that we want to successfully commit to the databae. Databae, we love it.
 *
 * This class will have the ability to pull various things from the database in which case
 * the application will pull relevant information that the applicaiton desires at that current
 * point of time.
 */

public class UserDatabase {
    private User currentUser;
    private Set<User> users;
    private Set<Trade> trades;
    private Set<Skill> skillz;
    private Set<Image> imagez;
    private ChangeList toBePushed;
    private Elastic elastic;
    private Local local;

    /**
     * This should only ever be called once in the application, this constructs THE database
     * for the entire application. Instntiates a new set of hashes for users, trades, skills,
     * images and ChangeList.
     *
     * We will also create a newHTTPClient and invoke the setHttpClient method with this, giving
     * this database a particular HTTPClient that it will be utilizing extensively through the
     * application process.
     *
     * Another part of this constructor is that we will obtain all of the LocalPersistentObject
     * data (if it is there) and then we will shuttle this into the database.
     *
     * Finally we close with a push to the changelist, updating the database.
     */
    UserDatabase() {
        users = new HashSet<User>();
        trades = new HashSet<Trade>();
        skillz = new HashSet<Skill>();
        imagez = new HashSet<Image>();
        toBePushed = new ChangeList();

        // Persistence API
        // Via ElasticSearch(Internet)
        setHttpClient(new HTTPClient());
        // Via SD Card(Local)
        local = new Local();

        LocalPersistentObject lpo = local.getLocalData();
        if (lpo != null) {
            if (lpo.getCurrentUser() != null) {
                setCurrentUser(lpo.getCurrentUser());
            } else {
                currentUser = null;
            }
            users.addAll(lpo.getUsers());
            skillz.addAll(lpo.getSkillz());
            trades.addAll(lpo.getTrades());
            //TODO images & local (maybe? maybe this isn't even required!);
            getChangeList().getNotifications().addAll(lpo.getNotifications());
        } else {
            currentUser = null;
        }
        getChangeList().push(this);
    }

    /**
     * Sets the elastic variable of the UserDatabase Object to a new Elastic Object based upon
     * the client parameter passed into this method.
     * @param client HTTPClient Object
     */
    public void setHttpClient(HTTPClient client){
        elastic = new Elastic("http://cmput301.softwareprocess.es:8080/cmput301f15t15/", client);
    }

    /**
     * Basic setter method that sets the current user value to null.
     */
    public void setCurrentUserToNull(){ currentUser = null; }

    /**
     * Basic getter method that returns the Set of Skill Objects from the database.
     * @return Set of Skill Objects.
     */
    public Set<Skill> getSkillz(){ return skillz; }

    /**
     * Basic getter method that returns the ChangeList Object from the database.
     * @return ChangeList Object.
     */
    public ChangeList getChangeList() { return toBePushed; }

    /**
     * Basic getter method that returns the Elastic Object from the database.
     * @return Elastic Object.
     */
    public Elastic getElastic() { return elastic; }

    /**
     * Basic getter method that returns the Local Object from the database.
     * @return Local Object.
     */
    public Local getLocal() { return local; }

    /**
     * Basic getter method that returns current user User Oject from the database.
     * @return User Object.
     */
    public User getCurrentUser() { return currentUser; }

    /**
     * Basic getter method that returns the complete set of User Objects from the database.
     * @return Set of User Objects.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Basic getter method that returns the complete set of Skill Objects from the database.
     * @return Set of Skill Objects.
     */
    public Set<Skill> getSkills() {
        return skillz;
    }

    /**
     * Basic getter method that returns the complete set of Trade Objects from the database.
     * @return Set of Trade Objects.
     */
    public Set<Trade> getTrades() {
        return trades;
    }

    /**
     * Basic getter method that returns the complete set of Image Objects from the database.
     * @return Set of Image Objects.
     */
    public Set<Image> getImagez() {
        return imagez;
    }

    /**
     * Sets the UserDatabase imagez variable to the supplied Set of Image Objects supplied through
     * the method's parameter.
     * @param imagez Set of Image Objects
     */
    public void setImagez(Set<Image> imagez) {
        this.imagez = imagez;
    }

    /**
     * Sets the current user of the application across various aspects of the ChangeList Object
     * assocaited with the database.
     * @param currentUser User Object.
     */
    public void setCurrentUser(User currentUser) {
        //TODO THIS IS BAD!!!!!!!!!!!
        //THIS INVALIDATES EVERY USER OBJECT EVERYWHERE!
        //NEED TO COPY IN THE DATA INSTEAD.
        this.currentUser = currentUser;

        getChangeList().add(currentUser.getFriendsList());
        getChangeList().add(currentUser.getTradeList());
        getChangeList().add(currentUser.getProfile());
        getChangeList().add(currentUser.getInventory());
    }


}
