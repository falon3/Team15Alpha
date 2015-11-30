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

/**
 * Trades are the core element of our application. Without the ability to create new trade
 * objects this application would become absolutely pointless. Trades are how the users interact
 * with one another and are the main driving and drawing appeal for the users to actually
 * USE our application.
 *
 * A trade object will possess attrbutes relating two users together as actors (actor1 and actor2)
 * and then will have a list of skills which are being offered for each user, and a boolean that
 * will maintain whether or not the trade is actually being accepted or not by the users!
 * As well as if it is already completed (and fully accepted) or not.
 *
 * In terms of the methods avaliable here, we have the various methods in which we will have the
 * ability to obtain the current actors in the trade (object of trade), get offers, set (new counter
 * offers) offers, set if accepted or declined or if it is even still active or not!
 */

import java.util.List;

public class Trade extends Stringeable {
    private ID tradeID;
    private HalfTrade half1, half2;

    /**
     * The constructor takes in the database and the two users partipating in the Trade, creating
     * two new HalfTrade Objects- one for each user. This Constructor finishes by adding
     * itself to the database.
     * @param db UserDatabase Object.
     * @param user1 User Object.
     * @param user2 User Object.
     */
    Trade(UserDatabase db, User user1, User user2) {
        tradeID = ID.generateRandomID();
        half1 = new HalfTrade(tradeID, user1.getUserID(), 1);
        half2 = new HalfTrade(tradeID, user2.getUserID(), 2);

        DatabaseController.addTrade(this);
    }

    /**
     * Basic getter method to obtain the first half trade of the entire Trade Object.
     * @return HalfTrade Object.
     */
    public HalfTrade getHalf1() {
        return half1;
    }

    /**
     * Basic getter method to obtain the second half trade of the entire Trade Object.
     * @return HalfTrade Object.
     */
    public HalfTrade getHalf2() {
        return half2;
    }

    /**
     * Getter method that when it is supplied with a User Object as a parameter will return the
     * OPPOSITE half trade associated with that particular User Object.
     * @param user User Object.
     * @return HalfTrade Object.
     */
    public HalfTrade getOppositeHalf(User user) {
        if (half1.getUser().equals(user.getUserID())) {
            return half2;
        } else if (half2.getUser().equals(user.getUserID())) {
            return half1;
        }
        return null;
    }

    /**
     * Gets the parts of this trade corresponding to the User Object supplied as a parameter.
     * @param user User Object.
     * @return The half corresponding to the user, or null if the user is not involved in the trade.
     */
    public HalfTrade getHalfForUser(User user) {
        if (half1.getUser().equals(user.getUserID())) {
            return half1;
        } else if (half2.getUser().equals(user.getUserID())) {
            return half2;
        }
        return null;
    }

    /**
     * Basic setter method that takes in a large batch of parameters and sets the trade variables
     * with these given parameters.
     * @param userDB UserDatabase Object.
     * @param user1 User Object.
     * @param user2 User Object.
     * @param offer List of Skill Objects.
     * @param request List of Skill Objects.
     */
    public void set(UserDatabase userDB, User user1, User user2, List<Skill> offer, List<Skill> request){
        Trade t = new Trade(userDB, user1, user2);
        t.getHalfForUser(user1).setOffer(offer);
        t.getHalfForUser(user1).setAccepted(true);
        t.getHalfForUser(user2).setOffer(request);
        t.getHalfForUser(user2).setAccepted(false);
    }

    /**
     * Will take in an Object (any ol object...) of the Object type; and will then compare the
     * current object with the object passed into the method. If they are equal return true. If
     * they are not equal, then return false.
     * @param inputObject Object Object.
     * @return Boolean. True/False.
     */
    @Override
    public boolean equals(Object inputObject) {
        if (this == inputObject) return true;
        if (inputObject == null || getClass() != inputObject.getClass()) return false;

        Trade trade = (Trade) inputObject;

        return !(tradeID != null ? !tradeID.equals(trade.tradeID) : trade.tradeID != null);

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
        return tradeID != null ? tradeID.hashCode() : 0;
    }

    public ID getTradeID() {
        return tradeID;
    }

    /**
     * A trade is active if both parties have not yet accepted it.
     * @return Boolean. True/False.
     */
    public boolean isActive() {
        return !(half1.isAccepted() && half2.isAccepted());
    }

    /**
     * This method will check if the Trade Object is flagged as a complete Trade.
     * Returns True if it is complete, False if it is not complete.
     * @return Boolean. True/False.
     */
    public boolean checkIfComplete() {
        if (half1.hasChanged() || half2.hasChanged())
            notifyDB();
        if (!isActive())
            MasterController.getCurrentUser().getTradeList().tradeComplete(tradeID);
        return true;
    }

    /**
     * This method, when invoked, will take both HalfTrade objects associated with the Trade Object
     * and will send them into the database for safe keeping.
     *
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
    public boolean commit(UserDatabase userDB) {
        if (half1.hasChanged())
            if (!half1.commit(userDB))
                return false;
        if (half2.hasChanged())
            if (!half2.commit(userDB))
                return false;
        return true;
    }

    /**
     * Basic getter method where we will return the Strinf og the name of the Trade.
     * In particular this returns something like "user1 --> user2". Fancy.
     * @return String of the user1 --> user2
     */
    public String getName() {
        //TITLE
        return DatabaseController.getAccountByUserID(half1.getUser()).getProfile().getName() +
                " --> " +
                DatabaseController.getAccountByUserID(half2.getUser()).getProfile().getName();
    }

    /**
     * Basic getter method that will return the String of the Trade status; either it is active
     * or it is inactive.
     * @return String of state, either "Active" or "Inactive".
     */
    public String getCategory(){
        if(isActive()){
            return "Active";
        }
        return "Inactive";
    }

    /**
     * Getter method that when evoked on the Trade Object, will give us the description of each
     * side of the trade involved in the complete Object as a String.
     * Return is of the format: "description1 for description2"
     * @return String of the HalfTrade descriptions.
     */
    public String getDescription() {
        // SUBTITLE
        MasterController masterController = new MasterController();

        String desc1 = "", desc2 = "";
        List<Skill> offer = masterController.getSkillList(getHalf1().getOffer()),
                request = masterController.getSkillList(getHalf2().getOffer());

        if (!offer.isEmpty())
            desc1 = offer.get(0).getName();

        if (!request.isEmpty())
            desc2 = request.get(0).getName();

        for (int i=1;(i < 4) && ((i < offer.size()) || (i < request.size()));i++) {
            if (i < offer.size())
                desc1 += offer.get(i).getName() + ", ";
            if (i < request.size())
                desc2 += request.get(i).getName() + ", ";
        }

        if (offer.size() > 4) desc1 += "... ";
        if (request.size() > 4) desc2 += "...";

        return desc1 + "for " + desc2;
    }

    /**
     * Getter method that when invoked will return the Image from the Skill.
     * @return Image Object.
     */
    public Image getImage() {
        return new NullImage();
    }

    /**
     * Getter method that returns the top traders of the application.
     * @return Integer of the top traders.
     */
    public int getTop() {
        return DatabaseController.getAccountByUserID(getOppositeHalf(MasterController.getCurrentUser()).getUser()).getProfile().getTop();
    }

    /**
     * Special String method that will return to the caller of this method: the users, and all
     * of the skill names involved. This can get quite... involved and long if the trade is quite
     * a large trade. 
     * @return String format of the trade.
     */
    @Override
    public String toString() {
        MasterController msCont = new MasterController();
        String string = half1.getUser().toString() + " " + half2.getUser().toString() + " ";

        // Skill Names: Might be a bit much
        for (ID skillID:half1.getOffer())
            string += DatabaseController.getSkillByID(skillID).getName() + " ";
        for (ID skillID:half2.getOffer())
            string += DatabaseController.getSkillByID(skillID).getName() + " ";

        return string;
    }
}
