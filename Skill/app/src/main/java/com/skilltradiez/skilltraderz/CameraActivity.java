package com.skilltradiez.skilltraderz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 2015-11-27.
 */
public class CameraActivity extends GeneralMenuActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap lastImage;
    private List<Image> images;

    private Runnable imageResultAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        images = new ArrayList<Image>();
    }

    //TODO 3 methods BELOW
    public void addNewImage(View view) {
        startTakingImage();

        Image image = new Image(lastImage);
        DatabaseController.addImage(image);
        images.add(image);

        /*imageResultAction = new Runnable() {
            @Override
            public void run() {
                Button delete = new Button(generalContext);
                delete.setText("delete");
                Button retake = new Button(generalContext);
                retake.setText("retake");
                ImageView iv = new ImageView(generalContext);
                iv.setImageBitmap(lastImage);
                GridLayout gl = new GridLayout(generalContext);
                gl.addView(delete);
                gl.addView(retake);
                gl.addView(iv);
                imageLayout.addView(gl);

                Image image = new Image(lastImage);
                DatabaseController.addImage(image);
                if (skillToEdit == null) {
                    images.add(image);
                } else {
                    skillToEdit.addImage(image);
                }
            }
        };*/
    }

    public void deleteImage(View view, Image toBeRemoved) {
        images.remove(toBeRemoved);
    }

    public void retakeImage(View view, Image toBeRemoved) {
        deleteImage(view, toBeRemoved);

        addNewImage(view);
    }

    public Bitmap startTakingImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        return lastImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            lastImage = (Bitmap) extras.get("data");
            imageResultAction.run();
        }
    }

    public Bitmap getLastImage() {
        return lastImage;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }
}
