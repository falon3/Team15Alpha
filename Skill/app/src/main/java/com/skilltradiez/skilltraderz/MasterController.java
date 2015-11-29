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
import java.util.Set;

/**
 * Activities should ideally never need to interact with the model directly.
 * This acts as a controller that facilitates the communication from the views to the models that
 * we possess.
 * <p/>
 * Assuming direct control.
 * --Sovereign, mass effect
 */
public final class MasterController implements ControllerInterface {
    private static DatabaseController databaseController;

    /**
     * DATABASE RELATED
     **/
    //Initialize the master controller.
    //Absolutely core, this sets up the entire system of controllers.
    public void initializeController() {
        databaseController = new DatabaseController();
    }

    //Return the database object! Only available to other controller objects.
    public static UserDatabase getUserDB() {
        return databaseController.getUserDB();
    }

    /**
     * USER RELATED
     **/
    //If we probe for the USER that is currently on the app... returns the USER object of that user.
    //NOT just the name. USER object.
    public static User getCurrentUser() {
        return getUserDB().getCurrentUser();
    }

    //Give the current username from the database.
    public String getCurrentUserUsername() {
        return getUserDB().getCurrentUser().getProfile().getUsername();
    }

    public String getCurrentUserEmail() {
        return getUserDB().getCurrentUser().getProfile().getEmail();
    }

    //Given a profile name we return a user
    public User getUserByName(String userProfileName) {
        return DatabaseController.getAccountByUsername(userProfileName);
    }

    public User getUserByID(ID userID) {
        return DatabaseController.getAccountByUserID(userID);
    }

    /**
     * FRIEND RELATED
     **/
    //Do they have THIS friend in particular.
    public boolean hasFriend(User friend) {
        return getCurrentUser().getFriendsList().hasFriend(friend);
    }

    public void addFriend(User friend) {
        getCurrentUser().getFriendsList().addFriend(friend);
    }

    public void removeFriend(User friend) {
        getCurrentUser().getFriendsList().removeFriend(friend);
    }

    /**
     * SKILLZ RELATED FUNCTIONS
     **/
    //Clear the current List<Skill> of skillz!
    // this seems unnecessary
    public void clearSkillzList(List<Skill> skillz) {
        skillz.clear();
    }

    public boolean userHasSkill(Skill skill) {
        return getCurrentUser().getInventory().hasSkill(skill);
    }

    //Obtain from the user database all of the current skills!
    public Set<Skill> getAllSkillz() {
        return getUserDB().getSkills();
    }

    public Set<User> getAllUserz() {
        return getUserDB().getUsers();
    }

    public Set<Trade> getAllTradez() {
        return getUserDB().getTrades();
    }

    public List<Trade> getAllTradezForCurrentUser() {
        ArrayList<Trade> trades = new ArrayList<Trade>();
        for (ID id : getCurrentUser().getTradeList().getTradesList())
            trades.add(getTradeByID(id));
        return trades;
    }

    public void makeNewSkill(String name, String category, String description, String quality, boolean isVisible, List<Image> images) {
        getCurrentUser().getInventory().add(new Skill(getUserDB(), name, category, description, quality, isVisible, images));
        DatabaseController.save();
    }

    /**
     * SkillDescriptionActivity methods
     **/
    public void removeCurrentSkill(Skill currentSkill) {
        getCurrentUser().getInventory().remove(currentSkill.getSkillID());
        currentSkill.removeOwner(getCurrentUser().getUserID());
    }

    public void addCurrentSkill(Skill currentSkill) {
        getCurrentUser().getInventory().add(currentSkill);
        currentSkill.addOwner(getCurrentUser().getUserID());
    }

    public void deleteTrade(Trade trade) {
        DatabaseController.deleteDocumentTrade(trade.getTradeID());
        getUserDB().getTrades().remove(trade);
        getUserByID(trade.getHalf1().getUser()).getTradeList().delete(trade);
        getUserByID(trade.getHalf2().getUser()).getTradeList().delete(trade);
    }

    public List<Skill> getSkillList(List<ID> ids) {
        List<Skill> skills = new ArrayList<Skill>();
        for (ID id : ids)
            skills.add(DatabaseController.getSkillByID(id));
        return skills;
    }

    public List<Stringeable> getStringeableSkillList(List<ID> ids) {
        List<Stringeable> skills = new ArrayList<Stringeable>();
        for (ID id : ids)
            skills.add(DatabaseController.getSkillByID(id));
        return skills;
    }

    /**
     * TradeRequestActivity methods
     **/
    //Given the ID of a trade, we will now RETURN a TRADE OBJECT to the caller of this method.
    public Trade getTradeByID(ID identifier) {
        return DatabaseController.getTradeByID(identifier);
    }

    //ACCEPT THE TRADE
    public void acceptTheCurrentTrade(Trade trade) {
        trade.getHalfForUser(getCurrentUser()).setAccepted(true);
    }
}
