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
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * So our program is core on trades, and profiles and users. Users have profiles and there is a
 * very clear way to make their profiles and their offers (of skills) more lucrative, exciting,
 * cool and overall appleaing...
 *
 * Colorful images! We're going to actually grant the user the ability to actually go through
 * their application and select an image (or upload a new one!) where they will be making an
 * image class.
 */

public class Image extends Notification {

    /**LOCAL CLASS VARIABLES:
     * 1: bitmap, will hold a Bitmap Object that corresponds to the image that the Image Object
     *     holds.
     * 2: id, will hold an ID Object that will correspond to the identification number of this
     *     image Object.
     */
    private Bitmap bitmap;
    private ID id;

    /**CONSTRUCTORS: There are FOUR. **/

    /**
     * This constructor takes in no parameters and will merely generate a random novel ID.
     * (Pseudorandom, not truly random.) This pseudorandom will then be assigned to the value
     * of the identification number of this Object when created.
     *
     * Params: None.
     */
    public Image() {
        id = ID.generateRandomID();
    }


    /**
     * Will take in a Bitmap Object to be assigned as the image when this class is being
     * instantiated as an Image Object and then will
     *
     * @param bitmap Bitmap Object
     */
    public Image(Bitmap bitmap) {
        id = ID.generateRandomID();
        this.bitmap = bitmap;
    }

    /**
     * Will take in a Bitmap Object AND an ID Object and will create a new Image Object based upon
     * the image stored being the Bitmap Object given and the ID of the Image being the ID given.
     * @param bitmap Bitmap Object
     * @param id ID Object
     */
    public Image(Bitmap bitmap, ID id) {
        this.id = id;
        this.bitmap = bitmap;
    }

    /**
     * Will take in an input String being the filename and then will create a new Image Object
     * based upon the string given to this constructor.
     * @param filename String input.
     */
    public Image(String filename) {
        //LOL
        throw new NullPointerException("this can't possibly work!");
    }


    /**
     * When invoked will return the Image Object's identification number.
     * @return ID Object.
     */
    public ID getID() {
        return id;
    }

    /**
     * When invoked this will return the image associated with this particular Image Object.
     * @return Bitmap Object.
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Takes the Bitmap Object passed in as a parameter and sets the bitmap of this Image Object
     * to be the passed in Bitmap Object!
     * @param bitmap Bitmap Object.
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyDB();
    }

    /**
     *
     * @param userDB UserDatabase Object
     * @return Boolean. True/False.
     * @throws IOException
     */
    @Override
    boolean commit(UserDatabase userDB) {
        System.out.println("IMAGE COMMIT");
        http://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            encoded = encoded.replace("\n", "");
            userDB.getElastic().addDocument("image", id.toString(), new Imageb64(encoded));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This is a particular class embedded within this class. This Imageb64 class will be taking
     * in a String object of data and then it will be assigning it's data variable to be equal
     * to the data String passed into it's one method.
     */
    class Imageb64 {
        /**
         * This method is the only method of the Imageb64 class and will assign it's data to the
         * string of input given.
         * @param data String of input data.
         */
        public Imageb64(String data) {
            this.data = data;
        }
        String data;
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

        Image image = (Image) inputObject;

        return !(id != null ? !id.equals(image.id) : image.id != null);
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
        return id != null ? id.hashCode() : 0;
    }
}
