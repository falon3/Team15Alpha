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

public class TradeTest extends ActivityInstrumentationTestCase2{
    public TradeTest() {
        super(com.skilltradiez.skilltraderz.TradeTest.class);
    }

    public void testInitTrade() {
        UserDatabase db = new UserDatabase();
        User user = db.createUser("u", "p");
        User user2 = db.createUser("u2", "p");

        Trade trade = user.getTradeList().createTrade(user, user2, new Skill("illlllll", "LLLLLLLLLLLLLLLL"));
        assertEquals(user.getTradeList().getMostRecentTrade(), trade);
        assertEquals(user2.getTradeList().getMostRecentTrade(), trade);
    }

    public void testAcceptTradeRequest() {
        UserDatabase db = new UserDatabase();
        User user = db.createUser("u", "p");
        User user2 = db.createUser("u2", "p");

        Trade trade = user.getTradeList().createTrade(user, user2, new Skill("illlllll", "iLLLLLLLLLLLLLLLL"));
        user2.getTradeList().getMostRecentTrade().setAccepted(user2);
        assertTrue(user.getTradeList().getMostRecentTrade().getAccepted(user2));
    }

    public void testRefuseTradeRequest() {
        UserDatabase db = new UserDatabase();
        User user = db.createUser("u", "p");
        User user2 = db.createUser("u2", "p");

        Trade trade = user.getTradeList().createTrade(user, user2, new Skill("illlllll", "iLLLLLLLLLLLLLLLL"));
        // decline the trade
        trade.setDeclined(user2);
        assertTrue(trade.getDeclined(user2));
        // delete the trade
        user2.getTradeList().delete(user2.getTradeList().getMostRecentTrade());
        assertTrue(user.getTradeList().getActiveTrades().size() == 0);
    }

    public void testCounterOfferTradeRequest() {
        UserDatabase db = new UserDatabase();
        User user = db.createUser("u", "p");
        User user2 = db.createUser("u2", "p");

        Trade trade = user.getTradeList().createTrade(user, user2, new Skill("illlllll", "iLLLLLLLLLLLLLLLL"));
        trade.setDeclined(user2);
        trade.setCounterOffer(new List { new Skill("Counter skill", "meta") });
        assertEquals(trade.getCounterOffer(), new List { new Skill("Counter skill", "meta") });
    }
    public void testModifyActiveTrade() {
        UserDatabase db = new UserDatabase();
        User bob = db.createUser("Bob","Password");
        User joel = db.createUser("Joel","Password");

        List<Skill> skillz1 = new List<Skill>(), skillz2 = new List<Skill>();
        skillz1.add(new Skill("...YEP"));

        TradeList tl = bob.getTradelist();
        tl.createTrade(bob, joel, skillz2);

        Trade t = tl.getMostRecentTrade();

        // Modify An Active Trade
        t.changeOffer(bob, skillz1);
        assertEquals(t.getCurrentOffer(bob), skillz1);

        // Delete An Active Trade
        tl.delete(t);
        assertTrue(tl.getActiveTrades().size() == 0);
    }

    public void testBrowseTradeHistory() {
        UserDatabase db = new UserDatabase();
        User bob = db.createUser("Bob","Password");
        User joel = db.createUser("Joel","Password");

        List<Skill> skillz1 = new List<Skill>(), skillz2 = new List<Skill>();
        skillz1.add(new Skill("...YEP"));

        TradeList tl = bob.getTradelist();

        tl.createTrade(bob, joel, skillz2);
        Trade t1 = tl.getMostRecentTrade();

        tl.createTrade(bob, joel, skillz1);
        Trade t2 = tl.getMostRecentTrade();

        // Trade History Has been updated
        assertTrue(!t1.equals(t2));
    }
}
