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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An inventory contains the the skills held by a user.
 */
public class Inventory {
    private ArrayList<ID> skillz;

    public Inventory() {
        skillz = new ArrayList<ID>();
    }

    /**
     * Gets a skill at a given index
     *
     * @param index the index
     * @return the skill
     */
    public Skill get(UserDatabase userDB, Integer index) {
        if (index < skillz.size())
            return userDB.getSkillByID(skillz.get(index));
        return null;
    }

    public ID get(Integer index) {
        if (index < skillz.size())
            return skillz.get(index);
        return null;
    }

    /**
     * Adds a new skill to the end of the list
     *
     * @param new_skill the skill to add.
     * @return false if the skill was already in the list
     */
    public Boolean add(Skill new_skill) {
        if (skillz.contains(new_skill.getSkillID())) return false;
        skillz.add(new_skill.getSkillID());
        return true;
    }

    /**
     * Removes a skill from the list
     *
     * @param skill the skill to remove
     */
    public void remove(ID skill) {
        skillz.remove(skill);
    }

    /**
     * Gets the number of skills on the list
     */
    public Integer size() {
        return skillz.size();
    }

    /**
     * Finds all skills with a particular name.
     *
     * @param name The skill name to search for
     * @return a list of all skills matching the given name.
     */
    public List<Skill> findByName(UserDatabase userDB, String name) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        Skill temp;
        for (ID s : skillz) {
            temp = userDB.getSkillByID(s);
            if (temp.getName().contains(name)) {
                matching.add(temp);
            }
        }
        return matching;
    }

    /**
     * Finds all skills with a particular category.
     *
     * @param category The skill category to search for
     * @return a list of all skills matching the given category.
     */
    public List<Skill> findByCategory(UserDatabase userDB, String category) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        Skill temp;
        for (ID s : skillz) {
            temp = userDB.getSkillByID(s);
            if (temp.getCategory().contains(category)) {
                matching.add(temp);
            }
        }
        return matching;
    }

    /**
     * Returns a copy of the list of skills, sorted ascending by name.
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
     * Returns a copy of the list of skills, sorted ascending by name.
     */
    public ArrayList<Skill> orderByCategory(UserDatabase userDB) {
        ArrayList<Skill> sorted = cloneSkillz(userDB);
        Collections.sort(sorted, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        return sorted;
    }

    // TODO: Check if it's the same Skill, but a different version
    public Boolean hasSkill(Skill skill) {
        for (ID s:skillz)
            if (skill.getSkillID().equals(s))
                return true;
        return false;
    }

    public ArrayList<Skill> cloneSkillz(UserDatabase userDB) {
        ArrayList<Skill> newList = new ArrayList<Skill>();
        for (ID id:skillz)
            newList.add(userDB.getSkillByID(id));
        return newList;
    }
}
