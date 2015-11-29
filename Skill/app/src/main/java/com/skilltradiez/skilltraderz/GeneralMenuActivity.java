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
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * DESCRPTION: This activity is going to be the core menu page for our entire application, following
 * MVC styling and the conceptual idea of an OO-Knife scraping off the UI we have made it so
 * that all functionality located here is going to be involved in the controller and model classes.
 *
 * However that being said this activity class contains all of the essential UI aspect of the
 * program which allows us to actually have a functioning interactive application!
 *
 * One CRITICAL function though is that this is actually going to be involved in creating
 * THE master controller for the application. Please refer to that class to see more information
 * on it- but it is of paramount importance that this is created ahead of time!
 */
public class GeneralMenuActivity extends ActionBarActivity {



    /**LOCAL CLASS VARIABLES:
     * 1: generalContext: Assign a Context to a variable to be used generally.
     * 2: masterController: Create the ever-critical MasterController object that acts
     * as the most core controller for the entire application.
     */

    protected Context generalContext = this;
    public MasterController masterController;
    protected EditText searchBar;
    // Search Skillz By default
    private int SEARCH_PARAM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.ic_search);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.search, null);

        actionBar.setCustomView(view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);
        return true;
    }

    /**
     * This method is core UI to the application's UI, it has a series of case statements that will
     * break down all of the possible options that the user interface may take. Has limited
     * functionality related purely to UI (activity, intents, etc). View and controller
     * functionality are still split into their respective categories and handled elsewhere.
     * As is the style of our MVC.
     *
     * @param item MenuItem Object. (UI)
     * @return Boolean. True/False.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.All_Skillz:
                intent = new Intent(generalContext, SearchScreenActivity.class);
                intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 0);
                startActivity(intent);
                return true;
            case R.id.All_Users:
                intent = new Intent(generalContext, SearchScreenActivity.class);
                intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 1);
                startActivity(intent);
                return true;
            case R.id.My_Friends:
                intent = new Intent(generalContext, SearchScreenActivity.class);
                intent.putExtra(SearchScreenActivity.FILTER_PARAM, "Friends");
                intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 1);
                startActivity(intent);
                return true;
            case R.id.Go_To_Settings:
                intent = new Intent(generalContext, SettingsActivity.class);
                startActivity(intent);
                return true;
            //@todo maybe if already at home screen don't go anywhere
            case R.id.Go_Home_Menu:
                intent = new Intent(generalContext, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.Go_Profile_Menu:
                intent = new Intent(generalContext, ProfileActivity.class);
                intent.putExtra(ProfileActivity.UNIQUE_PARAM, masterController.getCurrentUserUsername());
                startActivity(intent);
                return true;
            case R.id.Go_To_Messages_Menu:
                return true;
            case R.id.Go_Make_Skill:
                intent = new Intent(generalContext, EditSkillActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    protected void setSearchParam(int NEW_PARAM) {
        SEARCH_PARAM = NEW_PARAM;
    }

    protected void startSearch(String query) {
        Intent intent = new Intent(generalContext, SearchScreenActivity.class);
        intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, SEARCH_PARAM);
        intent.putExtra(SearchScreenActivity.SEARCH_QUERY, query);
        startActivity(intent);
    }

    protected String getQuery() {
        return searchBar.getText().toString();
    }

    //@todo clean up strings if there is time
    //@todo parent activity for enabling the back button on action bar needs work

    /**
     * When invoked this method will return the MasterController object for the entire application.
     * @return MasterController object.
     */
    public MasterController getMasterController() {
        return masterController;
    }
}
