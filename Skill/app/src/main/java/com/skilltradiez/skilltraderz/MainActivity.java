package com.skilltradiez.skilltraderz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    private Context mainContext = this;

    private static UserDatabase userDB;

    private Button searchButton;
    private Button searchAllSkillzButton;
    private Button searchAllUsersButton;
    private Button goToProfile;
    private EditText searchField;
    private String searchDatabase;

    MainActivity(){
        userDB = new UserDatabase();
        userDB.getLocal().;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);

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

        searchButton = (Button) findViewById(R.id.search_button);
        searchAllSkillzButton = (Button) findViewById(R.id.browse_skillz);
        searchAllUsersButton = (Button) findViewById(R.id.browse_users);
        goToProfile = (Button) findViewById(R.id.go_to_profile);
        searchField = (EditText) findViewById(R.id.search_bar);

        searchDatabase = "";
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
     * Browse all skillz or users in the database. Might be able to combine this with refined search
     * for better code reuse. EEDIT
     * @param view
     */
    public void beginAllSearch(View view){
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        startActivity(intent);
        //@todo need to send the proper context to search.
        // Browse Skills/Users/Refined search are three separate
    }

    /**
     * Begin a refined search of the database from user input
     * @param view
     */
    public void beginRefinedSearch(View view){
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        //intent.putExtra(SearchScreenActivity.TO_SEARCH, searchDatabase)
        startActivity(intent);
    }

    /**
     * Take user to their own profile when "Your Profile" button is pressed
     * @param view
     * @ TODO:
     */
    public void showProfile(View view){
        //get user id to sent to the profileActivity so that we open up the right profile
        Intent intent = new Intent(mainContext, ProfileActivity.class);

        startActivity(intent);
    }

    /**
     * Sends user to the EditSkill activity to make a new skill
     * @param view
     * @ TODO:
     */
    public void createNewSkill(View view){
        //need to differentiate 'createnewskill' and 'edit skill'
        Intent intent = new Intent(mainContext, EditSkillActivity.class);
        startActivity(intent);
    }
}
