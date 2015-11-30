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

/** ~~DESCRIPTION:
 * A null image is going to be the default image that we utilize in this application.
 * We cannot exactly utilize a "null" in the way that a null will undoubtebly cause cataclysmic
 * errors, bugs, and other joyful stuff that just... is... totally... NOT joyful.
 * AND THUS THIS CLASS WAS BORN!
 *
 * Various other classes in this application will make a call to generate one of these objects
 * in order to actually have an object that will fill the void (hah) that the application would
 * otherwise have if the user didn't specify a particular image to be utilized!
 *
 * This class extends the Image superclass and thus is expected to have the full functionality
 * that is inline with the image class.
 */

public class NullImage extends Image {

    /**
     * The constructor for this assigns a basic default image to the "bitmap" variable which is
     * representative of the image. It is not actually "null". It is our default.
     */
    public NullImage() {
        super();
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        setBitmap(bitmap);
    }
}
