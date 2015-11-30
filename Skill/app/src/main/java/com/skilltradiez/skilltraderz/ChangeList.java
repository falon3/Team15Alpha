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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * What is life without change?
 * I don't know, I am not a philosopher. Please don't ask me. Please... :(
 *
 * So in our application we make use of a lot of models, viewers and controllers (MVC is great!)
 * and so we need to have some way of informing all of these various parts about just WHAT on
 * Earth is going on in the rest of the application!
 *
 * (or Mars, if this application ever gets to Mars-- ooh or I might set my sights higher
 * and hope for Pluto or mayb- *cough cough*)
 *
 * So we create, in very common MVC fashion, a big beautiful w-...list.... of notifications. This
 * list is going to be populated with a large number of Notification objects that are described
 * in the notification class (Please don't make me type more, mercy!).
 *
 * When something changes in the application, who are you gonna call? (No, not ghostbusters)
 * The ChangeList!
 *
 * So we give the application to populate this big beautiful list of notifications with a ton
 * of different notifications that are tied to various parts of the application. Sounds awesome
 * right?! Well it gets even better! When the notification list is fully populated we are going
 * to let the user actually have a method that lets them push ALL of this to a particular user's
 * database! Now that is glorious, and majestic.
 */

class ChangeList {
    /** Class Variables:
     * Notifications are: FriendsList, Profile, Inventory, Skill, TradeList, Trade
     *
     * 1: notifications, the overall list if all notifications.
     * 2: newNotifications, the list of only new notifications that haven't been commited to the DB.
     * 3: oldNotifications, the list of only old notifications that have been commited to the DB.
     * 4: lock, a boolean involved in whether we have the method use the overall notifications list
     *    or whether we have it in an old or new notifications list.
     */
    private Set<Notification> notifications;
    private Set<Notification> newNotifications;
    private Set<Notification> oldNotifications;
    private boolean lock;

    /**
     * Creates the ChangeList object, assigning three ArrayLists of notifications corresponding to:
     * 1: notifications, the overall list if all notifications.
     * 2: newNotifications, the list of only new notifications that haven't been commited to the DB.
     * 3: oldNotifications, the list of only old notifications that have been commited to the DB.
     *
     * Params: None.
     * Return: Creates a ChangeList object.
     */
    ChangeList() {
        notifications = new HashSet<Notification>();
        newNotifications = new HashSet<Notification>();
        oldNotifications = new HashSet<Notification>();
    }


    /** Methods **/
    /**
     * Given a notification object, will add this notification object to the notifications list.
     * @param newNote Notification Object.
     */
    public void add(Notification newNote) {
        if (lock) newNotifications.add(newNote);
        else notifications.add(newNote);
    }

    /**
     * Given a notification object, will remove that object from the notifications list.
     * @param note Notification Object.
     */
    public void remove(Notification note) {
        if (lock) oldNotifications.add(note);
        else notifications.remove(note);
    }

    /**
     * Returns the list of notifications.
     * @return List of Notifications.
     */
    public Set<Notification> getNotifications() {
        return notifications;
    }

    public List<Notification> getNotificationsAsList() {
        List<Notification> notifications = new ArrayList<Notification>();
        notifications.addAll(this.notifications);
        return notifications;
    }

    /**
     * Returns the list of friends, based upon the friends being an instance of any notification
     * present in the notifications list.
     * @return List of FriendsLists. (A lists of lists! How ... obfusticated.)
     */
    public List<FriendsList> getFriendsList() {
        List<FriendsList> friendsLists = new ArrayList<FriendsList>();
        for (Notification note:notifications)
            if (note instanceof FriendsList)
                friendsLists.add((FriendsList)note);
        return friendsLists;
    }

    /**
     * Returns the list of profiles, based upon the profiles being an instance of any notification
     * present in the notifications list.
     * @return List of profiles.
     */
    public List<Profile> getProfiles() {
        List<Profile> profiles = new ArrayList<Profile>();
        for (Notification note:notifications)
            if (note instanceof Profile)
                profiles.add((Profile)note);
        return profiles;
    }

    /**
     * Returns the list of inventories, based upon the inventories being an instance of any
     * notification present in the notifications list.
     * @return List of Inventories.
     */
    public List<Inventory> getInventory() {
        List<Inventory> inventories = new ArrayList<Inventory>();
        for (Notification note:notifications)
            if (note instanceof Inventory)
                inventories.add((Inventory)note);
        return inventories;
    }

    /**
     * Returns the list of Skillz, based upon the Skillz being an instance of any notification
     * present in the notifications list.
     * @return List of Skills.
     */
    public List<Skill> getSkillz() {
        List<Skill> skillz = new ArrayList<Skill>();
        for (Notification note:notifications)
            if (note instanceof Skill)
                skillz.add((Skill) note);
        return skillz;
    }

    /**
     * Returns the list of trades LISTS, based upon the trades being an instance of any notification
     * present in the notifications list.
     * Please do not confuse this with the getTrades method.
     * @return List of Trade LISTS. (A list of lists! How.. obfusticated...)
     */
    public List<TradeList> getTradesList() {
        List<TradeList> tradeLists = new ArrayList<TradeList>();
        for (Notification note:notifications)
            if (note instanceof TradeList)
                tradeLists.add((TradeList)note);
        return tradeLists;
    }

    /**
     * Returns the list of INDIVIDUAL trades, based upon the profiles being an instance of any
     * notification present in the notifications list.
     * Please do not confuse this with the getTradesList method.
     * @return A list of Trades.
     */
    public List<Trade> getTrades() {
        List<Trade> trades = new ArrayList<Trade>();
        for (Notification note:notifications)
            if (note instanceof Trade)
                trades.add((Trade)note);
        return trades;
    }


    /**
     * Pushes all notifications to the internet through the User Database.
     * - If the internet is not available or it is disconnected, the Notification
     * is not removed from the list
     * - If the commit is successful, then we remove the Notification
     * @param userDB UserDatabase Object.
     * @throws IOException
     */

    public void push(UserDatabase userDB) {
        lock = true;
        for (Notification note : notifications) {
            try {
                // Perform needed changes
                if (note.hasChanged())
                    if (!note.commit(userDB))
                        note.notifyDB();
            } catch (Exception e2) {
                // if exception then also fails to commit
                note.notifyDB();
            }
        }
        lock = false;
        notifications.addAll(newNotifications);
        notifications.removeAll(oldNotifications);
        newNotifications.clear();
        oldNotifications.clear();
    }
}
