package com.skilltradiez.skilltraderz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

public class ImageViewerActivity extends GeneralMenuActivity {
    static String IMAGE_ID_PARAM = "image_id";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        imageView = (ImageView) findViewById(R.id.imageView);

        Image image = DatabaseController.getImageByID((ID) getIntent().getExtras().get(IMAGE_ID_PARAM));
        imageView.setImageBitmap(image.getBitmap());
    }

}
