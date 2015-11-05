package com.skilltradiez.skilltraderz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

public class SkillDescriptionActivity extends ActionBarActivity {

    private Button addSkill;
    private TextView skillTitle;
    private TextView skillDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);
    }

    @Override
    public void onStart(){
        super.onStart();

        addSkill = (Button) findViewById(R.id.add_remove_skill);
        skillTitle = (TextView) findViewById(R.id.skillTitle);
        skillDescription = (TextView) findViewById(R.id.skill_description);
    }

    /**
     * @ TODO:
     */
    public void setSkillTitle(){
        //skillTitle = title of the skill we're looking at
    }

    /**
     * @ TODO:
     */
    public void setSkillDescription(){
        //skillDecription = description of the skill we're looking at
    }

    /**
     * Adds or removes a skill from a user's list or trade request
     */
    public void addRemoveSkill(){
        //notify the user that the skill has been added to their profile or trade request depending
        //on what context we're given ie: trade request vs skill search
    }

}
