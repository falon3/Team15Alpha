package com.skilltradiez.skilltraderz;
/**~~DESCRPTION:
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
 *
 * ~~ACCESS:
 * This class is a public class, meaning that at any point in time during the application with
 * any class in the application we can instnatiate a new copy of this object, and that it's methods
 * are fully accessible throughout the entire applicaiton at any given point in time!
 *
 * ~~CONSTRUCTORS:
 * This class is going to be instantiated in which case we have the default constructor.
 * This takes in nothing, and just makes a user database. Have fun!
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: CURRENTUSER:
 *     This is going to be the attribute that is going to have the identifier for the current
 *     user using this application!
 *
 *     WE PROVIDE THE METHODS TO:
 *
 *
 * 2: USERS:
 *     This is going to be the full list of all of the user's that is pulled from the database.
 *     This list is going to likely be comprehensive, however keep in mind that we have a
 *     large variety of methods that can actually allow the current user/application of the
 *     current online user to choose what sort of list of the entire range of users that the
 *     current user actually desires to see at any given point in time.
 *
 *
 *      WE PROVIDE THE METHODS TO:
 *          -Pull users, granting us the entire list of users       --pullUsers
 *          -Get the user by their username                         --getAccountByUsername
 *          -Get the users by accordance to their user ID           --getAccountByUserID
 *          -Get the user's by how their nickname is in relations!  --getAccountByNickname
 *
 *
 *  3: TOBEPUSHED:
 *      This is going to be a large list of all of the pending information that is related to the
 *      users in which we have NOT YET AT ALL updated the database yet with the
 *      information of the users that we currently have stored within this very interesting
 *      list of potential users and then we will actually have the thingies update once we make an
 *      update request to the database through the method save()
 *
 * ~~MISC METHODS:
 *
 * 1: CREATEUSER:
 *     This method is going to be the method that will take into itself the parameters
 *     of the user's string of their username, the string of the user's pasword and then will create
 *     a brand new user in the database associated with the username and apssword that is given
 *     to the user. And then we forever have this! YAY! :)
 *
 * 2: LOGIN:
 *     Suppose we already have a database of useres (wow, what a coincidence!!!!) and now we want
 *     to actually be able to confirm if the user that tried to login to the application was authentic or not...
 *     now I realize strongly that we don't need to supply a username or a password to the database to
 *     confirm that the user is authentic....
 *     we have no cryptography systems or anything for this application and so we actually are
 *     just going off of the idea and fact that the assignment... will just let us....
 *     have any random user login to the application using a username and then will
 *     just log that user into the application and then
 *     we wil grant them full acess to that profile. Regardless... we have a password that is
 *     associated with each user...
 *
 *     WE PROVIDE THE METHODS TO:
 *         -log into the application           --login
 *
 *
 *
 * 3: SAVE:
 *     Where we have the application itself actually involve itself with the database.
 *     When this method is actually called by the user or the class we will actually take
 *     the entire funnel of information that the applicaiton currently
 *     has locked within itself and then the database will be updated with the infromation.
 *     This will be the master function that is called in order to call the database and then fill
 *     the database with all the information.
 *
 *
 *      WE PROVIDE THE METHODS TO:
 *          -save the information to the databae --save
 *
 *
 *
 *
 *
 */


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

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private User currentUser;
    private Set<User> users;
    private List<Trade> trades;
    private Set<Skill> skillz;
    private ChangeList toBePushed;
    private Elastic elastic;
    private Local local;

    UserDatabase() {
        users = new HashSet<User>();
        trades = new ArrayList<Trade>();
        skillz = new HashSet<Skill>();
        toBePushed = new ChangeList();

        // Persistence API
        // Via ElasticSearch(Internet)
        elastic = new Elastic("http://cmput301.softwareprocess.es:8080/cmput301f15t15/");
        // Via SD Card(Local)
        local = new Local();

        LocalPersistentObject lpo = local.getLocalData();
        if (lpo != null) {
            currentUser = lpo.getCurrentUser();
            users.addAll(lpo.getUsers());
            skillz.addAll(lpo.getSkillz());
            trades.addAll(lpo.getTrades());
            getChangeList().getNotifications().addAll(lpo.getNotifications());
            Log.d("GGGGGGGGEEEEEEEEEETTTT", "HEEEEEERRRRRRRR");
        } else {
            currentUser = null;
        }
    }

    public User createUser(String username) throws UserAlreadyExistsException {
        if (getAccountByUsername(username) != null)
            throw new UserAlreadyExistsException();

        User u = new User(username);
        users.add(u);
        // You wouldn't be creating a user if you already had one
        setCurrentUser(u);
        try {
            elastic.addDocument("user", username, u);
        } catch (IOException e) {
            // No internet, no registration
            throw new RuntimeException();
        }
        return u;
    }

    public User login(String username) {
        User u = getAccountByUsername(username);
        setCurrentUser(u);
        return u;
    }

    public void deleteAllData() {
        try {
            elastic.deleteDocument("example", "");
            elastic.deleteDocument("user", "");
            elastic.deleteDocument("skill", "");
            elastic.deleteDocument("trade", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocumentUser(User user) {
        deleteDocumentUser(user.getUserID().toString());
    }

    public void deleteDocumentSkill(Skill skill) {
        deleteDocumentSkill(skill.getSkillID().toString());
    }

    public void deleteDocumentTrade(Trade trade) {
        deleteDocumentTrade(trade.getTradeID().toString());
    }

    public void deleteDocumentUser(String userID) {
        try {
            elastic.deleteDocument("user", userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocumentSkill(String skillID) {
        try {
            elastic.deleteDocument("skill", skillID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocumentTrade(String tradeID) {
        try {
            elastic.deleteDocument("trade", tradeID);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Downloads all online data into a local cache
     * TODO: save must be done before this or we might lose data
     */
    public void refresh() {
        try {
            List<User> onlineUsers = elastic.getAllUsers();
            for (User u : onlineUsers) {
                if (u.equals(currentUser)) setCurrentUser(u);
                users.remove(u);
                users.add(u);
            }
            //TODO CATCH IOExceptions
            List<Skill> skills = elastic.getAllSkills();
            for (Skill s : skills) {
                skillz.remove(s);
                skillz.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO saving should merge, not overwrite.
     */
    public void save() {
        toBePushed.push(this);
        // TODO: Saves locally and pushes changes if connected to the internet
        local.saveToFile(currentUser, users, skillz, trades, toBePushed.getNotifications());
    }

    public ChangeList getChangeList() {
        return toBePushed;
    }

    /*
     * The internet API
     */
    public Elastic getElastic() {
        return elastic;
    }

    /*
     * The Local API
     */
    public Local getLocal() {
        return local;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getAccountByUsername(String username) {
        for (User u : users)
            if (u.getProfile().getUsername().equals(username))
                return u;
        return getOnlineAccountByUsername(username);
    }

    private User getOnlineAccountByUsername(String username) {
        //TODO Maybe this should throw an exception instead of returning null.
        User u = null;
        try {
            //System.out.println(username);
            u = elastic.getDocumentUser(username);
            if (u != null) users.add(u);
        } catch (IOException e) {
        }
        return u;
    }

    public User getAccountByUserID(ID id) {
        for (User u : users)
            if (u.getUserID().equals(id))
                return u;
        return null;
    }

    public Trade getTradeByID(ID id) {
        for (Trade t : trades)
            if (t.getTradeID().equals(id))
                return t;
        return getOnlineTradeByID(id);
    }

    private Trade getOnlineTradeByID(ID id) {
        Trade t = null;
        try {
            t = elastic.getDocumentTrade(id.toString());
            if (t != null)
                trades.add(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public Skill getSkillByID(ID id) {
        for (Skill s : skillz)
            if (s.getSkillID().equals(id))
                return s;
        return getOnlineSkillByID(id);
    }

    private Skill getOnlineSkillByID(ID id) {
        Skill s = null;
        try {
            s = elastic.getDocumentSkill(id.toString());
            if (s != null) skillz.add(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public void addSkill(Skill s) {
        skillz.add(s);
        // New Skill
        getChangeList().add(s);
        try {
            //TODO: Seems to be a problem for no apparent reason (Maybe the Network in UI Issue?)
            getElastic().addDocument("skill", s.getSkillID().toString(), s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTrade(Trade t) {
        trades.add(t);
        // New Trade
        getChangeList().add(t);
        try {
            getElastic().addDocument("trade", t.getTradeID().toString(), t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        getChangeList().add(currentUser.getFriendsList());
        getChangeList().add(currentUser.getTradeList());
        getChangeList().add(currentUser.getProfile());
        getChangeList().add(currentUser.getInventory());
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Skill> getSkills() {
        return skillz;
    }
}
