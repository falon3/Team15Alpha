package com.skilltradiez.skilltraderz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

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
