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
import java.util.Set;

/**
 *Following MVC styling to the absolute core, this is taking out of the UserDatabase
 * anything but the most bare minimal methods. We're maintaing the model as the model, and we're
 * making all the other functionality be housed here.
 *
 * This area will take care of anything and everything the rest of the program may possibly want
 * to do with the database.
 */
public final class DatabaseController {

    /** Class Variables:
     * 1: userDB, this is going to be the single core database object created for the entire
     * application. Anything that needs to access, write to, or otherwise in this application
     * will go through this controller which has exclusive access to this database object.
     */
    private static UserDatabase userDB;

    /**
     * Creates the DatabaseController object. In reality though the only class that will
     * (or ever should) create this is the master controller once the master controller is
     * instantiated.
     *
     * Parameters: None.
     * Return: An instance of the DatabaseController object.
     */
    DatabaseController() {
        userDB = new UserDatabase();
    }

    /** Methods **/
    /**
     * Returns the user database object, the one used for the entire application.
     * This method should not be called EVER outside of controllers to follow strict MVC methods.
     * ...Please... :(
     *
     * @return UserDatabase object, the core database of the application.
     */
    public UserDatabase getUserDB() {
        return userDB;
    }


    /**
     * Downloads all online data into a local cache
     * TODO: debug why local changes offline don't persist once back online... not sure if problem is here
     */


    /**
     * This method when invoked will take all of the current various parts of the model of
     * the application and put them into the database.
     * Upon failure, will return an IOException.
     * @throws IOException
     */
    public static void refresh() {
        save();
        Elastic elastic = MasterController.getUserDB().getElastic();
        User currentUser = MasterController.getUserDB().getCurrentUser();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        ChangeList changes = MasterController.getUserDB().getChangeList();

        try {
            List<User> onlineUsers = elastic.getAllUsers();
            for (User u : onlineUsers) {
                if (u.equals(currentUser)) MasterController.getUserDB().setCurrentUser(u);
                users.remove(u);
                users.add(u);
            }
            //TODO CATCH IOExceptions //Done?
            List<Skill> skills = elastic.getAllSkills();
            for (Skill s : skills) {
                skillz.remove(s);
                skillz.add(s);
                changes.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method when called will update the MasterController's model with the current data
     * within the application.
     */
    public static void save() {
        ChangeList toBePushed = MasterController.getUserDB().getChangeList();
        Local local = MasterController.getUserDB().getLocal();
        toBePushed.push(MasterController.getUserDB());
        User currentUser = MasterController.getUserDB().getCurrentUser();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Set<Trade> trades = MasterController.getUserDB().getTrades();

        local.saveToFile(currentUser, users, skillz, trades, toBePushed);
        if (MainActivity.connected) {
            toBePushed.push(MasterController.getUserDB());
        }
    }

    /**
     * Will morbidly destroy all data from the models within the application and elastic.
     * @throws IOException
     */
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
            elastic.deleteDocument("image", "");
            local.deleteFile();
            MasterController.getUserDB().setCurrentUserToNull();
            users.clear();
            skillz.clear();
            trades.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will take in a trade object and add it to the model in the application.
     * @param trade Trade Object.
     * @throws IOException
     */
    public static void addTrade(Trade trade) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        trades.add(trade);
        // New Trade
        MasterController.getUserDB().getChangeList().add(trade);
        try {
            MasterController.getUserDB().getElastic().addDocument("trade", trade.getTradeID().toString(), trade);
        } catch (IOException e) {
            //TODO this is bad and the user won't even know
            //actually, it's probably fine, it's on the changelist
            e.printStackTrace();
        }
    }

    /**
     * Will take in an image object and it will add the image object to the model of the application
     * @param image Image Object.
     */
    public static void addImage(Image image) {
        Set<Image> images = MasterController.getUserDB().getImagez();
        images.add(image);
        MasterController.getUserDB().getChangeList().add(image);
        image.notifyDB();
        save();
    }
    /**
     *When we have a new user... we call upon the controller here to interact with the database
     *in order to create a brand new user. Returns this brand new user!
     * @param usernameGiven String input.
     * @param emailGiven String input.
     * @return User Object
     */
    public static User createNewUser(String usernameGiven, String emailGiven) throws UserAlreadyExistsException {
        User new_guy = null;
        new_guy = createUser(usernameGiven);

        new_guy.getProfile().setEmail(emailGiven);
        DatabaseController.save();

        return new_guy;
    }

    /**
     * Given an ID object, will return the user where the ID matches.
     * @param id ID Object
     * @return User Object.
     */
    public static User getAccountByUserID(ID id) {
        Set<User> users = MasterController.getUserDB().getUsers();
        for (User u : users)
            if (u.getUserID().equals(id))
                return u;
        return null;
    }

    /**
     * This method when called is going to be involved in creating a new user and all tasks that are
     * involved with the creation of said user.
     * @param username String input
     * @return User Object.
     * @throws UserAlreadyExistsException
     * @throws IOException
     */
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

    /**
     * Create a new User object with the username given and return the User object.
     * @param username String input.
     * @return User Object.
     */
    public static User login(String username) {
        User u = getAccountByUsername(username);
        MasterController.getUserDB().setCurrentUser(u);
        return u;
    }

    /**
     * Returns a boolean stating TRUE-- the user is logged in. Or FALSE-- where the user is not
     * logged in.
     * @return Boolean. True/False.
     */
    public static boolean isLoggedIn() {
        User currentUser = MasterController.getUserDB().getCurrentUser();
        return currentUser != null;
    }

    /**
     * Returns a User object where the username passed in matches a User object.
     * This is paired with the getOnlineAccountByID in this class.
     * @param username String input.
     * @return User Object.
     */
    public static User getAccountByUsername(String username) {
        Set<User> users = MasterController.getUserDB().getUsers();
        for (User u : users)
            if (u.getProfile().getUsername().equals(username))
                return u;
        return getOnlineAccountByUsername(username);
    }

    /**
     *Returns a User object where the username passed to the method matches a User object.
     * This IS going to look at the elastic / online database information.
     * @param username String input.
     * @return User Object.
     * @throws IOException
     */
    private static User getOnlineAccountByUsername(String username){
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();
        User u = null;
        try {
            //System.out.println(username);
            u = elastic.getDocumentUser(username);
            if (u != null) users.add(u);
        } catch (IOException e) {
            System.err.println("Issue reading user from database." + e.getMessage());
        }

        return u;
    }


    /**
     * Returns a Trade object where the ID passed in matches a Trade object.
     * This is paired with the getOnlineTradeByID method in this class.
     * @param id ID Object.
     * @return Trade Object.
     */

    public static Trade getTradeByID(ID id) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        for (Trade t : trades)
            if (t.getTradeID().equals(id))
                return t;
        return getOnlineTradeByID(id);
    }

    /**
     * Returns a Trade object where the ID passed in matches a Trade object.
     * This IS GOING TO be looking at the elastic / online database information.
     * @param id ID Object.
     * @return Trade Object.
     * @throws IOException
     */
    private static Trade getOnlineTradeByID(ID id) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        Elastic elastic = MasterController.getUserDB().getElastic();
        Trade t = null;
        try {
            t = elastic.getDocumentTrade(id.toString());
            if (t != null)
                trades.add(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Returns a Skill object where the ID passed in matches a Skill object.
     * This is paired with the getOnlineSkillByID method in this class.
     * @param id ID Object.
     * @return ID Object.
     */
    public static Skill getSkillByID(ID id) {
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        for (Skill s : skillz)
            if (s.getSkillID().equals(id))
                return s;
        return getOnlineSkillByID(id);
    }


    /**
     * Returns a Skill object where the ID passed in matches a Skill object.
     * This is IS GOING TO be looking at the elastic / online database information.
     * @param id ID Object.
     * @return Skill Object.
     * @throws IOException
     */
    private static Skill getOnlineSkillByID(ID id) {
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Elastic elastic = MasterController.getUserDB().getElastic();
        Skill s = null;
        try {
            s = elastic.getDocumentSkill(id.toString());
            if (s != null) {
                skillz.add(s);
                MasterController.getUserDB().getChangeList().add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Returns an Image object where the ID passed in matches an Image object.
     * This is paired with the getOnlineImageByID method in this class.
     * @param id ID Object.
     * @return Image Object.
     */
    public static Image getImageByID(ID id) {
        Set<Image> images = MasterController.getUserDB().getImagez();
        for (Image i : images)
            if (i.getID().equals(id))
                return i;
        return getOnlineImageByID(id);
    }

    public static Image forceGetImageByID(ID id) {
        Set<Image> images = MasterController.getUserDB().getImagez();
        for (Image i : images)
            if (i.getID().equals(id))
                return i;
        Elastic elastic = MasterController.getUserDB().getElastic();
        Image i = null;
        try {
            i = elastic.getDocumentImage(id.toString());
            if (i != null) {
                images.add(i);
                MasterController.getUserDB().getChangeList().add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Returns an Image object where the ID passed in matches an Image object.
     * This IS GOING TO be looking at the elastic / online database information.
     * @param id ID Object.
     * @return Image Object.
     * @throws IOException
     */
    private static Image getOnlineImageByID(ID id) {
        if (!MasterController.getCurrentUser().getProfile().getShouldDownloadImages()) {
            return null;
        }
        return forceGetImageByID(id);
    }


    /**
     * Add a Skill object passed to this method to the model of this application.
     * @param skill Skill Object
     * @throws IOException
     */
    public static void addSkill(Skill skill) {
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        ChangeList changeList = MasterController.getUserDB().getChangeList();
        Elastic elastic = MasterController.getUserDB().getElastic();


        skillz.add(skill);

        // New Skill
        changeList.add(skill);
        try {
            elastic.addDocument("skill", skill.getSkillID().toString(), skill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Delete Document block of functions. These look WAY more controller-like than model **/
    /**
     * Deletes the given User object from the model of the application.
     * @param user User Object.
     */
    public static void deleteDocumentUser(User user) {
        deleteDocumentUser(user.getUserID());
    }

    /**
     * Deletes the given Skill object from the model of the application.
     * @param skill Skill Object
     */
    public static void deleteDocumentSkill(Skill skill) {
        deleteDocumentSkill(skill.getSkillID());
    }

    /**
     * Deletes the given Trade object from the model of the application.
     * @param trade Trade Object
     */
    public static void deleteDocumentTrade(Trade trade) {
        deleteDocumentTrade(trade.getTradeID());
    }

    /**
     * Deletes the given ID (USER ID!) from the model of the application.
     * Yes, if you look at the code it looks similar to the other delete document functions.
     * BUT there is a very critical string flag that is going to differentiate to the elastic
     * function on WHAT is being deleted.
     * @param userID ID Object for a particular USER.
     * @throws IOException
     */
    public static void deleteDocumentUser(ID userID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("user", userID.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the given ID (SKILL ID!) from the model of the application.
     * Yes, if you look at the code it looks similar to the other delete document functions.
     * BUT there is a very critical string flag that is going to differentiate to the elastic
     * function on WHAT is being deleted.
     * @param skillID ID object for a particular SKILL.
     * @throws IOException
     */
    public static void deleteDocumentSkill(ID skillID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("skill", skillID.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the given ID (TRADE ID!) from the model of the application.
     * Yes, if you look at the code it looks similar to the other delete document functions.
     * BUT there is a very critical string flag that is going to differentiate to the elastic
     * function on WHAT is being deleted.
     * @param tradeID ID Object for a particular TRADE.
     * @throws IOException
     */
    public static void deleteDocumentTrade(ID tradeID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("trade", tradeID.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method will update the elastic document paired with the user database with the
     * updated information passed into this method.
     * @param type Input string for a type, NOT an Object.
     * @param id Input string for an ID, NOT an Object.
     * @param data Elastic search requires the use of a T type.
     * @param path Input string for a path to be utilized by elastic. NOT an Object.
     * @param <T> Elastic Search requires a T type to be utilized.
     * @throws IOException
     */
    public static  <T> void  updateElasticDocument(String type, String id, T data, String path) throws IOException{
        userDB.getElastic().updateDocument(type, id, data, path);
    }
}
