package com.skilltradiez.skilltraderz;

import android.util.Log;

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
public final class MasterController implements ControllerInterface{
    private static DatabaseController databaseController;

    /** DATABASE RELATED **/
    //Initialize the master controller.
    //Absolutely core, this sets up the entire system of controllers.
    public void initializeController(){
        databaseController = new DatabaseController();
    }

    //Return the database object! Only available to other controller objects.
    public static UserDatabase getUserDB(){
        return databaseController.getUserDB();
    }

    /** USER RELATED **/
    //If we probe for the USER that is currently on the app... returns the USER object of that user.
    //NOT just the name. USER object.
    public User getCurrentUser(){
        return getUserDB().getCurrentUser();
    }

    //Give the current username from the database.
    public String getCurrentUserUsername(){
        return getUserDB().getCurrentUser().getProfile().getUsername();
    }

    public String getCurrentUserEmail(){
        return getUserDB().getCurrentUser().getProfile().getEmail();
    }

    //Given a profile name we return a user
    public User getUserByName(String userProfileName){
        return DatabaseController.getAccountByUsername(userProfileName);
    }

    public User getUserByID(ID userID){
        return DatabaseController.getAccountByUserID(userID);
    }

    /**FRIEND RELATED **/
    //Do they have THIS friend in particular.
    public boolean userHasFriend(User user){
        return user.getFriendsList().hasFriend(getUserDB().getCurrentUser());
    }

    public void addANewFriend(User currentUser){
        getCurrentUser().getFriendsList().addFriend(currentUser);
    }

    public void removeThisFriend(User currentUser){
        getCurrentUser().getFriendsList().removeFriend(currentUser);
    }

    /** SKILLZ RELATED FUNCTIONS **/
    //Clear the current List<Skill> of skillz!
    // this seems unnecessary
    public void clearSkillzList(List<Skill> skillz){
        skillz.clear();
    }

    public boolean userHasSkill(Skill skill) {
        return getCurrentUser().getInventory().hasSkill(skill);
    }

    //Obtain from the user database all of the current skills!
    public Set<Skill> getAllSkillz(){
        return getUserDB().getSkills();
    }

    public Set<User> getAllUserz(){
        return getUserDB().getUsers();
    }

    public Set<Trade> getAllTradez() {return getUserDB().getTrades();}

    public void makeNewSkill(String name, String category, String description, boolean isVisible, Image image){
        getCurrentUser().getInventory().add(new Skill(getUserDB(), name, category, description, isVisible, image));
        DatabaseController.save();
    }

    /** SkillDescriptionActivity methods **/
    public void removeCurrentSkill(Skill currentSkill){
        getCurrentUser().getInventory().remove(currentSkill.getSkillID());
        currentSkill.removeOwner(getCurrentUser().getUserID());
    }

    public void addCurrentSkill(Skill currentSkill){
        getCurrentUser().getInventory().add(currentSkill);
        currentSkill.addOwner(getCurrentUser().getUserID());
    }

    /** TradeRequestActivity methods **/
    //Given the ID of a trade, we will now RETURN a TRADE OBJECT to the caller of this method.
    public Trade getCurrentTradeByID(ID identifier){
        return DatabaseController.getTradeByID(identifier);
    }

    //ACCEPT THE TRADE
    public void acceptTheCurrentTrade(Trade trade){
        trade.getHalfForUser(getCurrentUser()).setAccepted(true);
    }
}
