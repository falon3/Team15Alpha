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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**~~DESCRIPTION:
 * This is going to be the class where we have the inventory and all of it's associated functions
 * that a user will have all clumped together into one coherent object.
 * This object will have a set of attributes that are related to the inventory such as an
 * arraylist of skills. (And that's it.... for now... dun dun dunnnnn!)
 * This object will be method centric, where most of the object will be dealing with manipulating
 * a truckload of functionality throughout the rest of the objects present within the application.
 *
 * An inventory contains the the skills held by a user.
 */


public class Inventory extends Notification {
    /**Class Variables:
     * 1: skillz, a list of ID Objects representing the Skillz in the inventory.
     * 2: user, an ID Object that represents the ID of the user this Inventory Object belongs to.
     */
    private ArrayList<ID> skillz;
    private ID user;

    /**
     * Given an ID Object of a User, create a new Inventory Object.
     * Assigns object's user variable to the passed in ID Object.
     * Creates a new ArrayList of ID Object's used for Skills in the inventory.
     * @param user ID Object of a User.
     */
    public Inventory(ID user) {
        skillz = new ArrayList<ID>();
        this.user = user;
    }

    /**
     * Given an index, provides the Skill Object associated with that index value.
     * Returns null if the index does not have an associated Skill Object.
     * @param userDB UserDatabase Object.
     * @param index Integer Object.
     * @return
     */
    public Skill get(UserDatabase userDB, Integer index) {
        if (index < skillz.size())
            return DatabaseController.getSkillByID(skillz.get(index));
        return null;
    }

    /**
     * Given an index, provides the ID Object of the skill associated with that index value.
     * Returns null if the index does not have an associated Skill Object.
     * @param index Integer Objcet.
     * @return ID Object.
     */
    public ID get(Integer index) {
        if (index < skillz.size())
            return skillz.get(index);
        return null;
    }

    /**
     * Given a Skill Object, adds the Skill Object to the inventory's list of Skill Objects.
     * @param new_skill Skill Object.
     * @return Boolean. True/False.
     */
    public Boolean add(Skill new_skill) {
        if (skillz.contains(new_skill.getSkillID())) return false;
        skillz.add(new_skill.getSkillID());
        notifyDB();
        return true;
    }


    /**
     * Remove from the Inventory's list of Skill Objects the skill associated with the given ID Object.
     * @param skill ID Object of a Skill.
     */
    public void remove(ID skill) {
        skillz.remove(skill);
        notifyDB();
    }

    /**
     * Returns an Integer Object of the size of the Inventory Object's list of Skill ID Objects.
     * @return Integer Object
     */
    public Integer size() {
        return skillz.size();
    }


    /**
     * Returns a list of Skill Objects associated with the name passed to the method.
     * @param userDB UserDatabase Object.
     * @param name String input of a name of a Skill.
     * @return A List of Skill Objects
     */
    public List<Skill> findByName(UserDatabase userDB, String name) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        Skill temp;
        for (ID s : skillz) {
            temp = DatabaseController.getSkillByID(s);
            if (temp.getName().toLowerCase().contains(name.toLowerCase()) && temp.isVisible()) matching.add(temp);
        }
        return matching;
    }

    /**
     * Given a category, yields a list of Skill Objects that are associated with the string given.
     * @param userDB UserDatabase Object.
     * @param category String input of a category.
     * @return List of Skill Objects
     */
    public List<Skill> findByCategory(UserDatabase userDB, String category) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        Skill temp;
        for (ID s : skillz) {
            temp = DatabaseController.getSkillByID(s);
            if (temp.getCategory().toLowerCase().contains(category.toLowerCase()) &&
                    (temp.isVisible() || DatabaseController.getAccountByUserID(user).getInventory().hasSkill(temp)))
                matching.add(temp);
        }
        return matching;
    }

    /**
     * When invoked will return a list of Skill Objects sorted by their name.
     * @param userDB UserDatabase Object.
     * @return List of Skill Objects.
     */
    public ArrayList<Skill> orderByName(UserDatabase userDB) {
        ArrayList<Skill> sorted = cloneSkillz(userDB);
        Collections.sort(sorted, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return sorted;
    }

    /**
     * Returns a copy of the list of skills, sorted ascending by category.
     * @param userDB UserDatabase Object.
     * @return List of Skill Objects.
     */
    public ArrayList<Skill> orderByCategory(UserDatabase userDB) {
        ArrayList<Skill> sorted = cloneSkillz(userDB);
        Collections.sort(sorted, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getCategory().toLowerCase().compareTo(rhs.getCategory().toLowerCase());
            }
        });
        return sorted;
    }

    /**
     * Pass in a Skill Object to this method to return TRUE if the Inventory has the skill or
     * FALSE if the inventory does not have that skill.
     * @param skill Skill Object.
     * @return Boolean. True/False.
     */
    public Boolean hasSkill(Skill skill) {
        for (ID s:skillz)
            if (skill.getSkillID().equals(s))
                return true;
        return false;
    }

    /**
     * When invoked will produce a brand new list of Skill Objects and return this new list.
     * @param userDB UserDatabase Object
     * @return List of Skill Objects.
     */
    public ArrayList<Skill> cloneSkillz(UserDatabase userDB) {
        ArrayList<Skill> newList = new ArrayList<Skill>();
        for (ID id:skillz)
            newList.add(DatabaseController.getSkillByID(id));
        return newList;
    }

    /**
     * When invoked will commit to the database all of the changes that have occured since the
     * last commit.
     *
     * Attempts to update the Elastic document for the inventory.
     * If this fails, we get an IO Exception and return false. Signalling failure.
     * If that was successful we move onto invoking the method to save to the local device
     * (As in a flash card in most phones.) the changes to the inventory.
     * If this fails we return false again- indicating failure.
     * If both of those methods go through though we return true to the caller of this method.
     *
     * @param userDB UserDatabase Object
     * @return Boolean. True/False.
     */
    @Override
    boolean commit(UserDatabase userDB) {
        System.out.println("Inventory commit!");
        try {
            userDB.getElastic().updateDocument("user",
                    DatabaseController.getAccountByUserID(user).getProfile().getUsername(), this, "inventory");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //Save Locally
        try {
            userDB.getLocal().saveToFile();
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    public String getType() {
        return "Your Inventory";
    }

    public String getStatus() {
        return "Changed";
    }

    public String getDescription() {
        return "Something was added or removed.";
    }
}
