package com.skilltradiez.skilltraderz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class Inventory {
    private List<Skill> skillz;

    public Skill get(Integer index) {
        if (index < skillz.size())
            return skillz.get(index);
        return null;
    }

    public Boolean add(Skill new_skill) {
        for (Skill s:skillz)
            if (s.equals(new_skill))
                return false;
        return skillz.add(new_skill);
    }

    public void remove(Skill skill) {
        skillz.remove(skill);
    }

    public Integer size() {
        return skillz.size();
    }

    public ArrayList<Skill> findByName(String name) {
        return null;
    }

    public ArrayList<Skill> findByCategory(String category) {
        return null;
    }

    public ArrayList<Skill> orderByName() {
        // A-Z
        return null;
    }

    public ArrayList<Skill> orderByCategory() {
        // A-Z
        return null;
    }
}
