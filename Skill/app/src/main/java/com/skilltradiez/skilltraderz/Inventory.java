package com.skilltradiez.skilltraderz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An inventory contains the the skills held by a user.
 */
public class Inventory {
    private ArrayList<Skill> skillz;

    /**
     * Gets a skill at a given index
     * @param index the index
     * @return the skill
     */
    public Skill get(Integer index) {
        if (index < skillz.size())
            return skillz.get(index);
        return null;
    }

    /**
     * Adds a new skill to the end of the list
     * @param new_skill the skill to add.
     * @return false if the skill was already in the list
     */
    public Boolean add(Skill new_skill) {
        if (skillz.contains(new_skill)) return false;
        skillz.add(new_skill);
        return true;
    }

    /**
     * Removes a skill from the list
     * @param skill the skill to remove
     */
    public void remove(Skill skill) {
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
     * @param name The skill name to search for
     * @return a list of all skills matching the given name.
     */
    public List<Skill> findByName(String name) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        for (Skill s : skillz) {
            if (s.getName().equals(name)) {
                matching.add(s);
            }
        }
        return matching;
    }

    /**
    * Finds all skills with a particular category.
    * @param category The skill category to search for
    * @return a list of all skills matching the given category.
    */
    public List<Skill> findByCategory(String category) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        for (Skill s : skillz) {
            if (s.getCategory().equals(category)) {
                matching.add(s);
            }
        }
        return matching;
    }

    /**
     * Returns a copy of the list of skills, sorted ascending by name.
     */
    public ArrayList<Skill> orderByName() {
        ArrayList<Skill> sorted = (ArrayList<Skill>) skillz.clone();
        Collections.sort(skillz, new Comparator<Skill>() {
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
    public ArrayList<Skill> orderByCategory() {
        ArrayList<Skill> sorted = (ArrayList<Skill>) skillz.clone();
        Collections.sort(skillz, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        return sorted;
    }
}
