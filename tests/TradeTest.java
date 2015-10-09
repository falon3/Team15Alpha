
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

public class TradeTest {
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
}
