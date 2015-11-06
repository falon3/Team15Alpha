package com.skilltradiez.skilltraderz;
/** ~~DESCRIPTION:
 *
 * BEFORE I EVEN BEGIN: This is a GENERAL ID THAT IS USED FOR
 * 1: USERS
 * 2: TRADES
 * 3: INVENTORIES
 *
 * This is used for ALL THREE. However in the description below I will use specific examples at
 * times. BUT DO NOT GET CONFUSED. THIS ID IS A GENERAL ID THAT COVERS ALL THREE OF THESE.
 *
 * An ID is going to be the absolutely essential core bit of information that this application
 * is going to utilize to identify the user to other users, trades, and inventories This is going
 * to be how the user, trade or inventory is going to be shown and will tie the user down to a
 * particular string of characters. That being said this is a core feature that is weaved through
 * the entire application making touches/references to multiple classes across multiple files.
 * This is the quissential identifier.
 *
 * In this object we have the private number that is going to be associated with the user and this
 * is going to be the arbitrarily assigned id of the user. This is not something that we exactly
 * want the user to care about, this is something that the application is going to care about.
 *
 * ~~ACCESS:
 * This is going to be a PUBLIC method, meaning that any other class or anything else within
 * this application can infact make this UserID object, this grants us the ability to have ease
 * of access to create the UserID.
 *
 * ~~CONSTRUCTORS:
 * This class has one single constructor, this is going to take in the "num" which is of the
 * type Number and then it will make use of this num in order to assign a value to the userID
 * attribute. And then we can utilize this class to represent the user in a unique numerical
 * fashion at whim! Makes life a lot easier than dealing with "xXxLegolass9172XxXxXxXxxXxXxXXx".
 *
 *
 * ~~ATTRIBUTES/METHODS:
 *  1: ID:
 *      This is going to be the numerical identifier of the user. This allows the application
 *      a great deal of ease and flexibility when dealing with multiple users with seemingly
 *      arbitrary names to them. Granting us the ability to have the users identified by a
 *      simple-to-deal-with-number.
 *
 *      WE PROVIDE THE METHODS TO:
 *      -Get the ID                     --getID
 *      -if the ID equals another      --equals
 *
 * ~~MISC METHODS:
 *
 * 1: HASHCODE:
 *     This is going to be the method to allow the user's ID to be turned into a hash value
 *     where the hash is a safe "message digest" of the user's ID. This means that we can
 *     VERIFY the integrity of the userID WITHOUT revealing the exact userID. This is securitay!
 *
 *
 * 2: GENERATERANDOMID:
 *     When we have a new user is it not going to be considered essential to have a unique
 *     ID generated for this user? This is what we call at that time.
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

import java.io.Serializable;
import java.util.Random;

/**
 * Created by sja2 on 10/28/15.
 */
public class ID implements Serializable {
    private Long id;

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ID id1 = (ID) o;

        return !(id != null ? !id.equals(id1.id) : id1.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    ID(long num) {
        id = num;
    }

    public Number getID() {
        return id;
    }

    static ID generateRandomID() {
        return new ID(new Random().nextLong());
    }
}
