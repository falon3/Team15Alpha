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
package com.skilltradiez.skilltraderz;

import java.io.IOException;

/**
 * ~~DESCRIPTION:
 * A skill represents something that a person can do. In this application we have skills as the
 * primary element surrounding the entire purpose of why a user would want to obtain this app.
 * Therefore it is critical that we store details on that particular skill in an easy to access,
 * easy to modify, easy to store and easy to retrieve way. However this class when instantiated
 * will provide us with an object (for a particular skill) with all possible desirable features.
 * <p/>
 * Implementation wise, a skill is a large set of attributes bundled together in a reasonable
 * (assumedly) fashion. We maintain proper encapsulation of these details through making these
 * attributes all private and giving getter/setter methods (very traditional).
 * <p/>
 * ~~ACCESS:
 * This class is public, allowing new instances to be instantiated outside of the program
 * by any other class. Further to the point, the constructors are public access; anything else
 * in this application may create a skill object.
 * <p/>
 * ~~CONSTRUCTORS:
 * This class has two constructors, in particular we are making use of constructor method
 * overloading. Meaning that the same method name to call the constructor is used to access
 * two different constructors.
 * <p/>
 * 1: The first constructor may be called with ONLY a name for the skill object to be created.
 * Likewise we will fashion the newly instantiated object with a plethora of defaults.:
 * -Category = Misc., by default we'll throw it into the Misc. category.
 * -Visibility = True (any user may see this object)
 * -Description = an empty string (Literally "").
 * -Image = a null image object, however we should damn well put a default here.
 * <p/>
 * In this constructor the only thing specified is the name of the skill.
 * <p/>
 * 2: The second constructor is activated (METHOD OVERLOADING) when provided with a string
 * representing the name of the skill AND a category string.
 * Defaults are created upon construction as follows:
 * -Visibility = True (any user may see this object)
 * -Description = an empty string (Literally "").
 * -Image = a null image object, however we should damn well put a default here.
 * <p/>
 * In this constructor the two things specified, skill name AND the skill category are maintained
 * by the object.
 * <p/>
 * ~~ATTRIBUTES/METHODS:
 * Note, all methods in this class are directly related to the attributes present in this object.
 * Therefore I will describe them paired together in an ordered fashion.
 * <p/>
 * 1: NAME.
 * This will be what the user inputs as the name of the skill. This is completely
 * arbitrary and up to the user to put in. Eg: If they wanted to call their skill
 * "master of hungry-hungry hippos" they by all means may do that. The database would accept it.
 * <p/>
 * WE PROVIDE THE METHODS TO:
 * -Get the name of the skill.  --getName
 * -Set the name of the skill.  --setName
 * <p/>
 * <p/>
 * 2: CATEGORY.
 * This is going to be what the user selects their skill falls under. Think about this as a more
 * rigid structure that the user does NOT get to arbitrarily choose. This is HARD CODED into
 * the application itself. This is given a unique identifier that the database also maintains
 * that gives it a unique grouping.
 * Eg: A user may NAME a skill "Cat bathing master of awesome" but they MUST choose between
 * our predefined hard coded categories. In this instance let's say we have a "Pets" category.
 * The user will choose their own defined skill to be within our defined "Pets" category!
 * WE PROVIDE THE METHODS TO:
 * -Get the name of the category        --getCategory
 * -Set the name of the category        --setCategory
 * <p/>
 * <p/>
 * 3: IMAGE.
 * What is life without images? It is terrible.
 * But more to the point, we will allow users to select and upload an image that is related to
 * their skill. This image is going to actually be shoved into the database itself. We will
 * of course provide some (admittedly hideous) default image if this is left blank, but we
 * give the user an option to provide us with an image.
 * WE PROVIDE THE METHODS TO:
 * -Get the image                       --getImage
 * -Set the image                       --setImage
 * -Delete the image! (Just incase!)    --deleteImage
 * <p/>
 * 4: VISIBLE.
 * This is going to be a user option where they can either set their skill to be VISIBLE on
 * their page or to NOT be VISIBLE on their page. This allows the user to either reveal or
 * to hide their skill.
 * WE PROVIDE THE METHODS TO:
 * -Get the current visibility of the skill  --isVisibile (***NOTE: NOT GET VISIBILITY!)
 * -Set the current visibility of the skill  --setVisibility
 * <p/>
 * <p/>
 * 5: DESCRIPTION.
 * The user has a set of skills. But what exactly seperates this user's "Cat Olympics" skill
 * vs. Mary-Jane-Sue-The-VIII's "Cat Olympics" skill? Why, of course, the description! This
 * is going to be a private string dedicated to letting the user fill out details of their
 * particular brand of (just an example) "Cat Olympics". Maybe Mary-Jane-Sue-The-VIII has
 * successfully trained 8 cats that won gold... this would be a perfect thing to put in
 * the description! You go Mary-Jane-Sue-The-VIII, you go girl!
 * WE PROVIDE THE METHODS TO:
 * -Get the description         --getDescription
 * -Set the description         --setDescription
 */
public class Skill implements Notification {

    private String name;
    private String category;
    private Image image;
    private boolean visible;
    private String description;
    private Integer version = 0;

    /**
     * CONSTRUCTORS
     **/
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


    /**
     * METHODS
     **/
    //Traditional getter and setter methods for the private attribute name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Traditional getter and setter methods for the private attribute getCategory
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //Traditional getter and setter methods for the private attribute image
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    //DELETION of an image method. Replaces the image with a newly instantiated NullImage
    //object within this line.
    public void deleteImage() {
        setImage(new NullImage());
    }

    //Traditional getter and setter methods for the private attribute description.
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //"Traditional" getter and setter methods for the private boolean attribute visible
    //Why "isVisible" isn't "getVisible" is... personal preference. It is a boolean so we'll
    //just go with that! But basically is a getter/setter method.
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void commit(UserDatabase userDB) {
        //TODO
        Elastic ela = userDB.getElastic();
        Skill prev_version;

        try {
            prev_version = ela.getDocumentSkill(name + "_" + version);

            //TODO make equals method
            if (!prev_version.equals(this)) {
                version = version + 1;
                ela.addDocument("skill", name + "_" + version, this);
            }
        } catch (IOException e) {
            //TODO Save Locally
            e.printStackTrace();
        }
    }
}
