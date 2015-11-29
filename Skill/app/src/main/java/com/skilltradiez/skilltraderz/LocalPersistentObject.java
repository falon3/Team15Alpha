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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * When we want to have something saved locally this is going to be the class instantiated
 * through the application. This is going to be the entire process of making the local save
 * within the actual device of the user and then things will be alright afterwards.
 *
 * This class supports the attributes for all potential things that are needed for a local
 * persistent object such as the users, the current user, the skills, the trades and the
 * notifications associated with this all.
 *
 * The methods are truly just traditional getters and setters in typical java fashion.
 */

public class LocalPersistentObject {
    /**Class Variables:
     * 1: currentUser: Represents the current user of the application. Includes Profile, Inventory
     *      and the current user's FriendList.
     * 2: users: A Collection of User Objects that includes Friends, Profiles and Inventories.
     * 3: skillz: A Collection of Skill Objects. Includes the Skill Objects that are in the
     *      current user's inventory as well as those in the current user's friend's inventories.
     * 4: trades: A collection of Trades that represent the current user's trade history.
     * 5: notifications: A List of Notification Objects that represent any sort of changes that
     *      the application should keep track of. Eg: Skill changes, Trade changes.
     * 6: changedFriends: A Collection os FriendsLists that keeps track of changes to these
     *      FriendsList objects.
     * 7: changedProfile: A collection of Profile objects that maintains track of changes to the
     *      Profile Objects.
     * 8: changedInventory: A collection of Inventory objects that keeps track of changes made to
     *      the Inventory objects through the application.
     * 9: changedSkillz: A collection of Skill objects that will keep track of any changes to the
     *      Skill objects across the application.
     * 10: changedTradesList: A collection of TradeList objects that will maintain what TradeList
     *      objects have been modified throughout the application. Changes to TradeLists. Not Trades
     * 11: changedTrades: Similar to above, but for individual Trade objects and not TradeLists.
     */
    private User currentUser;
    private Collection<User> users;
    private Collection<Skill> skillz;
    private Collection<Trade> trades;
    //private List<Notification> notifications; // Skill changes, Trade Changes
    private Collection<FriendsList> changedFriends;
    private Collection<Profile> changedProfile;
    private Collection<Inventory> changedInventory;
    private Collection<Skill> changedSkillz;
    private Collection<TradeList> changedTradesList;
    private Collection<Trade> changedTrades;

    /**
     * This constructor is heavy on initializations, creates a large amount of new objects and
     * associates these objects with itself by assigning it's variables to these new objects.
     */
    LocalPersistentObject() {
        currentUser = null;
        users = new ArrayList<User>();
        skillz = new ArrayList<Skill>();
        trades = new ArrayList<Trade>();
        //notifications = new ArrayList<Notification>();
        this.changedFriends = new ArrayList<FriendsList>();
        this.changedProfile = new ArrayList<Profile>();
        this.changedInventory = new ArrayList<Inventory>();
        this.changedSkillz =  new ArrayList<Skill>();
        this.changedTradesList = new ArrayList<TradeList>();
        this.changedTrades = new ArrayList<Trade>();
    }

    /**
     * Classical getter method where we return to the caller the User Object that represents the
     * current user of the applciation.
     *
     * @return User Object.
     */
    public User getCurrentUser () { return currentUser;}

    /**
     * Classical setter method where we assign the currentUser of this Object to be the current
     * user of the application. I know it probably sounds bogus, but this is necessary for
     * complete and proper functioning.
     *
     * @param currentUser User Object.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Classical getter method that will return this Object's currently assigned Collection of
     * User Objects to the caller.
     *
     * @return Collection of User Objects.
     */
    public Collection<User> getUsers() {
        return users;
    }

    /**
     * Classical setter method that takes as a parameter a Collection of User Objects and
     * will assign this Object's users variable to ths passed in parameter.
     *
     * @param users Collection of User Objects.
     */
    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    /**
     * Classical getter method where we return this Object's currently assigned collection of
     * Skill Objects.
     *
     * @return Collection of Skill Objects.
     */
    public Collection<Skill> getSkillz() {
        return skillz;
    }

    /**
     * Classical setter method where we set this Object's skillz variable to the Collection of
     * Skill Objects passed into this method.
     *
     * @param skillz Collection of Skill Objects.
     */
    public void setSkillz(Collection<Skill> skillz) {
        this.skillz = skillz;
    }

    /**
     * Classical getter method where we return the Collection of Trade Objects that this Object
     * has.
     *
     * @return Collection of Trade Objects.
     */
    public Collection<Trade> getTrades() {
        return trades;
    }

    /**
     * Classical setter method that assigns this Object's trades variable to the Collection
     * of Trade Objects that are passed into this method.
     *
     * @param trades Collection of Trade Objects.
     */
    public void setTrades(Collection<Trade> trades) {
        this.trades = trades;
    }

    /**
     * Classical getter method to obtain the Notification Objects for this application.
     *
     * Goes through each of the changed variables (eg: changedFriends) and then adds them
     *    to the notifications through the Notification class's addAll method.
     * Returns this as a Collection of Notification Objects.
     * @return Collection of Notification Objects.
     */
    public Collection<Notification> getNotifications() {
        Collection<Notification> notifications = new ArrayList<Notification>();
        notifications.addAll(changedFriends);
        notifications.addAll(changedProfile);
        notifications.addAll(changedInventory);
        notifications.addAll(changedSkillz);
        notifications.addAll(changedTradesList);
        notifications.addAll(changedTrades);
        return notifications;
    }

    /**
     * Given a ChangeList of notifications, will then assign this object's variables to be
     * the variables within the passed in ChangeList Object's variables.
     *
     * This method goes one-by-one through the ChangeList Object's variables and assigns this
     * LocalPersistentObject's variables to be those variables.
     *
     * @param notifications ChangeList Object.
     */
    public void setNotifications(ChangeList notifications) {
        this.changedFriends = notifications.getFriendsList();
        this.changedProfile = notifications.getProfiles();
        this.changedInventory = notifications.getInventory();
        this.changedSkillz =  notifications.getSkillz();
        this.changedTradesList = notifications.getTradesList();
        this.changedTrades = notifications.getTrades();
        //this.notifications = notifications;
    }
}
