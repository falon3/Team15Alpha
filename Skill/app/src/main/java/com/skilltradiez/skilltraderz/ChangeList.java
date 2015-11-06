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

class ChangeList {
    private List<Notification> notifications;

    ChangeList() {
        notifications = new ArrayList<Notification>();
    }

    public void add(Notification newNote) {
        notifications.add(newNote);
    }

    /*
     * Pushes all notifications to the internet through the User Database
     * - If the internet is not available or it is disconnected, the Notification
     * is not removed from the list
     * - If the commit is successful, then we remove the Notification
     */
    public void push(UserDatabase userDB) {
        for (Notification note : notifications) {
            try {
                // Perform needed changes
                if (note.readChanged())
                    if (!note.commit(userDB))
                        note.notifyDB();
            } catch (Exception e2) {
                // Continue
                //TODO Catch
            }
        }
    }
}
