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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire skill description process that the user will need to interact
 * with through our application.
 */

public class SkillDescriptionActivity extends GeneralMenuActivity {
    static String ID_PARAM = "skill_id";
    private Skill currentSkill;
    private Button addRemoveSkill, editSkill;
    private TextView skillTitle, skillCategory, skillDescription;
    private Boolean hasSkill;
    private LinearLayout imageView;
    private Context context = this;

    /**
     * Standard Activity Method invoked upon creation, handles UI element setup.
     * @param savedInstanceState Bundle Object.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);
        masterController = new MasterController();

        currentSkill = DatabaseController.getSkillByID((ID) getIntent().getExtras().get(ID_PARAM));

        addRemoveSkill = (Button) findViewById(R.id.add_remove_skill);
        skillDescription = (TextView) findViewById(R.id.skill_description);
        skillTitle = (TextView) findViewById(R.id.skillTitle);
        skillCategory = (TextView) findViewById(R.id.skillCategory);
        editSkill = (Button) findViewById(R.id.edit_skill);
        imageView = (LinearLayout) findViewById(R.id.imageView);
    }

    /**
     * Standard Android Activity Method invoked onStart, handles a call to the superclass's
     * onStart method and then refreshes.
     */
    @Override
    public void onStart(){
        super.onStart();
        refresh();
    }

    /**
     * This method is going to be involved in retrieving a large amount of data from the models
     * of the application for the Skills, Users and Inventories- and set up the UI in a reasonable
     * manner.
     */
    public void refresh() {
        setSkillTitle(currentSkill.getName());
        setSkillCategory(currentSkill.getCategory());
        setSkillDescription(currentSkill.getDescription());

        User user = masterController.getCurrentUser();
        Inventory inv = user.getInventory();

        hasSkill = inv.hasSkill(currentSkill);
        if (hasSkill) {
            addRemoveSkill.setText("Remove Skill");
            editSkill.setVisibility(View.VISIBLE);
        }
        // It's initially set to "Add Skill"

        imageView.removeAllViews();
        for (final ID id : currentSkill.getImages()) {
            Image image = DatabaseController.getImageByID(id);
            ImageView iv = new ImageView(this);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageViewerActivity.class);
                    intent.putExtra(ImageViewerActivity.IMAGE_ID_PARAM, id);
                    startActivity(intent);
                }
            });
            if (image != null)
                iv.setImageBitmap(image.getBitmap());
            else
                iv.setImageResource(R.drawable.sz_circle);
            imageView.addView(iv);
        }
    }

    /**
     * Basic setter method that will take the String passed as a parameter to this method and
     * assign it to the title.
     * @param text String input.
     */
    public void setSkillTitle(String text){
        //skillTitle = title of the skill we're looking at
        skillTitle.setText(text);
    }

    /**
     * Basic setter method that will take the String passed as a parameter to this method and
     * assign it to the category of the Skill.
     * @param category String input.
     */
    public void setSkillCategory(String category) {
        //skillCategory = category of the skill we're looking at
        skillCategory.setText(category);
    }

    /**
     * Basic setter method that will take the String passed as a parameter to this method and
     * assign it to the description of the Skill.
     * @param text
     */
    public void setSkillDescription(String text){
        //skillDescription = description of the skill we're looking at
        skillDescription.setText(text);
    }


    /**
     * Adds or removes a skill from a user's list or trade request.
     * @param v View Object.
     */
    public void addRemoveSkill(View v){
        //notify the user that the skill has been added to their profile or trade request depending
        //on what context we're given ie: trade request vs skill search
        if (hasSkill) {
            //Set text to the OPPOSITE of what is happening.
            addRemoveSkill.setText("Add Skill");
            editSkill.setVisibility(View.INVISIBLE);
            //Evoke controller to REMOVE skill.
            masterController.removeCurrentSkill(currentSkill);
        } else {
            //Set text yet again to the OPPOSITE of what the function is.
            addRemoveSkill.setText("Remove Skill");
            editSkill.setVisibility(View.VISIBLE);
            //Evoke controller to ADD skill.
            masterController.addCurrentSkill(currentSkill);
        }
        hasSkill = !hasSkill;
        currentSkill.notifyDB();
        DatabaseController.save();
    }

    /**
     * Returns the Button related to adding or removing a skill, involved in gathering information
     * from the UI to be used in the controllers.
     * @return Button Object.
     */
    public Button getAddRemoveSkill(){
        return addRemoveSkill;
    }

    /**
     * When invoked, this method will change the current activity over to the EditSkillActivity
     * Activity with the currentSkill being looked at in this activity.
     * @param view View Object.
     */
    public void editSkill(View view){
        Intent intent = new Intent(this, EditSkillActivity.class);
        intent.putExtra("skill_id", currentSkill.getSkillID());
        startActivity(intent);
    }

}
