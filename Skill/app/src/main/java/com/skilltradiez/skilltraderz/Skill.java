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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A skill represents something that a person can do. In this application we have skills as the
 * primary element surrounding the entire purpose of why a user would want to obtain this app.
 * Therefore it is critical that we store details on that particular skill in an easy to access,
 * easy to modify, easy to store and easy to retrieve way. However this class when instantiated
 * will provide us with an object (for a particular skill) with all possible desirable features.
 *
 * Implementation wise, a skill is a large set of attributes bundled together in a reasonable
 * (assumedly) fashion. We maintain proper encapsulation of these details through making these
 * attributes all private and giving getter/setter methods (very traditional).
 */
public class Skill extends Stringeable {
    private String name, category, description, quality;
    private List<ID> images;
    private boolean visible;
    private ID skillID = ID.generateRandomID();
    private ArrayList<ID> owners;

    /**
     * Creates a new skill with the parameters passed into this constructor.
     * @param db UserDatabase Object.
     * @param skill_name String input.
     * @param category String input.
     * @param description String input.
     * @param isVisible Boolean flag.
     * @param images List of Image Objects.
     */
    Skill(UserDatabase db, String skill_name, String category, String description, boolean isVisible, List<Image> images) {
        this(db, skill_name, category, description, "test quality", isVisible, images);
    }

    /**
     * Creates a new skill with the parameters passed into this constructor. Has more parameters
     * than the one above... in particular the quality parameter.
     * @param db UserDatabase Object.
     * @param skill_name String input.
     * @param category String input.
     * @param description String input.
     * @param quality String input.
     * @param isVisible Boolean flag.
     * @param images List of Image Objects.
     */
    Skill(UserDatabase db, String skill_name, String category, String description, String quality, boolean isVisible, List<Image> images) {
        setName(skill_name);
        owners = new ArrayList<ID>();
        owners.add(db.getCurrentUser().getUserID());
        setCategory(category);
        setVisible(isVisible);
        setDescription(description);
        setQuality(quality);
        this.images = new ArrayList<ID>();
        setImages(images);

        //TODO this probably shouldn't add itself to the database.
        //To fix this, you need to make sure that everywhere new Skill(...) is called it also adds
        //it to the database. this doesn't happen too many times in the actual app, but lots in tests.
        DatabaseController.addSkill(this);
    }

    /**
     * Reduced constructor to make a new Skill, takes in two parameters and uses them and defaults
     * to create a new Skill object.
     * @param db UserDatabase Objekt.
     * @param skill Skill Objekt
     */
    Skill(UserDatabase db, Skill skill) {
        setName(skill.getName());
        owners = new ArrayList<ID>();
        owners.add(db.getCurrentUser().getUserID());
        setCategory(skill.getCategory());
        setVisible(skill.isVisible());
        setDescription(skill.getDescription());
        setQuality(skill.getQuality());
        images = skill.getImages();

        //TODO this probably shouldn't add itself to the database.
        //To fix this, you need to make sure that everywhere new Skill(...) is called it also adds
        //it to the database. this doesn't happen too many times in the actual app, but lots in tests.
        DatabaseController.addSkill(this);
    }

    /**
     * This method takes in a List of Image Objects and will add the images to this Skill and then
     * notify the database of this change.
     * @param images List of Image Objects.
     */
    public void setImages(List<Image> images) {
        if (this.images == null) {
            this.images = new ArrayList<ID>();
        }
        this.images.clear();
        for (Image i : images) {
            this.images.add(i.getID());
        }
        notifyDB();
    }

    /**
     * Given an Image Object and an Integer as a position desired, this method will take the image
     * and will put it in the position supplied as a parameter.
     * @param image Image Object.
     * @param pos Integer Value.
     */
    public void setImage(Image image, int pos) {
        this.images.set(pos, image.getID());
    }

    /**
     * Basic getter method that when invoked will return the Image for the Skill Object in question.
     * @return Image Object.
     */
    @Override
    public Image getImage() {
        return (images == null || images.size() == 0) ? new NullImage() : DatabaseController.getImageByID(images.get(0));
    }

    /**
     * Basic getter method that will return the List of ID Objects for all images of the Skill.
     * @return List of ID Objects.
     */
    public List<ID> getImages() {
        return images;
    }

    /**
     * Adds a passed in Image Object to the Skill Object.
     * @param image Image Object.
     */
    public void addImage(Image image) {
        images.add(image.getID());
    }

    /**
     * Removes a passed in Image Object from the Skill Objekt.
     * @param image Image Object.
     */
    public void removeImage(Image image) {
        images.remove(image.getID());
    }

    /**
     * Basic getter method that returns a String of the name of the Skill Object.
     * @return String of the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Basic setter method that sets the passed in String parameter of the name to the Skill
     * Object having this method invoked upon it.
     * @param name String of the name.
     */
    public void setName(String name) {
        this.name = name;
        notifyDB();
    }

    /**
     * Basic getter method that gets the String format of the category of the Skill Object.
     * @return String of the category of the skill.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Basic setter method that sets the passed in String parameter of the category to the Skill
     * Object having this method invoked upon it.
     * @param category String input.
     */
    public void setCategory(String category) {
        this.category = category;
        notifyDB();
    }

    /**
     * Delection of an image. Replaces the image with a newly instantiated NullImage
     * object.
     * @param pos Integer of the position.
     */
    public void deleteImage(int pos) {
        images.remove(pos);
        notifyDB();
    }

    /**
     * Basic getter method to obtain the String of the Skill Object's description.
     * @return String of the Skill Object description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Basic setter method that will set the description of the Skill to the String parameter
     * passed into this method.
     * @param description String input.
     */
    public void setDescription(String description) {
        this.description = description;
        notifyDB();
    }

    /**
     * Basic setter method that will set the quality of the Skill to the String parameter
     * passed into this method.
     * @param quality String input.
     */
    public void setQuality(String quality) {
        this.quality = quality;
        notifyDB();
    }

    /**
     * Basic getter method that will get the quality of the Skill Object.
     * @return String of the Skill quality.
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Basic getter method that will get the number of owners offering this Skill in particular.
     * @return Integer value of the number of owners of the skill.
     */
    public int getTop() {
        return getNumOwners();
    }

    /**
     * This is prettymuch just a getter method, returning the value of the visible boolean.
     * @return Boolean. True/False.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * This is a basic setter method, it will change the visibility of the Skill to the
     * Boolean value passed into the method as a parameter.
     * @param visible Boolean. True/False.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyDB();
    }

    /**
     * Basic getter method that returns the ID of the Skill Object in question.
     * @return ID Object.
     */
    public ID getSkillID() {
        return skillID;
    }

    /**
     * Special String method that returns the information of this skill as a String: including
     * the name, category, description and if it is visible or not.
     * @return String information for the Skill.
     */
    @Override
    public String toString() {
        return this.getName() + ": " + this.getCategory() + " " + this.getDescription() + (isVisible() ? "" : " (Invisible)");
    }

    /**
     * This method, once invoked, will commit all of the changes made to Skills to the database
     * for the application.
     * @param userDB UserDatabase Object.
     * @return Boolean. True/False.
     */
    public boolean commit(UserDatabase userDB) {
        Elastic ela = userDB.getElastic();
        Skill prev_version;
        ID owner = userDB.getCurrentUser().getUserID();
        System.out.println("SKILL COMMIT");
        try {
            prev_version = ela.getDocumentSkill(skillID.toString());
            if (prev_version == null)
                prev_version = this;
            if (prev_version.isOwner(owner) && !hasOwners()) {
                //if removed skill from inventory and no other owners had skill
                userDB.getSkills().remove(prev_version);
                DatabaseController.deleteDocumentSkill(skillID);
                System.out.println("Permanently deleted skill");

            } else if (prev_version.isOwner(owner) && !isOwner(owner)) {
                //if removed skill from inventory and other owners had skill
                prev_version.owners.remove(owner);
                ela.addDocument("skill", skillID.toString(), prev_version);
                System.out.println("Removed skill from one person's inventory");

            } else if (prev_version.isOwner(owner) && prev_version.getNumOwners() == 1) {
                // if only one owner so update skill
                DatabaseController.deleteDocumentSkill(skillID);
                ela.addDocument("skill", skillID.toString(), this);
                Set<Skill> skillz = MasterController.getUserDB().getSkills();
                skillz.remove(prev_version);
                skillz.add(this);
                System.out.println("Updated skill");

            } else if (prev_version.isOwner(owner) && prev_version.getNumOwners() > 1) {
                //if other users had and it was now updated then make new skill and remove self from old one
                Skill new_version = new Skill(userDB, this);
                prev_version.owners.remove(owner);
                ela.addDocument("skill", skillID.toString(), prev_version);
                ela.addDocument("skill", new_version.getSkillID().toString(), new_version);
                System.out.println("dated skill");
            } else {
                //TODO what if this happened??
                System.out.println("MAYBE BROKEN, FIXME!!!");
                // i think this happens when the user isn't a previous owner!
                throw new RuntimeException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Basic getter method that returns the number of owners of the Skill Object.
     * @return Integer of the number of owners of the Skill.
     */
    public int getNumOwners() {
        return owners.size();
    }

    /**
     * Adds the passed in ID Object to the list of owners of the Skill Object.
     * @param owner ID Object.
     */
    public void addOwner(ID owner) {
        owners.add(owner);
        notifyDB();
    }

    /**
     * Removes the passed in ID Object from the list of owners of the Skill Object.
     * @param owner ID Object.
     */
    public void removeOwner(ID owner) {
        owners.remove(owner);
        notifyDB();
    }

    /**
     * This method will return if the passed in ID Object (user ID) is within the list of
     * owners of the Object. If the user ID is in the list of owners it will return True, else
     * it will return False.
     * @param owner ID Object.
     * @return Boolean. True/False.
     */
    public boolean isOwner(ID owner) {
        return owners.contains(owner);
    }

    /**
     * This method will return if there are owners in the owners list (True) or if there aren't
     * owners in the Skill object's owners list (False).
     * @return Boolean. True/False.
     */
    public boolean hasOwners() {
        return !owners.isEmpty();
    }

    /**
     * Will take in an Object (any ol object...) of the Object type; and will then compare the
     * current object with the object passed into the method. If they are equal return true. If
     * they are not equal, then return false.
     * @param inputObject Object Object.
     * @return Boolean. True/False.
     */
    @Override
    public boolean equals(Object inputObject) {
        if (this == inputObject) return true;
        if (inputObject == null || getClass() != inputObject.getClass()) return false;

        Skill skill = (Skill) inputObject;

        return !(skillID != null ? !skillID.equals(skill.skillID) : skill.skillID != null);
    }

    /**
     * This method when called is going to give us a hash (horrendous hash function- I am deeply
     * offended) of the tradeID ID Object value. Allowing us to easily compare values.
     * Why is that? Because a hash will make comparison very easy and small.
     *
     * @return Integer. THIS integer represents the hash of the tradeID ID object.
     */
    @Override
    public int hashCode() {
        return skillID != null ? skillID.hashCode() : 0;
    }
}
