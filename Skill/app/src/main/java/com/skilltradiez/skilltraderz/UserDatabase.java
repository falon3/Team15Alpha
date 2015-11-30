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

    public void setHttpClient(HTTPClient client){
        elastic = new Elastic("http://cmput301.softwareprocess.es:8080/cmput301f15t15/", client);
    }



    /** Model here only has the simple getter/setter methods that allow the controller to function
     * without some insane level of complexity.
     *
     * All of those huge functions from before were just outright modified and put into the
     * controller itself. Those don't belong in a true model class following MVC.
     */
    /**
     * This is unfortunately necessary
     */
    public void setCurrentUserToNull(){ currentUser = null; }

    public Set<Skill> getSkillz(){ return skillz; }

    public ChangeList getChangeList() { return toBePushed; }

    public Elastic getElastic() { return elastic; }

    public Local getLocal() { return local; }

    public User getCurrentUser() { return currentUser; }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Skill> getSkills() {
        return skillz;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        getChangeList().add(currentUser.getFriendsList());
        getChangeList().add(currentUser.getTradeList());
        getChangeList().add(currentUser.getProfile());
        getChangeList().add(currentUser.getInventory());
    }

    public Set<Image> getImagez() {
        return imagez;
    }

    public void setImagez(Set<Image> imagez) {
        this.imagez = imagez;
    }
}
