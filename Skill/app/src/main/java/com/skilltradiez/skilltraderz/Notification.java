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

/**~~DESCRIPTION:
 * This is going to be an interface in this program for the entire framework of notifications.
 * We may have many sorts of notifications that all vary throughout this application, it is our
 * goal here with this interface to (as is the goal of most interfaces) create a structural
 * framework for the interface that will actually depict the methods that MUST be present in any
 * class that implements this interface.
 *
 * This, like most interfaces, acts as a contract promising any class that implements this
 * interface WILL have these methods listed below.
 */


public abstract class Notification {
    private boolean notify = false;
    public void notifyDB() {
        notify = true;
    }

    /**
     * Reads the flag indicating if something has changed, and clears it.
     */
    public boolean hasChanged() {
        boolean tmp = notify;
        notify = false;
        return tmp;
    }

    abstract boolean commit(UserDatabase userDB);
}
