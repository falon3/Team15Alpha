package com.skilltradiez.skilltraderz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;

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

public class MainActivity extends ActionBarActivity {

    private Context mainContext = this;

    public static UserDatabase userDB = new UserDatabase();

    //Main screen
    private Button searchButton;
    private Button searchAllSkillzButton;
    private Button searchAllUsersButton;
    private Button goToProfile;
    private EditText searchField;
    private String searchDatabase;

    //First time user screen
    private EditText newUserName;
    private EditText newUserEmail;
    private Button makeNewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO HACK
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(userDB.getCurrentUser() != null){
            setContentView(R.layout.activity_main);
        }else{
            setContentView(R.layout.first_time_user);
        }

        // main
        searchButton = (Button) findViewById(R.id.search_button);
        searchAllSkillzButton = (Button) findViewById(R.id.browse_skillz);
        searchAllUsersButton = (Button) findViewById(R.id.browse_users);
        goToProfile = (Button) findViewById(R.id.go_to_profile);
        searchField = (EditText) findViewById(R.id.search_bar);

        // first_tine
        newUserName = (EditText) findViewById(R.id.makeUserName);
        newUserEmail = (EditText) findViewById(R.id.emailField);
        makeNewUser = (Button) findViewById(R.id.beginApp);

        searchDatabase = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * onClick listeners and buttons go here
     */
    @Override
    public void onStart(){
        super.onStart();
    }

    /**
     * Make a thread with a string. Used to search the database for a specific user or skill.
     * @param
     */
    public void makeSearchThread(String stringToSearch){
        /*
        Like in the lab Get a string and search
        SearchThread thread = new SearchThread(string);
        thread.start();*/
    }

    /**
     * Create a new user when you first open up the app.
     *
     */
    private static User new_guy = null;
    public void newUser(View view){
        final Context context = getApplicationContext();
        final String username;

        if(newUserName.getText().toString().isEmpty()){
            Toast.makeText(context, "You need a name!", Toast.LENGTH_SHORT).show();
        }else {
            username = newUserName.getText().toString();

            try {
                new_guy = userDB.createUser(username);
            } catch (UserAlreadyExistsException e) {
                e.printStackTrace();
            }
            new_guy.getProfile().setEmail(newUserEmail.getText().toString());
            userDB.save();

            if (new_guy != null) {
                Toast.makeText(context, "Welcome, " + username, Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_main);
            } else {
                Toast.makeText(context, username + " Already Exists!", Toast.LENGTH_SHORT).show();
                // Do nothing
            }

            //todo email if needed
        }
    }

    /**
     * Browse all skillz or users in the database. Might be able to combine this with refined search
     * for better code reuse. EEDIT
     * @param view
     */
    public void beginAllSearch(View view){
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        if(view.getId() == R.id.browse_skillz){
            intent.putExtra("All_search", 0);
        } else {
            intent.putExtra("All_search", 1);
        }
        startActivity(intent);
    }

    /**
     * Begin a refined search of the database from user input
     * @param view
     */
    public void beginRefinedSearch(View view){
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        startActivity(intent);
    }

    /**
     * Take user to their own profile when "Your Profile" button is pressed
     * @param view
     * @ TODO:
     */
    public void showProfile(View view){
        Intent intent = new Intent(mainContext, ProfileActivity.class);
        intent.putExtra("user_name_for_profile", userDB.getCurrentUser().getProfile().getUsername());
        startActivity(intent);
    }

    /**
     * Sends user to the EditSkill activity to make a new skill
     * @param view
     */
    public void createNewSkill(View view){
        //@todo need to differentiate 'createnewskill' and 'edit exisiting skill'???????
        Intent intent = new Intent(mainContext, EditSkillActivity.class);
        startActivity(intent);
    }
}
