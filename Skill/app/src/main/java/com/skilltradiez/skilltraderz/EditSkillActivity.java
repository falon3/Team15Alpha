package com.skilltradiez.skilltraderz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;

public class EditSkillActivity extends ActionBarActivity {

    private EditText skillName;
    private EditText skillDescription;
    private EditText skillCategory;
    private Button addSkillToDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skill);
    }

    public void onStart(){
        super.onStart();
        skillName = (EditText) findViewById(R.id.new_skill_name);
        skillDescription = (EditText) findViewById(R.id.new_skill_description);
        skillCategory = (EditText) findViewById(R.id.new_category);
        addSkillToDB = (Button) findViewById(R.id.add_skill_to_database);

    }

    /**
     * add skill to the database
     * @ TODO:
     */
    public void addSkill(){

    }

}
