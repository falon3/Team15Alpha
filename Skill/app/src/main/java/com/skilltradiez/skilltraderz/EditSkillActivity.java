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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Our application is based off of the notion of facilitating the entire process of one user
 * being able to offer another user a skill. They make a trade from one user to another user
 * through our application.
 *
 * That being said, how possibly could the ability to edit a skill (THE thing offered during trades)
 * not be one of our core functionalities?!
 *
 * This class will be all about editing the skills that users offer, storing relevant values into
 * a swath of attributes and then giving other classes the methods to interact with this!
 */

public class EditSkillActivity extends CameraActivity {
    /**Activity Class Variables:
     * 1: ID_PARAM, used to identify the particular ID in string format.
     * 2: skillToEdit, holds a Skill Object that will be edited by the user.
     * 3: imageList, holds a list of Image Objects in a List View. (UI)
     * 4: skillName, EditText Object that will display the name of the skill. (UI)
     * 5: skillDescrption, EditText Object that will display the description of the skill. (UI)
     * 6: skillCategory, Spinner Object displaying the various categories the user may see. (UI)
     * 7: skillVisible, Button Object that will let the user click it to make their skill visible
     *     or not. (UI)
     * 8: addSkillToDB, Button Object that when clicked will add the skill to the database. (UI)
     * 9: imageAdapter, will adapt the image to the application's screen. (UI)
     * 10: adapter, will be involved in making many CharSequence objects relate to the UI. (UI)
     */
    static String ID_PARAM = "skill_id";
    private Skill skillToEdit;

    private ListView imageList;
    private EditText skillName, skillDescription;
    private RadioGroup radioGroup;
    private RadioButton epic, great, okay, mediocre, poor;
    private Spinner skillCategory;
    private CheckBox skillVisible;
    private Button addSkillToDB;
    private ImageAdapter imageAdapter;
    private SpinnerAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Instantiate the critical MasterController object. Mandatory for application function.
        masterController = new MasterController();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skill);

        skillName = (EditText) findViewById(R.id.new_skill_name);
        skillDescription = (EditText) findViewById(R.id.new_skill_description);
        addSkillToDB = (Button) findViewById(R.id.add_skill_to_database);
        skillCategory = (Spinner) findViewById(R.id.category_spinner);
        skillVisible = (CheckBox) findViewById(R.id.is_visible);
        imageList = (ListView) findViewById(R.id.imageList);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        epic = (RadioButton) findViewById(R.id.radioButton);
        great = (RadioButton) findViewById(R.id.radioButton2);
        okay = (RadioButton) findViewById(R.id.radioButton3);
        mediocre = (RadioButton) findViewById(R.id.radioButton4);
        poor = (RadioButton) findViewById(R.id.radioButton5);
        // Images Setup
        imageAdapter = new ImageAdapter(this, getImages());
        imageList.setAdapter(imageAdapter);

        //Custom Spinner Adapter
        CharSequence[] categories = getResources().getStringArray(R.array.category_spinner_strings);
        adapter = new SpinnerAdapter<CharSequence>(this, categories);
        //ArrayAdapter.createFromResource(this,
                //R.array.category_spinner_strings, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillCategory.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        setAddImageCallback(new Runnable() {
            @Override
            public void run() {
                imageAdapter.notifyDataSetChanged();
            }
        });
        // We need to be able to edit an existing skill
        if (getIntent().hasExtra(ID_PARAM)) {
            skillToEdit = DatabaseController.getSkillByID((ID) getIntent().getExtras().get(ID_PARAM));
            skillName.setText(skillToEdit.getName());
            skillDescription.setText(skillToEdit.getDescription());
            skillCategory.setSelection(adapter.getPosition(skillToEdit.getCategory()));
            addImages(skillToEdit.getImages());
            skillVisible.setChecked(skillToEdit.isVisible());
            setQuality(skillToEdit.getQuality());
            addSkillToDB.setText("Save changes");
        }
        imageAdapter.notifyDataSetChanged();
    }


    /**
     * Notifies the imageAdapter that there is a new image to display.
     * @param menu View Object. (UI related.)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);

        //disable add skill button from menu bar when already in edit skill activity
        MenuItem item = menu.findItem(R.id.Go_Make_Skill);
        item.setEnabled(false);
        return true;
    }

    /**
     * When invoked, will initialize all of the Skill elements to a default state.
     *
     * Set the text of Skill name to be empty.
     * Set the description of the Skill to be empty.
     * Make the Skill category 0.
     * Make the Skill visible.
     * Clear the images from the Skill.
     * Notify the adapter tha the Skill data has changed.
     */
    public void initState() {
        skillName.setText("");
        skillDescription.setText("");
        skillCategory.setSelection(0);
        skillVisible.setChecked(true);
        setQuality("Good");
        getImages().clear();
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * When this method is invoked we will use the passed in view to identify the image, and then
     * we will pass this image into the database.
     *
     * The View here will be able to distinguish a given Image, that is why a View is passed.
     * This view will then be passed to the superclass (CameraActivity) method of addNewImage.
     * We then notify the adapter that there has been a change to the data set.
     *
     * @param view View Object. (UI related.)
     */
    @Override
    public void addNewImage(View view) {
        super.addNewImage(view);
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * Removes a supplied Image Object and then notifies the imageAdapter of the change.
     * @param view View Object. (UI related.)
     * @param toBeRemoved Image Object.
     */
    @Override
    public void deleteImage(View view, Image toBeRemoved) {
        super.deleteImage(view, toBeRemoved);
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * When invoked, will remove the current image and invoke the retakeImage method and notify adapter.
     * @param view View Object.
     * @param toBeRemoved Image Object.
     */
    @Override
    public void retakeImage(View view, Image toBeRemoved) {
        super.retakeImage(view, toBeRemoved);
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * Return the skillName EditText from the application UI.
     * @return EditText Object. (UI)
     */
    public EditText getSkillName() {
        return skillName;
    }

    /**
     * Returns the skillDescription EditText from the application UI.
     * @return EditText Object. (UI)
     */
    public EditText getSkillDescription() {
        return skillDescription;
    }

    /**
     * Returns the skillCategory Spinner from the application UI.
     * @return Spinner Object. (UI)
     */
    public Spinner getSkillCategory() {
        return skillCategory;
    }

    /**
     * Returns the addSkillToDB Button from the application UI.
     * @return Button Object. (UI)
     */
    public Button getAddSkillToDB() {
        return addSkillToDB;
    }

    /**
     * Returns the skillVisible CheckBox from the application UI.
     * @return Checkbox Object. (UI)
     */
    public CheckBox getSkillVisible() {
        return skillVisible;
    }

    public String getQuality() {
        // If one is checked, then the id will be > -1
        if (radioGroup.getCheckedRadioButtonId() > -1) {
            if (epic.isChecked()) {
                return "EPIC";
            } else if (great.isChecked()) {
                return "Great";
            } else if (okay.isChecked()) {
                return "Okay";
            } else if (mediocre.isChecked()) {
                return "Mediocre";
            } else if (poor.isChecked()) {
                return "Poor";
            }
        }
        return null;
    }

    public void setQuality(String quality) {
        if (quality.equals("EPIC"))
            epic.setChecked(true);
        else if (quality.equals("Great"))
            great.setChecked(true);
        else if (quality.equals("Okay"))
            okay.setChecked(true);
        else if (quality.equals("Mediocre"))
            mediocre.setChecked(true);
        else if (quality.equals("Poor"))
            poor.setChecked(true);
    }

    /**
     * Will add a new skill to the model, however beneath the hood involves many controller
     * method calls, alerts and UI element changes.
     * @param view View Object.
     */
    public void addNewSkill(View view){
        String name, description, category, quality;
        boolean isVisible = skillVisible.isChecked();

        name = skillName.getText().toString();
        description = skillDescription.getText().toString();
        category = skillCategory.getSelectedItem().toString();
        quality = getQuality();

        if (name.length() == 0 || description.length() == 0 || quality == null) {
            // this makes a pop-up alert with a dismiss button.
            // source credit: http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please make sure all fields are filled!\n");
            alert.setCancelable(true);
            alert.setPositiveButton("retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog toolong_alert = alert.create();
            toolong_alert.show();
            return;
        }

        Context context = getApplicationContext();
        if (skillToEdit == null) { // if we are creating a new skill
            //Make a new skill through the controller.
            masterController.makeNewSkill(name, category, description, quality, isVisible, getImages());
            DatabaseController.save();

            //Toasty
            Toast.makeText(context, "You made a skill!", Toast.LENGTH_SHORT).show();

            initState();
        } else { // if we are editing an existing skill
            skillToEdit.setName(name);
            skillToEdit.setDescription(description);
            skillToEdit.setCategory(category);
            skillToEdit.setQuality(quality);
            skillToEdit.setImages(getImages());
            skillToEdit.setVisible(isVisible);
            skillToEdit.setImages(getImages());
            DatabaseController.save();

            Toast.makeText(context, "Skill saved!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
