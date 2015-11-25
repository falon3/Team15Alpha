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
import java.util.List;

/**~~DESCRIPTION:
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
 *
 * ACCESS:
 * This is a (not specified so default!) public class that will be accessible throughout
 * the entire application. Why? Why my good sir, because change happens everywhere! That is why
 * this is such a majestic class. The methods can be called anywhere and everywhere.
 *
 * CONSTRUCTORS:
 * This class only has a single constructor (ChangeList) which takes in NO PARAMETERS (In other
 * words, please please please instantiate this before you plan to put in notifications into it,
 * well that and pushing an empty list of notifications to the userDB Would be strange....)
 *
 * ATTRIBUTES/METHODS:
 *  1: NOTIFICATIONS:
 *      This is going to be the overall main list that is going to hold together all of the
 *      various notification objects together into a single easy-to-deal-with bundle. Allowing
 *      us to make modifications to this list that will represent modifications in the
 *      entire string of the program.
 *
 *      WE PROVIDE THE METHODS TO:
 *      -Add notification objects to this list!             --add
 *      -Put into the database all of the modifications     --push
 */



class ChangeList {
    private List<Notification> notifications;
    private List<Notification> newNotifications;
    boolean lock;

    ChangeList() {
        notifications = new ArrayList<Notification>();
        newNotifications = new ArrayList<Notification>();
    }

    public void add(Notification newNote) {
        if (lock) newNotifications.add(newNote);
        else notifications.add(newNote);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    /*
     * Pushes all notifications to the internet through the User Database
     * - If the internet is not available or it is disconnected, the Notification
     * is not removed from the list
     * - If the commit is successful, then we remove the Notification
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
        newNotifications.clear();
    }
}
