package com.skilltradiez.skilltraderz;
/**~~DESCRIPTION:
 * When we want to have something saved locally this is going to be the class instantiated
 * through the application. This is going to be the entire process of making the local save
 * within the actual device of the user and then things will be alright afterwards.
 *
 * This class supports the attributes for all potential things that are needed for a local
 * persistent object such as the users, the current user, the skills, the trades and the
 * notifications associated with this all.
 *
 * The methods are truly just traditional getters and setters in typical java fashion.
 *
 * ~~ACCESS:
 * This is a public access class meaning that objects of this class can be (1) instantiated
 * anywhere throughout the program (2) they can, once instantiated, have the methods
 * of this class called anywhere throughout the application. This allows the entire application
 * full access to this class.
 *
 * ~~CONSTRUCTOR:
 * This is going to only have a single type of constructor (no overloading here) where the object
 * is created and has no parameters assocaited with itself where the constructor is going
 * to create a series of brand new ArrayList lists and then we wil have this object interact
 * through the methods to modify these into actually reasonably functional and logical
 * things that are good and awesome.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: CURRENTUSER:
 *     It is core to the functionality to keep track of just who the current user of this
 *     application is, this is going to be assigned to this object through settters.
 *
 *     METHODS PROVIDED:
 *     -getter (traditional)  --getCurrentUser
 *     -setter (tradtiional)  --setCurrentUser
 *
 * 2: USERS:
 *     This is going to be the attribute where we maintain a list of all of the user objects
 *     that are relevant to this application and we will always have this avalaible.
 *
 *     METHODS PROVIDED:
 *     -getter (traditional)  --getUsers
 *     -setter (tradtiional)  --setUsers
 * 3: SKILLZ:
 *     This is going to be all of the skillz that are present within the inventories that we
 *     would love to make a cache of within the local device.
 *
 * *    METHODS PROVIDED:
 *     -getter (traditional)  --getSkillz
 *     -setter (tradtiional)  --setSkillz
 * 4: TRADES:
 *     This is going to be the cached information on the trades that the user is currently
 *     somehow assocaited with and will be maintained through this object and stored locally
 *     for permanent potentail accesses.
 *
 *     METHODS PROVIDED:
 *     -getter (traditional)  --getTrades
 *     -setter (tradtiional)  --setTrades
 *
 * 5: NOTIFICATIONS:
 *     This is going to be the list that maintains ALL the notifications of the application.
 *     If something happens within the applicaiton that we find relevant then we will need to
 *     activate this list of notifications for that user and so we're going to just make damn
 *     sure that we save all of these notification objects within the user's locally
 *     stored information.
 *
 *     METHODS PROVIDED:
 *     -getter (traditional)  --getNotifications
 *     -setter (tradtiional)  --setNotifications
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Falon3 on 2015-11-04.
 */
public class LocalPersistentObject {
    private User currentUser; // Profile, Inventory, Friendslist
    private Collection<User> users; // Friends Profiles, Inventories
    private Collection<Skill> skillz; // Skillz that are found in the User and Friend's Inventories
    private Collection<Trade> trades; // User's Trade History
    private List<Notification> notifications; // Skill changes, Trade Changes

    LocalPersistentObject() {
        currentUser = null;
        users = new ArrayList<User>();
        skillz = new ArrayList<Skill>();
        trades = new ArrayList<Trade>();
        notifications = new ArrayList<Notification>();
    }

    public User getCurrentUser () { return currentUser;}

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<Skill> getSkillz() {
        return skillz;
    }

    public void setSkillz(Collection<Skill> skillz) {
        this.skillz = skillz;
    }

    public Collection<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Collection<Trade> trades) {
        this.trades = trades;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
