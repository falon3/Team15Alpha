package com.skilltradiez.skilltraderz;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Cole on 2015-11-22.
 */
public final class CDatabaseController implements CControllerInterface{



    //This refresh method damn well belongs in the controller.
    /**
     * Downloads all online data into a local cache
     * TODO: save must be done before this or we might lose data
     */
    public static void refresh() {
        Elastic elastic = MasterController.getUserDB().getElastic();
        User currentUser = MasterController.getUserDB().getCurrentUser();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();

        try {
            List<User> onlineUsers = elastic.getAllUsers();
            for (User u : onlineUsers) {
                if (u.equals(currentUser)) MasterController.getUserDB().setCurrentUser(u);
                users.remove(u);
                users.add(u);
            }
            //TODO CATCH IOExceptions
            List<Skill> skills = elastic.getAllSkills();
            for (Skill s : skills) {
                skillz.remove(s);
                skillz.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static void save() {
        ChangeList toBePushed = MasterController.getUserDB().getChangeList();
        Local local = MasterController.getUserDB().getLocal();
        toBePushed.push(MasterController.getUserDB());
        User currentUser = MasterController.getUserDB().getCurrentUser();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Set<Trade> trades = MasterController.getUserDB().getTrades();


        // TODO: Saves locally and pushes changes if connected to the internet
        //local.saveToFile(currentUser, users, skillz, trades, toBePushed.getNotifications());
        local.saveToFile(currentUser, users, skillz, trades);
    }





    public static void deleteAllData() {
        ChangeList toBePushed = MasterController.getUserDB().getChangeList();
        Local local = MasterController.getUserDB().getLocal();
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        User currentUser = MasterController.getUserDB().getCurrentUser();


        try {
            elastic.deleteDocument("example", "");
            elastic.deleteDocument("user", "");
            elastic.deleteDocument("skill", "");
            elastic.deleteDocument("trade", "");
            local.deleteFile();
            MasterController.getUserDB().setCurrentUserToNull();
            users.clear();
            skillz.clear();
            trades.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //Add a trade
    public static void addTrade(Trade t) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        trades.add(t);
        // New Trade
        MasterController.getUserDB().getChangeList().add(t);
        try {
            MasterController.getUserDB().getElastic().addDocument("trade", t.getTradeID().toString(), t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    //When we have a new user... we call upon the controller here to interact with the database
    //in order to create a brand new user. Returns this brand new user!
    public static User createNewUser(String usernameGiven, String emailGiven){
        User new_guy = null;
        try {
            new_guy = createUser(usernameGiven);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        new_guy.getProfile().setEmail(emailGiven);
        CDatabaseController.save();

        return new_guy;
    }



    //Moved create user functionality to this new area
    public static User createUser(String username) throws UserAlreadyExistsException {
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();

        if (MasterController.getUserDB().getAccountByUsername(username) != null)
            throw new UserAlreadyExistsException();

        User u = new User(username);
        users.add(u);
        // You wouldn't be creating a user if you already had one
        MasterController.getUserDB().setCurrentUser(u);
        try {
            elastic.addDocument("user", username, u);
        } catch (IOException e) {
            // No internet, no registration
            throw new RuntimeException();
        }
        return u;
    }



    //Login functionality
    public static User login(String username) {
        User u = MasterController.getUserDB().getAccountByUsername(username);
        MasterController.getUserDB().setCurrentUser(u);
        return u;
    }



    public static boolean isLoggedIn() {
        User currentUser = MasterController.getUserDB().getCurrentUser();
        return currentUser != null;
    }

}
