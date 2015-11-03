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


import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

public class TradeTest extends ActivityInstrumentationTestCase2 {
    public TradeTest() {
        super(com.skilltradiez.skilltraderz.TradeTest.class);
    }

    public void testInitTrade() {
        UserDatabase db = new UserDatabase();
        try {
            User user = db.createUser("u", "p");
            User user2 = db.createUser("u2", "p");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill("illlllll", "LLLLLLLLLLLLLLLL"));

            Trade trade = user.getTradeList().createTrade(user, user2, offer);
            assertEquals(user.getTradeList().getMostRecentTrade(), trade);
            assertEquals(user2.getTradeList().getMostRecentTrade(), trade);
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testAcceptTradeRequest() {
        UserDatabase db = new UserDatabase();
        try {
            User user = db.createUser("u", "p");
            User user2 = db.createUser("u2", "p");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill("illlllll", "LLLLLLLLLLLLLLLL"));

            Trade trade = user.getTradeList().createTrade(user, user2, offer);
            try {
                user2.getTradeList().getMostRecentTrade().setAccepted(user2);
            } catch (InactiveTradeException e1) {
            }
            assertTrue(user.getTradeList().getMostRecentTrade().isAccepted());
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testRefuseTradeRequest() {
        UserDatabase db = new UserDatabase();
        try {
            User user = db.createUser("u", "p");
            User user2 = db.createUser("u2", "p");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill("illlllll", "LLLLLLLLLLLLLLLL"));

            Trade trade = user.getTradeList().createTrade(user, user2, offer);
            // decline the trade
            trade.decline();
            assertTrue(!trade.isActive());
            // delete the trade
            user2.getTradeList().delete(user2.getTradeList().getMostRecentTrade());
            assertTrue(user.getTradeList().getActiveTrades().size() == 0);
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testCounterOfferTradeRequest() {
        UserDatabase db = new UserDatabase();
        try {
            User user = db.createUser("u", "p");
            User user2 = db.createUser("u2", "p");
            List<Skill> offer = new ArrayList<Skill>();
            List<Skill> counterOffer = new ArrayList<Skill>();

            offer.add(new Skill("illlllll", "LLLLLLLLLLLLLLLL"));
            counterOffer.add(new Skill("Counter skill", "meta"));

            Trade trade = user.getTradeList().createTrade(user, user2, offer);
            trade.decline();
            trade.setCounterOffer(user2, counterOffer);
            assertEquals(trade.getCurrentOffer(user2), counterOffer);
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testModifyActiveTrade() {
        UserDatabase db = new UserDatabase();
        try {
            User bob = db.createUser("Bob", "Password");
            User joel = db.createUser("Joel", "Password");

            List<Skill> skillz1 = new ArrayList<Skill>(), skillz2 = new ArrayList<Skill>();
            skillz1.add(new Skill("...YEP"));

            TradeList tl = bob.getTradeList();
            tl.createTrade(bob, joel, skillz2);

            Trade t = tl.getMostRecentTrade();

            // Modify An Active Trade
            t.setCounterOffer(bob, skillz1);
            assertEquals(t.getCurrentOffer(bob), skillz1);

            // Delete An Active Trade
            tl.delete(t);
            assertTrue(tl.getActiveTrades().size() == 0);
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testBrowseTradeHistory() {
        UserDatabase db = new UserDatabase();
        try {
            User bob = db.createUser("Bob", "Password");
            User joel = db.createUser("Joel", "Password");

            List<Skill> skillz1 = new ArrayList<Skill>(), skillz2 = new ArrayList<Skill>();
            skillz1.add(new Skill("...YEP"));

            TradeList tl = bob.getTradeList();

            tl.createTrade(bob, joel, skillz2);
            Trade t1 = tl.getMostRecentTrade();

            tl.createTrade(bob, joel, skillz1);
            Trade t2 = tl.getMostRecentTrade();

            // Trade History Has been updated
            assertTrue(!t1.equals(t2));
        } catch (UserAlreadyExistsException e) {
        }
    }
}
