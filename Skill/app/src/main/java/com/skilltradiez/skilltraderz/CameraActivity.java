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

    /**Class variables
     * 1: lastImage, a Bitmap Object variable holding the last image utilized in the application.
     * 2: images, a list of Image Objects holding all of the images relevant to the application.
     */
    private Bitmap lastImage;
    private List<Image> images;
    private Runnable addImageCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        images = new ArrayList<Image>();
    }

    /**
     * Will invoke the method to start taking an image.
     *
     * @param view View Object. (UI related.)
     */
    public void addNewImage(View view) {
        startTakingImage();
    }

    /**
     * Remove the image passed into the method from the images file
     * @param view View Object. (UI related.)
     * @param toBeRemoved Image Object.
     */
    public void deleteImage(View view, Image toBeRemoved) {
        images.remove(toBeRemoved);
    }

    /**
     * If we already have an image, delete the old image and invoke method to take a new one.
     * @param view View Object.
     * @param toBeRemoved Image Object.
     */
    public void retakeImage(View view, Image toBeRemoved) {
        deleteImage(view, toBeRemoved);

        addNewImage(view);
    }

    /**
     * When invoked will start a new activity involving taking a picture with the device's camera.
     * @return Bitmap Object.
     */
    public void startTakingImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void setAddImageCallback(Runnable runnable) {
        this.addImageCallback = runnable;
    }

    /**
     * Method will take a new image and add the image to the images through the database controller
     * and the images.add method.
     * @param requestCode Integer.
     * @param resultCode Integer.
     * @param data Intent Object. (UI Related.)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            lastImage = (Bitmap) extras.get("data");

            Image image = new Image(lastImage);
            DatabaseController.addImage(image);
            images.add(image);

            addImageCallback.run();
        }
    }

    /**
     * Returns the last image
     * @return Bitmap Object.
     */
    public Bitmap getLastImage() {
        return lastImage;
    }

    /**
     * Will assign the (class local) images variable to the passed in image list parameter.
     * @param images List of Image Objects.
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Return the (class local) images variable.
     * @return List of Image Objects.
     */
    public List<Image> getImages() {
        return images;
    }

}
