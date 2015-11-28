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
import java.util.List;

/**~~DESCRIPTION:
 * We're going to have half a trade involved in this class. Our application is completely
 * dedicated around various users trading their skills to one another, similarily to the trade
 * class we're going to have this class focus ESPECIALLY on each half of the trade that is going
 * on at any given point of time!
 *
 * ~~ACCESS:
 * This is going to be a public class meaning that we're going to actually have all other parts
 * of this application be able to (1) instantiate an object of this class (2) be able to
 * utilize the methods within this class in any given fashion.
 *
 * ~~CONSTRUCTOR:
 * This is going to be a single constructor for this class of objects, where we will pass in
 * ONE users, ONE trade, and then a part into the constructor. The constructor will cause there to
 * be the trade attribute assigned with the trade passed in, the user attribute in this class
 * to be assigned with the passed in user, and then the offer to be a newly instantiated
 * ArrayList of ID's of users, and then we will automatically set the ACCEPTED attribute to false.
 *
 * Why accepted false? Because obviously until the user accepts the trade we are going to have
 * it set to false. Imagine a world in which case we begin a trade and then we have out of the
 * blue all of the users of our application initiate trades that are then automatically accepted.
 * That is tragic, so it is AUTOMATICALLY BY DEFAULT FALSE!
 *
 * We then assign our part attribute the value of the part that was passed into the constructor
 * of this class.
 *
 * ~~ATTRIBUTES/METHODS:
 *
 * 1: USER:
 *     We have a user of this application being passed into the constructor, this user is the one
 *     that is actually dealing with this particular half of a trade. That being said, this is going
 *     to allow us to maintain a unique primary key of the user that will allow us to identify
 *     the particular user that is of our interest!
 *
 *     WE PROVIDE THE METHODS TO:
 *     -get the name of the relevant user       --getUser
 *     *Note: No you cannot set the user, this is assigned from the constructor. No changes!
 *
 * 2: OFFER:
 *     When trading with another user is it not useful to have an offer? I mean theoretically
 *     we could be very cold-shoulder-esque in which we all trade nothing to one another... but
 *     that is boring and would trivialize the entire point of this application and make our
 *     entire team very very sad. :(
 *
 *     SO we provide the ability to get the current offer that this user is proposing in the
 *     application and then we also propose the ability to SET the current offer that the user
 *     is proposing! Traditional getter/setters! TADA!
 *
 *     WE PROVIDE THE METHODS TO:
 *     -Obtain the user's current offer in this trade       --getOffer()
 *     -Change the user's current offer in this trade       --setOffer()
 *
 * 3: ACCEPTED:
 *      99 booleans on the wall, 99 booleans on the wall, smack a boolean to false and then dance
 *      in a circle and 19217281 more booleans on the wall. (Now you'll remember this because that
 *      was absolutely terrible.) The entire point of this boolean is to provide a default
 *      setting that the trade is NOT automatically accepted. That would be an absolute ridiculous
 *      tragedy.
 *
 *      eg: Big Bertha The Cougar Trainer (oh my) is going to offer her skills of cougar training
 *      as something she does. That's great. Now John the Skilless (poor John.) is going to go
 *      into our application and then create a trade with BBTCT (Big Bertha The Cougar Trainer)
 *      and then it will be automatically accepted. This is .... obviously... bad.
 *
 *      So we set it to a default of false, and then we have it turned into true once the trade
 *      is actually accepted. MAAAVELOUS DAAAAALING.
 *
 *      WE PROVIDE THE METHODS TO:
 *      -check if it IS accepted or not!            -isAccepted()
 *      -set the trade to BE accepted!              -setAccepted()
 *
 * 4: TRADEID:
 *     Let's propose this idea, that we actually have a unique identifier for each and every trade
 *     that we use in this application. Seems crazy yeah? Seems... ESSENTIAL? YES! YES IT MUST!
 *     So we have the trades being uniquely identified in this fashion, considering how we're
 *     using a database just imagine all the fun we have. We all of a sudden pull a massive
 *     downpour of various trades for a user and then just...
 *
 *     .... Just no. We need this so we can identify a unique trade and be happy and good
 *     and joyful.
 *
 *     WE PROVIDE THE METHODS TO:
 *     -None.
 *
 * 5: PART:
 *     We're going to actually have a part assigned with the part that is associated with a
 *     given trade. Why would we want this? Because associating all of the parts together with
 *     each trade is just going to keep everything centralized and interconnected and allow us
 *     to make use of the passed parts.
 *
 *     WE PROVIDE THE METHODS TO:
 *     -None.
 *
 * ~~MISC METHODS:
 *     Methods not mentioned anywhere else that were related to attributes, these are
 *     methods that are not directly related to an attribute.
 *
 *     1: COMMIT:
 *         It would be ideal if we had all of these lovely halftrades that are a part of the trade
 *         actually be committed, and say... actually maintained and stored in a database. I mean
 *         maybe I am just in an idealistic world but having things stored somewhere is nice.
 *         And that is why we have this commit method.
 *
 *     2: EQUALS:
 *         Suppose we want to see if two half trades are equal, what are we going to do then?
 *         We're going to call THIS function (and no, no busters of the spectral kind) and
 *         pass into it an object to compare to.
 *
 *     3: HASHCODE:
 *         What if we want a unique identifier for this? A hash code is the ideal way of trying
 *         to uniquely identify a half trade. Call this function? SHAZAM! We get a unique identifier
 *         and it is glorious. Please don't question the nature of possible collisions. Please.
 *         :(
 */

/**
 * Neither Trade nor HalfTrade use notifyDB
 * because their commit methods are called by TradesList and Trade, respectively
 */
public class HalfTrade extends Notification {
    private ID user, tradeID;
    private List<ID> offer;
    private boolean accepted;
    private int part;

    public HalfTrade(ID trade, ID user, int part) {
        tradeID = trade;
        this.user = user;
        offer = new ArrayList<ID>();
        accepted = false;
        this.part = part;
    }

    public ID getUser() {
        return user;
    }

    public void setUser(ID user) {
        this.user = user;
        notifyDB();
    }

    public List<ID> getOffer() {
        return offer;
    }

    public void setOffer(List<Skill> offer) {
        List<ID> ids = new ArrayList<ID>();
        for (Skill skill : offer)
            ids.add(skill.getSkillID());
        this.offer = ids;
        notifyDB();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
        notifyDB();
    }

    @Override
    public boolean commit(UserDatabase userDB) {
        Elastic ela = userDB.getElastic();
        try {
            ela.updateDocument("trade", tradeID.toString(), this, "half" + part);
        } catch (IOException e) {
            notifyDB();
            return false; // try again later
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HalfTrade halfTrade = (HalfTrade) o;

        if (part != halfTrade.part) return false;
        return !(tradeID != null ? !tradeID.equals(halfTrade.tradeID) : halfTrade.tradeID != null);

    }

    @Override
    public int hashCode() {
        int result = tradeID != null ? tradeID.hashCode() : 0;
        result = 31 * result + part;
        return result;
    }
}
