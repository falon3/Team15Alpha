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
/**
 * A skill represents something that a person can do.
 */
public class Skill implements Notification {
    private String name;
    private String category;
    private Image image;
    private boolean visible;
    private String description;

    Skill(String skill_name) {
        setName(skill_name);
        setCategory("Misc.");
        setVisible(true);//Default is visible
        setDescription("");//Empty String
        setImage(new NullImage());
    }

    Skill(String skill_name, String category) {
        setName(skill_name);
        setCategory(category);
        setVisible(true);//Default is visible
        setDescription("");//Empty String
        setImage(new NullImage());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void deleteImage() {
        setImage(new NullImage());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void commit(UserDatabase userDB) {
        //TODO
    }
}
