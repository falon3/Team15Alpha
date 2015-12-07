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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * This Activity is purely dedicated to displaying an image through the User Interface so that
 * the user of the application can view the image. Simple as that.
 */

public class ImageViewerActivity extends GeneralMenuActivity {
    /**Class Variables:
     * 1: IMAGE_ID_PARAM: This String variable will be assigned the value of the image
     *     identification number.
     * 2: imageView: This variable holds an ImageView Object which will let us have this
     *     activity have a consistent view for the images.
     */
    static String IMAGE_ID_PARAM = "image_id";
    private ImageView imageView;

    /**
     * Normally I don't speak on onCreate methods in actvities, but this is the only method here.
     * This onCreate method will present an imageView object on the UI and through accessing the
     * database will obtain an Image object and then set the ImageView Object's bitmap value
     * to the image's bitmap value that was obtained from the database.
     * @param savedInstanceState Android Activity Standard
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        imageView = (ImageView) findViewById(R.id.imageView);

        Image image = DatabaseController.forceGetImageByID((ID) getIntent().getExtras().get(IMAGE_ID_PARAM));
        imageView.setImageBitmap(image.getBitmap());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
