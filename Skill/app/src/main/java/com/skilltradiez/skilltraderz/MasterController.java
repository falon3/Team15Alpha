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
 *
 * Assuming direct control.
 * --Sovereign, mass effect
 */
public final class MasterController {

    public MasterController() {}

    /**Class Variables:
     * 1: databaseController: Holds a copy of the databaseController, kept static so that the
     *      controller for the database will always keep it's same database.
     */
    private static DatabaseController databaseController;

    /**
     * Initialize the master controller.
     * Absolutely core, this sets up the entire system of controllers.
     */
    public void initializeController() {
        databaseController = new DatabaseController();
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    /**
     * Return the database object! Only available to other controller objects.
     * @return UserDatabase Object.
     */
    public static UserDatabase getUserDB() {
        if (databaseController.getUserDB() == null)
            databaseController = new DatabaseController();
        return databaseController.getUserDB();
    }


    /**
     * If we probe for the USER that is currently on the app... returns the USER object of that user.
     * NOT just the name. USER object.
     * @return User Object.
     */
    public static User getCurrentUser() {
        return getUserDB().getCurrentUser();
    }

    /**
     * Get the username of the current user from the database.
     * @return String of the current user username.
     */
    public String getCurrentUserUsername() {
        return getUserDB().getCurrentUser().getProfile().getUsername();
    }

    /**
     * Get the email of the current user from the database.
     * @return String of the current user email.
     */
    public String getCurrentUserEmail() {
        return getUserDB().getCurrentUser().getProfile().getEmail();
    }

    /**
     * Given a profile name we return a user
     * @param userProfileName String input of a user's profile name.
     * @return User Object.
     */
    public User getUserByName(String userProfileName) {
        return DatabaseController.getAccountByUsername(userProfileName);
    }

    /**
     * Given an ID Object, return the User Object from the database.
     * @param userID ID Object.
     * @return User Object.
     */
    public User getUserByID(ID userID) {
        return DatabaseController.getAccountByUserID(userID);
    }


    /**
     * Answers the question "Do they have THIS friend in particular?!" when given a User as a
     * parameter to this method.
     * @param friend User Object of the profile in question.
     * @return Boolean. True/False.
     */
    public boolean hasFriend(User friend) {
        return getCurrentUser().getFriendsList().hasFriend(friend);
    }

    /**
     * Adds a given User Object as a friend of the current user.
     * @param friend User Object of the User to add as a friend.
     */
    public void addFriend(User friend) {
        getCurrentUser().getFriendsList().addFriend(friend);
    }

    /**
     * Removes a given User Object from the current user's friend list.
     * @param friend User Object of the friend to be removed (OWCH!)!
     */
    public void removeFriend(User friend) {
        getCurrentUser().getFriendsList().removeFriend(friend);
    }


    /**
     * Clear the current List<Skill> of skillz!
     * @param skillz List of Skill Objects.
     */
    public void clearSkillzList(List<Skill> skillz) {
        skillz.clear();
    }

    /**
     * This method answers the question "Does this user have THIS skill or not?" with a boolean,
     * pass in the Skill Object to be checked.
     * @param skill Skill Object.
     * @return Boolean. True/False.
     */
    public boolean userHasSkill(Skill skill) {
        return getCurrentUser().getInventory().hasSkill(skill);
    }

    /**
     * Obtain from the user database all of the current skills!
     * @return Set of Skill Objects.
     */
    public Set<Skill> getAllSkillz() {
        return getUserDB().getSkills();
    }

    /**
     * Basic getter method that will return the list of all Users of the application from the
     * database.
     * @return Set of User Objects.
     */
    public Set<User> getAllUserz() {
        return getUserDB().getUsers();
    }


    /**
     * Basic getter method that will return the list of ALL Trades of the application from the
     * database- do not confuse this with getAllTradezForCurrentUser method!
     * @return Set of Trade Objects.
     */
    public Set<Trade> getAllTradez() {
        return getUserDB().getTrades();
    }

    /**
     * Basic getter method that will return the list of Trades Objects of the current user from the
     * database.
     * @return List of Trade Objects.
     */
    public List<Trade> getAllTradezForCurrentUser() {
        List<Trade> trades = new ArrayList<Trade>();
        TradeList tradeList = getCurrentUser().getTradeList();

        for (ID id : tradeList.getTradesList())
            trades.add(getTradeByID(id));
        return trades;
    }

    /**
     * Create a new Skill Object with the given parameters.
     * @param name String input.
     * @param category String input.
     * @param description String input.
     * @param quality String input.
     * @param isVisible Boolean Flag.
     * @param images List of Image Objects.
     */
    public Skill makeNewSkill(String name, String category, String description, String quality, boolean isVisible, List<Image> images) {
        Skill s = new Skill(getUserDB(), name, category, description, quality, isVisible, images);
        addCurrentSkill(s);
        DatabaseController.save();
        return s;
    }

    /**
     * Create a new Skill Object with the given parameters.
     * @param name String input.
     * @param category String input.
     * @param description String input.
     * @param isVisible Boolean Flag.
     * @param images List of Image Objects.
     */
    @Deprecated
    public Skill makeNewSkill(String name, String category, String description, boolean isVisible, List<Image> images) {
        return makeNewSkill(name, category, description, "good enough", isVisible, images);
    }


    /**
     * Remove a Skill Object from the current user.
     * @param currentSkill Skill Object to remove.
     */
    public void removeCurrentSkill(Skill currentSkill) {
        getCurrentUser().getInventory().remove(currentSkill.getSkillID());
        currentSkill.removeOwner(getCurrentUser().getUserID());
    }

    /**
     * Add a Skill Object to the current user.
     * @param currentSkill Skill Object to add.
     */
    public void addCurrentSkill(Skill currentSkill) {
        getCurrentUser().getInventory().add(currentSkill);
        currentSkill.addOwner(getCurrentUser().getUserID());
    }

    /**
     * Delete the Trade Object passed in as a parameter from the current user.
     * @param trade Trade Object to delete.
     */
    public void deleteTrade(Trade trade) {
        getUserByID(trade.getHalf1().getUser()).getTradeList().delete(trade);
        getUserByID(trade.getHalf2().getUser()).getTradeList().delete(trade);

        getUserDB().getChangeList().getTrades().remove(trade);
        getUserDB().getChangeList().getTradesList().remove(trade);

        DatabaseController.save();

        getUserDB().getTrades().remove(trade);
        DatabaseController.deleteDocumentTrade(trade.getTradeID());

        DatabaseController.save();
    }

    /**
     * Given a list of ID Objects, returns the corresponding Skill Objects to those ID Objects
     * passed in as parameters.
     * @param ids List of ID Objects.
     * @return List of Skill Objects.
     */
    public List<Skill> getSkillList(List<ID> ids) {
        List<Skill> skills = new ArrayList<Skill>();
        for (ID id : ids)
            skills.add(DatabaseController.getSkillByID(id));
        return skills;
    }

    /**
     * Given a List of ID Objects, return a list of Stringeable Objects.
     * @param ids List of ID Objects.
     * @return List of Stringeable Objects.
     */
    public List<Stringeable> getStringeableSkillList(List<ID> ids) {
        List<Stringeable> skills = new ArrayList<Stringeable>();
        for (ID id : ids)
            skills.add(DatabaseController.getSkillByID(id));
        return skills;
    }


    /**
     * Given the ID of a trade, we will return the corresponding Trade Object to the caller of
     * this method.
     * @param identifier ID Object.
     * @return Trade Object.
     */
    public Trade getTradeByID(ID identifier) {
        return DatabaseController.getTradeByID(identifier);
    }

    /**
     * Given a Trade Object as a parameter, this method will confirm the Trade as being accepted.
     * @param trade Trade Object to be accepted.
     */
    public void acceptTheCurrentTrade(Trade trade) {
        trade.getHalfForUser(getCurrentUser()).setAccepted(true);
    }
}
