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

/**
 * We have a trade list as one of the core features within our application. Users in our application
 * are all going to (ideally) have the goal in which case they want to trade skills with one another
 * this list is going to be THE focus of the class/object that facilitates this entire process
 * of trading with other users!
 */

public class TradeList extends Notification {
    private ID owner;
    private List<ID> trades, newTrades, deletedTrades;

    /**
     * Given an ID Object as a parameter, this constructor will assign the owner of this TradeList
     * Object to that ID and then create three new ArrayList of ID Objects.
     * One for all trades.
     * One for only new trades made after the most recent commit.
     * One for only old trades before or on the last commit.
     * @param id ID Object.
     */
    TradeList(ID id) {
        owner = id;
        trades = new ArrayList<ID>();
        newTrades = new ArrayList<ID>();
        deletedTrades = new ArrayList<ID>();
    }

    /**
     * Basic getter method that returns the List of Trade Objects that have not been commited yet.
     * @return List of ID Objects.
     */
    public List<ID> getPendingTradesList(){
        return newTrades;
    }

    /**
     * Basic getter method that returns the List of Trade Objects that have been commited
     * previously.
     * @return List of ID Objects.
     */
    public List<ID> getDeletedTradesList(){
        return deletedTrades;
    }

    /**
     * Basic getter method that returns the List of ID Objects of ALL Trades that have occured.
     * @return List of ID Objects.
     */
    public List<ID> getTradesList(){
        return trades;
    }

    /**
     * Basic getter method that returns the ID Object associated with the owner of this TradeList
     * Object.
     * @return ID Object.
     */
    public ID getOwnerID() {
        return owner;
    }

    /**
     * This method is used to create a new Trade Object, being passed in a volume of parameters
     * that will be utilized to specify the Trade.
     *  NOTE: This is only deprecated to discourage its use.
     *   NOTE: Used a considerable amount by tests
     * @param userDB UserDatabase Object.
     * @param user1 User Object.
     * @param user2 User Object.
     * @param offer List of Skill Objects.
     * @return Trade Object.
     */
    @Deprecated
    public Trade createTrade(UserDatabase userDB, User user1, User user2, List<Skill> offer) {
        Trade t = new Trade(userDB, user1, user2);
        t.getHalfForUser(user1).setOffer(offer);
        t.getHalfForUser(user1).setAccepted(true);
        addTrade(userDB, t);

        return t;
    }

    /**
     * Will create a new Trade Object based upon the supplied parameters to this method.
     * This method is overloading the "createTrade" method so that it may accept more parameters,
     * in particular this takes in the additional parameter of a List of Skill Objects that are
     * meant to represent the request of the trade.
     * @param userDB UserDatabase Object.
     * @param user1 User Object.
     * @param user2 User Object.
     * @param offer List of Skill Objects.
     * @param request List of Skill Objects.
     * @return Trade Object.
     */
    public Trade createTrade(UserDatabase userDB, User user1, User user2, List<Skill> offer, List<Skill> request) {
        Trade trade = createTrade(userDB, user1, user2, offer);
        // User1 set this offer, so user2 hasn't accepted
        trade.getHalfForUser(user2).setOffer(request);
        addTrade(userDB, trade);
        notifyDB();
        return trade;
    }

    /**
     * This method will be passed a ID Object and then return if the ID Object is present or not
     * in the TradeList.
     * Will follow basic iteration over the TradeList Object's contents.
     * @param identification ID Object.
     * @return Boolean. True/False.
     */
    //Iterate through the entire trades list to see IF something is present.
    //More used to prevent a null pointer exception then anything.
    public boolean contains(ID identification){
        for (ID individualID : trades){
            individualID.toString();

            //Compare the values and this is going to have the value of the string iterated through
            //compared to the value of the given ID. If it is equal we return the value true.
            //If not then we will just keep iterating through the loop.
            if (individualID.toString().equals( identification.toString()))
                return true;
        }
        //If it got through the entire loop then it is not in the string and will return the value
        //of false to the user.
        return false;

    }

    /**
     * This method will take in a Trade Object to be added to the TradeList Object.
     * @param db UserDatabase Object.
     * @param trade Trade Object.
     */
    public void addTrade(UserDatabase db, Trade trade) {
        if (trades.contains(trade.getTradeID()))
            return;
        trades.add(trade.getTradeID());
        newTrades.add(trade.getTradeID());
        notifyDB();
    }

    /**
     * Returns a List of Trade Objects that are identified as being active by their boolean flag.
     * @param userDB UserDatabase Object.
     * @return List of Trade Objects.
     */
    public List<Trade> getActiveTrades(UserDatabase userDB) {
        List<Trade> activeTrades = new ArrayList<Trade>();
        Trade trade;

        for (ID t : trades) {
            trade = DatabaseController.getTradeByID(t);
            if (trade.isActive())
                activeTrades.add(trade);
        }

        return activeTrades;
    }

    /**
     * Removes a Trade Object, supplied as a parameter to this method, from the TradeList.
     * @param trade Trade Object.
     */
    public void delete(Trade trade) {
        delete(trade.getTradeID());
    }

    /**
     * Removes a Trade Object from the TradeList, identified by it's particular ID Object which is
     * supplied into this method as a parameter.
     * @param tradeID ID Object.
     */
    public void delete(ID tradeID) {
        trades.remove(tradeID);
        deletedTrades.add(tradeID);
        notifyDB();
    }

    /**
     * When invoked, this method will return the most recent trade within the TradeList Object.
     * @param userDB UserDatabase Object.
     * @return Trade Object.
     */
    public Trade getMostRecentTrade(UserDatabase userDB) {
        if (trades.isEmpty()) return null;
        return DatabaseController.getTradeByID(trades.get(trades.size()-1));
    }

    /**
     * When invoked, this method will take all of the data from the TradeList model and will place
     * it into the database.
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
    @Override
    public boolean commit(UserDatabase userDB)  {
        for (ID tradeId : newTrades) {
            Trade trade = DatabaseController.getTradeByID(tradeId);
            User otherUser = DatabaseController.getAccountByUserID(trade.getHalf2().getUser());
            User theUser = DatabaseController.getAccountByUserID(trade.getHalf1().getUser());
            otherUser.getTradeList().addTrade(userDB, trade);
            theUser.getTradeList().addTrade(userDB, trade);
            try {
                userDB.getElastic().updateDocument("user", otherUser.getProfile().getUsername(), otherUser.getTradeList(), "tradeList");
                userDB.getElastic().updateDocument("user", theUser.getProfile().getUsername(), theUser.getTradeList(), "tradeList");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            if (!trade.commit(userDB))
                return false;
        }

        /** There might be a bug here, I did this delete trade todo... /signed Cole **/
        newTrades.clear();
        for (ID tradeId : deletedTrades) {
            Trade trade = DatabaseController.getTradeByID(tradeId);
            User tradePartner = DatabaseController.getAccountByUserID(trade.getHalf2().getUser());
            User currentUser = DatabaseController.getAccountByUserID(trade.getHalf1().getUser());

            //IF the tradeId is in the trade partners list then delete it.
            if (tradePartner.getTradeList().contains(tradeId)){
                tradePartner.getTradeList().delete(trade);
            }
            //IF the tradeId is in the current user's list then delete it.
            if (currentUser.getTradeList().contains(tradeId)){
                currentUser.getTradeList().delete(trade);
            }

            try {
                userDB.getElastic().updateDocument("user", tradePartner.getProfile().getUsername(), tradePartner.getTradeList(), "tradeList");
                userDB.getElastic().updateDocument("user", currentUser.getProfile().getUsername(), currentUser.getTradeList(), "tradeList");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        //Cleanse the deletedTrades list to be empty again.
        deletedTrades.clear();
        return true;
    }


    /**
     * When invoked, this method will set the Trade Object's flag of success to being True.
     * This is typically called by other methods that signal successful completion of a trade.
     * @param tradeID ID Object.
     */
    public void tradeComplete(ID tradeID) {
        MasterController.getCurrentUser().getProfile().tradeSuccess();
    }

    public boolean relatesToUser(ID userID) {
        return userID.equals(owner);
    }

    /**
     * Basic getter method that returns the type of the TradeList Object.
     * @return String "TradesList"
     */
    public String getType() {
        return DatabaseController.getAccountByUserID(owner).getProfile().getName()+"'s TradesList";
    }

    /**
     * Basic getter method that returns the status of the TradeList Object.
     * @return String of the Status of the TradeList Object.
     */
    public String getStatus() {
        return "";
    }

    /**
     * Basic getter method that returns the description of the TradeList Object.
     * @return String description of TradeList Object.
     */
    public String getDescription() {
        return "A trade has occured.";
    }
}
