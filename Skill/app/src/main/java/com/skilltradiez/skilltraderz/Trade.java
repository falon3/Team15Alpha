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
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class Trade extends Notification {
    private ID tradeID;

    public HalfTrade getHalf1() {
        return half1;
    }

    public HalfTrade getHalf2() {
        return half2;
    }

    private HalfTrade half1, half2;

    Trade(UserDatabase db, User user1, User user2) {
        tradeID = ID.generateRandomID();
        half1 = new HalfTrade(tradeID, user1.getUserID(), 1);
        half2 = new HalfTrade(tradeID, user2.getUserID(), 2);
        db.addTrade(this);
    }

    public HalfTrade getHalfForUser(User user) {
        if (half1.getUser().equals(user)) {
            return half1;
        } else if (half2.getUser().equals(user)) {
            return half2;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        return !(tradeID != null ? !tradeID.equals(trade.tradeID) : trade.tradeID != null);

    }

    @Override
    public int hashCode() {
        return tradeID != null ? tradeID.hashCode() : 0;
    }

    public ID getTradeID() {
        return tradeID;
    }

    public Boolean isActive() {
        if (half2.isAccepted() == Boolean.FALSE) return false;
        return !(half1.isAccepted() && half2.isAccepted());
    }

    public boolean commit(UserDatabase userDB) {
        if (half1.readChanged())
            if (!half1.commit(userDB))
                return false;
        if (half2.readChanged())
            if (!half2.commit(userDB))
                return false;
        return true;
    }
}
