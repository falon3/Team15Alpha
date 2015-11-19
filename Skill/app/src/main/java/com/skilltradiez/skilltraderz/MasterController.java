package com.skilltradiez.skilltraderz;

import java.util.ArrayList;

/**
 * Activities should ideally never need to interact with the model directly.
 * This acts as a controller that facilitates the communication from the views to the models that
 * we possess.
 *
 * Assuming direct control.
 * --Sovereign, mass effect
 */
public class MasterController {

    private static UserDatabase userDB;

    //Constructor for this. EMPTY.
    public MasterController(){
    }

    public void initDB(){
        userDB = new UserDatabase();
    }

    public static UserDatabase getUserDB(){
        return userDB;
    }


    //Do they have THIS friend in particular.
    public static boolean currentUserHasFriend(User currentUser,){
        return currentUser.getFriendsList().hasFriend(userDB.getCurrentUser());
    }

    //Give the current username from the database.
    public static String getCurrentUserUsername(){

        return userDB.getCurrentUser().getProfile().getUsername();
    }


    //Given a profile name... can we please return THE PROFILE OBJECT?! (Yes. Yes we can.)
    public static User getUserByName(String userProfileName){
        return getUserDB().getAccountByUsername(userProfileName);
    }

    public static String getCurrentUserEmail(){
        return userDB.getCurrentUser().getProfile().getEmail();
    }


    //I hate this.
    public static void crazyDatabaseDelection(){
        userDB.deleteAllData();
    }



    //When called upon, will create a brand new arraylist of skills associated with that user
    //Primarily used when there is a new user that was not previously detected.
    public static void initializeArrayListForSkills(){
        userDB.getLocal().getLocalData().setSkillz(new ArrayList<Skill>());

    }


    //When we have a new user... we call upon the controller here to interact with the database
    //in order to create a brand new user. Returns this brand new user!
    public static User createNewUser(String usernameGiven, String emailGiven){
        User new_guy = null;

        try {
            new_guy = userDB.createUser(usernameGiven);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        new_guy.getProfile().setEmail(emailGiven);
        userDB.save();

        return new_guy;
    }

    public static void save(){
        userDB.save();
    }

    public void makeNewSkill(String name, String category, String description){
        userDB.getCurrentUser().getInventory().add(new Skill(userDB, name, category, description));
        save();
    }



}
