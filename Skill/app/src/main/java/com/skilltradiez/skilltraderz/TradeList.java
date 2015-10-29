package com.skilltradiez.skilltraderz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */

public class TradeList {
    private UserID owner;
    private List<Trade> trades;

    TradeList(UserID id) {
        owner = id;
        trades = new ArrayList<Trade>();
    }

    public Trade createTrade(User user1, User user2, List<Skill> offer) {
        return null;
    }

    public Trade getMostRecentTrade() {
        return null;
    }

    public List<Trade> getActiveTrades() {
        List<Trade> activeTrades = new ArrayList<Trade>();

        for (Trade t:trades)
            if (t.isActive())
                activeTrades.add(t);

        return activeTrades;
    }

    public void delete(Trade trade) {
        trades.remove(trade);
    }
}
