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

        if (getAccountByUsername(username) != null)
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
        User u = getAccountByUsername(username);
        MasterController.getUserDB().setCurrentUser(u);
        return u;
    }



    public static boolean isLoggedIn() {
        User currentUser = MasterController.getUserDB().getCurrentUser();
        return currentUser != null;
    }


    public static User getAccountByUsername(String username) {
        Set<User> users = MasterController.getUserDB().getUsers();
        for (User u : users)
            if (u.getProfile().getUsername().equals(username))
                return u;
        return getOnlineAccountByUsername(username);
    }


    private static User getOnlineAccountByUsername(String username) {
        //TODO Maybe this should throw an exception instead of returning null.
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();
        User u = null;
        try {
            //System.out.println(username);
            u = elastic.getDocumentUser(username);
            if (u != null) users.add(u);
        } catch (IOException e) {
        }
        return u;
    }


    /** Delete Document block of functions. These look WAY more controller-like than model **/
    public static void deleteDocumentUser(User user) {
        deleteDocumentUser(user.getUserID().toString());
    }

    public static void deleteDocumentSkill(Skill skill) {
        deleteDocumentSkill(skill.getSkillID().toString());
    }

    public static void deleteDocumentTrade(Trade trade) {
        deleteDocumentTrade(trade.getTradeID().toString());
    }

    public static void deleteDocumentUser(String userID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("user", userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDocumentSkill(String skillID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("skill", skillID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDocumentTrade(String tradeID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("trade", tradeID);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
