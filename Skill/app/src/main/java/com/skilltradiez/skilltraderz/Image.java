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

/**~~DESCRIPTION:
 * So our program is core on trades, and profiles and users. Users have profiles and there is a
 * very clear way to make their profiles and their offers (of skills) more lucrative, exciting,
 * cool and overall appleaing...
 *
 * Colorful images! We're going to actually grant the user the ability to actually go through
 * their application and select an image (or upload a new one!) where they will be making an
 * image class.
 *
 * ~~ACCESS:
 * This is a public class and can be instantiated anywhere by any class anywhere in the application
 *
 * ~~CONSTRUCTORS:
 * This class has a constructor associated with itself where it takes in a string of a particular
 * file to open and then will process this string of the file and maintain the image in itself.
 * Glorious.
 *
 * ~~ATTRIBUTES/METHODS:
 *     This class has no attributes or methods associated with it aside from the constructor.
 */

public class Image extends Notification {
    
    private Bitmap bitmap;
    private ID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return !(id != null ? !id.equals(image.id) : image.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Image() {
        id = ID.generateRandomID();
    }
    public Image(Bitmap bitmap) {
        id = ID.generateRandomID();
        this.bitmap = bitmap;
    }
    public Image(Bitmap bitmap, ID id) {
        this.id = id;
        this.bitmap = bitmap;
    }
    public Image(String filename) {
        //LOL
        throw new NullPointerException("this can't possibly work!");
    }
    public ID getID() {
        return id;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyDB();
    }
    @Override
    boolean commit(UserDatabase userDB) {
        System.out.println("IMAGE COMMIT");
        http://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.URL_SAFE);
            encoded = encoded.replace("\n", "");
            userDB.getElastic().addDocument("image", id.toString(), new Imageb64(encoded));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    class Imageb64 {
        public Imageb64(String data) {
            this.data = data;
        }
        String data;
    }
}
