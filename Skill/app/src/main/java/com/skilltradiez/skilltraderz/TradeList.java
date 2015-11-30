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

    TradeList(ID id) {
        owner = id;
        trades = new ArrayList<ID>();
        newTrades = new ArrayList<ID>();
        deletedTrades = new ArrayList<ID>();
    }

    public List<ID> getPendingTradesList(){
        return newTrades;
    }

    public List<ID> getDeletedTradesList(){
        return deletedTrades;
    }

    public List<ID> getTradesList(){
        return trades;
    }


    public ID getOwnerID() {
        return owner;
    }

    /* This is only deprecated to discourage its use.
    *  - Used a considerable amount by tests
    */
    @Deprecated
    public Trade createTrade(UserDatabase userDB, User user1, User user2, List<Skill> offer) {
        Trade t = new Trade(userDB, user1, user2);
        t.getHalfForUser(user1).setOffer(offer);
        t.getHalfForUser(user1).setAccepted(true);
        addTrade(userDB, t);

        return t;
    }

    public Trade createTrade(UserDatabase userDB, User user1, User user2, List<Skill> offer, List<Skill> request) {
        Trade trade = createTrade(userDB, user1, user2, offer);
        // User1 set this offer, so user2 hasn't accepted
        trade.getHalfForUser(user2).setOffer(request);
        return trade;
    }

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

    public void addTrade(UserDatabase db, Trade trade) {
        if (trades.contains(trade.getTradeID()))
            return;
        trades.add(trade.getTradeID());
        newTrades.add(trade.getTradeID());
        notifyDB();
    }

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

    public void delete(Trade trade) {
        delete(trade.getTradeID());
    }

    public void delete(ID tradeID) {
        trades.remove(tradeID);
        deletedTrades.add(tradeID);
        notifyDB();
    }

    public Trade getMostRecentTrade(UserDatabase userDB) {
        if (trades.isEmpty()) return null;
        return DatabaseController.getTradeByID(trades.get(trades.size()-1));
    }

    @Override
    public boolean commit(UserDatabase userDB)  {
        for (ID tradeId : newTrades) {
            Trade trade = DatabaseController.getTradeByID(tradeId);
            User otherUser = DatabaseController.getAccountByUserID(trade.getHalf2().getUser());
            User theUser = DatabaseController.getAccountByUserID(getOwnerID());
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
            User currentUser = DatabaseController.getAccountByUserID(getOwnerID());
            User tradePartner = DatabaseController.getAccountByUserID(trade.getHalf2().getUser());

            //IF the tradeId is in the trade partners list then delete it.
            if (tradePartner.getTradeList().contains(tradeId)){
                tradePartner.getTradeList().delete(trade);
            }
            //IF the tradeId is in the current user's list then delete it.
            else if (currentUser.getTradeList().contains(tradeId)){
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

    /*
     * This happens when any trade completes successfully
     */
    public void tradeComplete(ID tradeID) {
        MasterController.getCurrentUser().getProfile().tradeSuccess();
        delete(tradeID);
    }

    public String getType() {
        return "TradesList";
    }

    public String getStatus() {
        return "";
    }

    public String getDescription() {
        return "A trade has occured.";
    }
}
