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
 * Neither Trade nor HalfTrade use notifyD because their commit methods are called by TradesList
 * and Trade, respectively.
 */
public class HalfTrade extends Notification {

    /**Class Variables:
     * 1: user, holds the ID Object related to the particular user involved in the trade.
     * 2: tradeID, holds the ID Object related to the particular Trade Object (THIS trade object!)
     * 3: offer, holds a List of ID Objects that represent the total trade offer in this Trade Obj.
     * 4: accepted, either this trade is (TRUE) accepted or it is not (FALSE).
     * 5: part, a basic integer that keeps track of which part of the trade this HalfTrade obj is.
     */
    private ID user, tradeID;
    private List<ID> offer;
    private boolean accepted;
    private int part;

    /**
     * @param trade ID Object for a TRADE.
     * @param user ID Object for a USER.
     * @param part Integer for which part of the trade we're dealing with.
     */
    public HalfTrade(ID trade, ID user, int part) {
        tradeID = trade;
        this.user = user;
        offer = new ArrayList<ID>();
        accepted = false;
        this.part = part;
    }

    /**
     * Returns the ID for the user who "owns" this half trade.
     * @return ID Object.
     */
    public ID getUser() {
        return user;
    }

    /**
     * Sets the ID for this object, the HalfTrade. This user and half trade will be related after.
     * @param user ID Object.
     */
    public void setUser(ID user) {
        this.user = user;
        notifyDB();
    }

    /**
     * Obtains the List of ID Objects that together form the offer of the HalfTrade Object (THIS).
     * @return List of ID Objects.
     */
    public List<ID> getOffer() {
        return offer;
    }

    /**
     * Sets the current offer present in this HalfTrade object to the passed in List of Skill
     * Objects passed into this method.
     * @param offer List of Skill Objects.
     */
    public void setOffer(List<Skill> offer) {
        List<ID> ids = new ArrayList<ID>();
        for (Skill skill : offer)
            ids.add(skill.getSkillID());
        this.offer = ids;
        notifyDB();
    }

    /**
     * Return if the trade has or has not been accepted.
     * @return Boolean. True/False.
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Passed in a boolean to be aware of current state, will change the boolean to accepted.
     * @param accepted Boolean Value. True/False. (Should be FALSE in this instance!!!!!!!!) :)
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
        notifyDB();
    }

    /**
     * Will commit all of the current HalfTrade Object's data located within the model to the DB.
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
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

    /**
     * Pass in an object, and we will then see if this object is equal to the HalfTrade object.
     * @param inputObject Object Object. (Oh how punny.)
     * @return Boolean. True/False.
     */
    @Override
    public boolean equals(Object inputObject) {
        if (this == inputObject) return true;
        if (inputObject == null || getClass() != inputObject.getClass()) return false;

        HalfTrade halfTrade = (HalfTrade) inputObject;

        if (part != halfTrade.part) return false;
        return !(tradeID != null ? !tradeID.equals(halfTrade.tradeID) : halfTrade.tradeID != null);

    }

    /**
     * This method when called is going to give us a hash (horrendous hash function- I am deeply
     * offended) of the tradeID ID Object value. Allowing us to easily compare values.
     * Why is that? Because a hash will make comparison very easy and small.
     *
     * @return Integer. THIS integer represents the hash of the tradeID ID object.
     */
    @Override
    public int hashCode() {
        int result = tradeID != null ? tradeID.hashCode() : 0;
        result = 31 * result + part;
        return result;
    }

    /**
     * Getter method that returns a formatted string of the half trade.
     * @return String formatted like "User1 part's Half-Trade"
     */
    public String getType() {
        return "User"+part+"'s Half-Trade";
    }

    /**
     * Getter method that returns a String based upon the current status of the HalfTrade,
     * either it is "Offered" or it is "Updated".
     * @return String of the status, either "Offered" or "Updated".
     */
    public String getStatus() {
        if (isAccepted())
            return "Offered";
        return "Updated";
    }

    /**
     * Getter method that returns tht description of the HalfTrade Object in question.
     * @return String of the Description of the HalfTrade Object.
     */
    public String getDescription() {
        return "";
    }

    public boolean relatesToUser(ID userID) {
        // I DON'T CARE
        return false;
    }
}
