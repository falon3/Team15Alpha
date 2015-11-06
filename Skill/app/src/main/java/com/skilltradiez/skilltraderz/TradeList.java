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

/**
 * Created by sja2 on 10/28/15.
 */

public class TradeList extends Notification {
    private ID owner;
    private List<ID> trades;
    private List<ID> newTrades;
    private List<ID> deletedTrades;

    TradeList(ID id) {
        owner = id;
        trades = new ArrayList<ID>();
        newTrades = new ArrayList<ID>();
        deletedTrades = new ArrayList<ID>();
    }

    public ID getOwnerID() {
        return owner;
    }

    public Trade createTrade(UserDatabase userDB, User user1, User user2, List<Skill> offer) {
        Trade t = new Trade(userDB, user1, user2);
        t.getHalfForUser(user1).setOffer(offer);
        trades.add(t.getTradeID());
        newTrades.add(t.getTradeID());
        notifyDB();

        return t;
    }

    public List<Trade> getActiveTrades(UserDatabase userDB) {
        List<Trade> activeTrades = new ArrayList<Trade>();
        Trade trade;

        for (ID t : trades) {
            trade = userDB.getTradeByID(t);
            if (trade.isActive())
                activeTrades.add(trade);
        }

        return activeTrades;
    }

    public void delete(Trade trade) {
        trades.remove(trade.getTradeID());
        deletedTrades.add(trade.getTradeID());
        notifyDB();
    }

    public Trade getMostRecentTrade(UserDatabase userDB) {
        if (trades.isEmpty()) return null;
        return userDB.getTradeByID(trades.get(trades.size()-1));
    }

    @Override
    public boolean commit(UserDatabase userDB) {
        for (ID tradeId : newTrades) {
            Trade trade = userDB.getTradeByID(tradeId);
            if (!trade.commit(userDB))
                return false;
        }
        newTrades.clear();
        for (ID tradeId : deletedTrades) {
            //TODO how to delete ttrade
        }
        newTrades.clear();
        return true;
    }
}
