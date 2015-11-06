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
    private User actor1, actor2;
    private List<Skill> offer1, offer2;
    private ID tradeID = ID.generateRandomID();
    private Boolean accepted = false, active = true;

    Trade(UserDatabase db, User user1, User user2) {
        actor1 = user1;
        actor2 = user2;
        db.addTrade(this);
    }

    public User getActor1() {
        return actor2;
    }

    public User getActor2() {
        return actor2;
    }

    public ID getTradeID() {
        return tradeID;
    }

    public void setAccepted(User other) throws InactiveTradeException {
        if ((other.equals(actor1) || other.equals(actor2)) && active)
            accepted = true;
            // Give The Goods
            // ~ Trade Initiated
        else {
            throw new InactiveTradeException();
        }
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public Boolean isActive() {
        return active;
    }

    public void decline() {
        active = false;
        accepted = false;

        // The Trade been declined
    }

    public void setOffer(User user, List<Skill> new_offer) {
        if (user.equals(actor1)) {
            offer1 = new_offer;
        } else if (user.equals(actor2)) {
            offer2 = new_offer;
        } else {
            throw new IllegalArgumentException("User is not involved in trade!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (!getActor1().equals(trade.getActor1())) return false;
        if (!getActor2().equals(trade.getActor2())) return false;
        if (offer1 != null ? !offer1.equals(trade.offer1) : trade.offer1 != null) return false;
        if (offer2 != null ? !offer2.equals(trade.offer2) : trade.offer2 != null) return false;
        if (!getTradeID().equals(trade.getTradeID())) return false;
        if (!accepted.equals(trade.accepted)) return false;
        return active.equals(trade.active);

    }

    @Override
    public int hashCode() {
        int result = getActor1().hashCode();
        result = 31 * result + getActor2().hashCode();
        result = 31 * result + (offer1 != null ? offer1.hashCode() : 0);
        result = 31 * result + (offer2 != null ? offer2.hashCode() : 0);
        result = 31 * result + getTradeID().hashCode();
        result = 31 * result + accepted.hashCode();
        result = 31 * result + active.hashCode();
        return result;
    }

    public List<Skill> getCurrentOffer(User user) {
        if (user.equals(actor1)) {
            return offer1;
        } else if (user.equals(actor2)) {
            return offer2;
        }
        return null;
    }

    public void commit(UserDatabase userDB) {
        //TODO
        Elastic ela = userDB.getElastic();
        Trade prev_version;

        try {
            prev_version = ela.getDocumentTrade(tradeID.toString());
            prev_version = userDB.getTradeByID(tradeID);

            //TODO make equals method
            if (!prev_version.equals(this)) {
                // TODO Check for new info


                // TODO Update this object


                // TODO Send your changes
                ela.addDocument("trade", tradeID.getID().toString(), this);
            }

        } catch (IOException e) {
            //TODO Save Locally
            e.printStackTrace();
        }
    }
}
