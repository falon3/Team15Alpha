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

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

public class UserInventoryTests extends ActivityInstrumentationTestCase2 {
    public UserInventoryTests() {
        super(com.skilltradiez.skilltraderz.Inventory.class);
    }

    public void testAddSkill() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new ArrayList<Image>());

        // Testing adding a skill to inventory
        inv.add(skill);
        // test for getting skill from inventory as well
        assertTrue(inv.get(db, 0).equals(skill));
    }

    public void testRemoveSkill() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new ArrayList<Image>());
        inv.add(skill);

        // Testing removing a skill from inventory
        inv.remove(skill.getSkillID());
        assertTrue(inv.size()==0);
    }


    public void testSetSkillProperties() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new ArrayList<Image>());
        inv.add(skill);

        // Testing modifying a skill in inventory
        Image dog = new Image(Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8));
        DatabaseController.addImage(dog);
        Skill held_skill = inv.get(db, 0);
        held_skill.setDescription("I jumped and then got really tired");
        held_skill.addImage(dog);
        held_skill.setVisible(false);
        assertEquals(dog, held_skill.getImage());
        assertTrue(held_skill.getDescription().equals("I jumped and then got really tired"));
        assertTrue(!held_skill.isVisible());
    }

    public void testSkillSorting() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new ArrayList<Image>());
        Skill skill2 = new Skill(db, "Skill Naem", "dategory", "desc", true, new ArrayList<Image>());
        ArrayList<Skill> list1 = new ArrayList<Skill>();

        list1.add(skill);
        list1.add(skill2);

        inv.add(skill2);
        inv.add(skill);

        assertEquals(inv.orderByCategory(), list1);
    }

    public void testSearchSkills() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new ArrayList<Image>());
        Skill skill2 = new Skill(db, "Skil Naem", "dategory", "desc", true, new ArrayList<Image>());
        ArrayList<Skill> list1 = new ArrayList<Skill>();
        ArrayList<Skill> list2 = new ArrayList<Skill>();

        list1.add(skill);
        list2.add(skill2);

        inv.add(skill);
        inv.add(skill2);

        assertEquals(inv.findByName("Name"), list1);
        assertEquals(inv.findByName("Naem"), list2);
        assertEquals(inv.findByCategory("category"), list1);
        assertEquals(inv.findByCategory("dategory"), list2);
        list1.add(skill2);
        assertEquals(inv.findByCategory("gory"), list1);
    }
}
