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


import android.test.ActivityInstrumentationTestCase2;

public class SkillTest extends ActivityInstrumentationTestCase2{

    public SkillTest() {
        super(com.skilltradiez.skilltraderz.SkillTest.class);
    }

    public void testSetVisibility() {
        Skill skill = new Skill("foo", "bar");
        skill.setVisible(false);
        assertFalse(skill.isVisible());
    }
    public void testSetDescription() {
        Skill skill = new Skill("foo", "bar");
        skill.setDescription("DESCRIBE'D!!!");
        assertEquals(skill.getDescription(), "DESCRIBE'D!!!");
    }

    public void testAttachPicture() {
        Skill skill = new Skill("foo", "bar");
        Image img = new Image("foo.tiff");
        //image should be null initially
        assertTrue(skill.getImage() instanceof NullImage);
        //test setting image
        skill.setImage(img);
        assertEquals(skill.getImage(), img);
    }

    public void testRetakePicture() {
        Skill skill = new Skill("foo", "bar");
        Image img = new Image("foo.tiff");
        Image img2 = new Image("foo.bmp");
        //test setting image
        skill.setImage(img);
        assertEquals(skill.getImage(), img);
        //test changing(retaking) image
        skill.setImage(img2);
        assertEquals(skill.getImage(), img2);
    }

    public void testDeletePicture() {
        Skill skill = new Skill("foo", "bar");
        Image img = new Image("foo.tiff");
        //test setting image
        skill.setImage(img);
        assertEquals(skill.getImage(), img);
        //test deleting image
        skill.deleteImage();
        assertTrue(skill.getImage() instanceof NullImage);
    }
}
