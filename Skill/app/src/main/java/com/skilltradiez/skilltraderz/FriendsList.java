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
import java.util.HashMap;
import java.util.List;

/**~~DESCRIPTION:
 * In an application that is based upon users trading skills with other users... does it not
 * make complete sense to have some sort of list that is assocaited with all of the users
 * that this user is correlated with? This is what this FriendsList class is all about! Put
 * users together into friendship groups! Lovely!
 *
 * This is going to maintain a few attributes such as the current identifier for the current user
 * who actually "owns" this friendslist. This is what will actually associate the user to the
 * object.
 *
 * Then we have three attributes lists that are going to relate the user in question to
 * a list of friends, then a list of old friends and then a list of new friends. We're going to
 * then hand the methods that are going to allow the classes of the remove, add, get, check if
 * the user has, a friend. And then another method that is going to actually allow the user
 * to commit all of their changes to their friendslists to the database.
 *
 *
 * ~~ACCESS:
 * This is going to be a public class meaning that any other point of the program we are actually
 * going to be able to (1) instantiate (2) use the methods of any FriendsList object at any other
 * part of the program we can utilize this object.
 *
 * ~~CONSTRUCTORS:
 * Only one constructor is involved at this point which will take in a ID of a user where
 * we will then have the object assign the attribute owner the value of the ID that is passed
 * into the constructor upon instantiation of a FriendsList object!
 *
 * We then instantiate at this point three brand new aggregated lists into this object, which
 * are going to be a friendslist, a new friends list and an old friends list. This is going
 * to allow this object to isolate these lists that are unique for any particular user
 * of this application. Flexibility is absolutely glorious.
 *
 * ~~ATTRIBUTES/METHODS:
 *1: OWNER:
 *     This is going to be the attribute that we will use that will allow us to retrieve the owner
 *     of this particular friendslist. This is pretty useful considering how if we have many
 *     users we will likely be drown in a sea of users and then we need to identify the
 *     particularities of each and every unique user.
 *
 *
 *     WE PROVIDE THE METHODS TO:
 *     -obtain the user ID for the owner of this object     --getOwner
 *
 * 2: FRIENDSLIST:
 *     Where we actually have the list of the current friends of this user. This is pretty
 *     core considering how our app restraint is that the user can ONLY trade with friends.
 *     So this will be made and maintained here and then referenced and identified throughout
 *     the rest of the application.
 *
 *
 * 3: NEWFRIENDS:
 *     This is going to be the list of new friends that request that the user be on their
 *     friendslist. This is going to allow a great deal of flexibility in our application
 *     where we have a list of people that are trying to deal with the user in our application.
 *
 *
 * 4: OLDFRIENDS:
 *     This is going to be the list that is going to maintain all of the old friends that the user
 *     has, this allows us to uniquely identify all of the more "old" friends that the user has
 *     maintained for a long duration of time.
 *
 *
 * ~~MISC METHODS:
 *    I am putting a lot of methods here, why you ask? Because we maintained three unique
 *    lists of friends and this for the most part is simplfiying the entire process of explaining
 *
 * 1: GETFRIENDS:
 *     This method takes no parameters and then returns to the caller the value of the entire
 *     list of friends. Any of the three lists may be returned to the user.
 *
 *
 * 2: REMOVEFRIEND:
 *     This when we give the name of a terrible, no good, very naughty user ("friend") we will
 *     actually go to our lists and we will remove this user from our lists. And then we now
 *     have one less problem to worry about.
 *
 * 3: ADDFRIEND:
 *     Suppose we want to make new friends (What a novel idea! Idealistic...? Enough philo
 *     references!). This method is going to take in the ID of a particular user of our application
 *     and then it will put this friend into the list of the friends and then it will
 *     grant the user a permanent access to the user from their friendslists and now we have
 *     something that can be modified and maintained throughout the applcication.
 *
 *
 * 4: HASFRIEND:
 *     Suppose we have the predicament where in our application we want to see if the user
 *     actually has a particular friend that we actually want to see if they have or not that
 *     user within their currently saved friendslists.
 *
 *
 * 5: COMMIT:
 *     Given the instance that we're actually going to have the user want to maintain their changes
 *     (What a theory, oh my~) and now we're going to see this actually be put into the database!
 *     Call this method and then we will actively put all of the current object and commit
 *     the changes to the database.
 *
 *     This also has the additional functionality of resetting our new and old friendslists. Meaning
 *     we keep track of who is new and who is old through this method as well! TADA!
 */

/**
 * FriendsList manages confirmed, pending and blocked friends for a single user.
 */
public class FriendsList extends Notification {
    /**Local Class Variables
     * 1: owner, an ID Object that is identifying the owner of this FriendsList Object.
     * 2: friendsList, a list of ID Objects that are going to be the ID Objects corresponding
     *     to the friends of this user who owns this FriendsList Object.
     */
    private ID owner;
    private List<ID> friendsList;

    /**CONSTRUCTOR:
     * Takes in the ID Object for the owner of this FriendsList Object and will assign the
     * local variable owner to the ID passed into the constructor. And then it will instantiate
     * a new ArrayList of ID objects.
     *
     * @param owner_id ID Object representing the owner of the FriendsList.
     */
    FriendsList(ID owner_id) {
        owner = owner_id;
        friendsList = new ArrayList<ID>();
    }


    /**
     * Returns the ID Object for the owner of the FriendsList Object.
     * @return ID Object
     */
    public ID getOwner() {
        return owner;
    }


    /**
     * When invoked, will return the list of friends from the FriendsList Object.
     * @return List of ID Objects.
     */
    public List<ID> getFriends() {
        return friendsList;
    }


    /**
     * Removes the passed in User Object parameter from the FriendsList. Notifies DB of change.
     * @param terrible_person User Object.
     */
    public void removeFriend(User terrible_person) {
        friendsList.remove(terrible_person.getUserID());
        notifyDB();
    }


    /**
     * Adds the passed in User Object parameter to the FriendsList. Notifies DB of change.
     * @param great_person User Object.
     */
    public void addFriend(User great_person) {
        if (hasFriend(great_person)) return;
        friendsList.add(great_person.getUserID());
        notifyDB();
    }


    /**
     * Will return true/false if the passed in User Object is present in the FriendsList.
     * @param that_guy User Object.
     * @return Boolean. True/False.
     */
    public boolean hasFriend(User that_guy) {
        return friendsList.contains(that_guy.getUserID());
    }

    /**
     * When invoked, this method will take in the user database and will update the elastic/DB.
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
    public boolean commit(UserDatabase userDB) {
        User owner = DatabaseController.getAccountByUserID(getOwner());
        try {
            userDB.getElastic().updateDocument("user", owner.getProfile().getUsername(), this, "friendsList");
        } catch (IOException e) {
            // try again later
            return false;
        }
        return true;
    }
}
