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

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire skill description process that the user will need to interact
 * with through our application.
 *
 * ~~ACCESS:
 * This may seem redundant but for formatting purposes... this is a "public" class, meaning that
 * we can have this class actually be accessed technically anywhere in the application that
 * calls it. But since this is an activity it may seem a bit strange to refer to instantiating
 * an instance of the "EditTradeActivity" object.
 *
 * Instead what is happening is that we are having this activity be called by the onCreate() method
 * as is traditionally done in the android studio framework for android applications. In this
 * instance we're going to create this activity and then we'll have an onstart() method following
 * this which is going to make it so that we have this activate a cascade of events that are all
 * interelated with the main primary goal of allowing us to have a screen where we edit the
 * trading activity!
 *
 *~~CONSTRUCTOR:
 * Upon calling the method onCreate() for this activity the android studio framework will
 * cause the android application to create an instance of this actvity and display it to the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: SETSKILLTITLE:
 *     This is more of a UI element but this will allow us to actually set the value of the title
 *     for this particular skill description for the particular application.
 * 2: SETSKILLDESCRIPTION:
 *     Suppose we want to have the user modify the skill description, through the UI this
 *     method will be invoked that will allow the user to actually set their skill description
 *     for the rest of the application.
 *
 * 3: ADDREMOVESKILL:
 *     This is going to be the entire process of adding or removing a skill from a skill description
 *     when we actually have the user interact with the UI to call this method that will
 *     allow them to add or remove a damn skill.
 */

public class SkillDescriptionActivity extends GeneralMenuActivity {
    static String ID_PARAM = "skill_id";

    private Skill currentSkill;
    private Button addRemoveSkill, editSkill;
    private TextView skillTitle, skillCategory, skillDescription;
    private Boolean hasSkill;
    private LinearLayout imageView;
    private Context context = this;

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

    @Override
    public void onStart(){
        super.onStart();
        refresh();
    }

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
            iv.setImageBitmap(image.getBitmap());
            imageView.addView(iv);
        }
    }

    public void setSkillTitle(String text){
        //skillTitle = title of the skill we're looking at
        skillTitle.setText(text);
    }

    public void setSkillCategory(String category) {
        //skillCategory = category of the skill we're looking at
        skillCategory.setText(category);
    }

    public void setSkillDescription(String text){
        //skillDescription = description of the skill we're looking at
        skillDescription.setText(text);
    }

    /**
     * Adds or removes a skill from a user's list or trade request
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

    public Button getAddRemoveSkill(){
        return addRemoveSkill;
    }

    public void editSkill(View view){
        Intent intent = new Intent(this, EditSkillActivity.class);
        intent.putExtra("skill_id", currentSkill.getSkillID());
        startActivity(intent);
    }

}
