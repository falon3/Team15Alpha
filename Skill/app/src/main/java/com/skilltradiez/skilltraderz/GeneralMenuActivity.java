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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
 * This activity is going to be the core menu page for our entire application, following
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

    /**Activity Class Variables:

     * 1: generalContext: Assign a Context to a variable to be used generally.
     * 2: masterController: Create the ever-critical MasterController object that acts
     *    as the most core controller for the entire application.
     * 3: searchBar: This EditText value is what allows the user to actually formulate a string
     *    query that the application can process.
     * 4: SEARCH_PARAM: This is going to indicate the type of search actually being performed
     *    by the application when the user desires to search.
     */
    protected Context generalContext = this;
    public MasterController masterController;
    protected EditText searchBar;
    private Thread thread;
    protected static boolean connected;
    private boolean pause;
    // Search Skillz By default
    private int SEARCH_PARAM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.ic_search);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.search, null);

        actionBar.setCustomView(view);

        pause = false;

        // Checks internet connectivity every second on separate thread
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    connected = isConnected();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while(pause){try{Thread.sleep(1000);}catch(InterruptedException e){e.printStackTrace();}}
                    System.out.println("isConnected = "+connected);
                }
            }
        });
        thread.start();
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
            case R.id.Trade_History:
                intent = new Intent(generalContext, SearchScreenActivity.class);
                intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 2);
                startActivity(intent);
                return true;
            case R.id.Go_To_Settings:
                intent = new Intent(generalContext, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.Go_Home_Menu:
                intent = new Intent(generalContext, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.Go_Profile_Menu:
                intent = new Intent(generalContext, ProfileActivity.class);
                intent.putExtra(ProfileActivity.UNIQUE_PARAM, masterController.getCurrentUserUsername());
                startActivity(intent);
                return true;
            case R.id.Go_Make_Skill:
                intent = new Intent(generalContext, EditSkillActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        pause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }

    /**
     * Given an integer value to identify the search parameter type, will set the local variable
     * SEARCH_PARAM to the given integer value.
     *
     * @param NEW_PARAM Integer value given, meant to represent the search parameter type.
     */
    protected void setSearchParam(int NEW_PARAM) {
        SEARCH_PARAM = NEW_PARAM;
    }


    /**
     * Given a string of what the user wants to search for, we treat this as query and start a
     * new activity class based upon this search.
     *
     * Create a new intent, given the Context, and the SearchStringActivity class.
     * Specify extra parameters on this SearchStringActivity that include SEARCH_TYPE being
     *    the type of search to perform, SEARCH_PARAM being the type of the search.
     * But that line isn't enough, we need to specify for this SearchScreenActivity
     *    the SEARCH_QUERY which is the string of thee query given into this method.
     * Finally we invoke that activity using startActivity.
     *
     * @param query String of what is being obtained by the user's inquisiton.
     */
    protected void startSearch(String query) {
        Intent intent = new Intent(generalContext, SearchScreenActivity.class);
        intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, SEARCH_PARAM);
        intent.putExtra(SearchScreenActivity.SEARCH_QUERY, query);
        startActivity(intent);
    }

    /**
     * Returns the String that the user put into the search bar.
     * @return A String of the "Query" the user put into the search bar.
     */
    protected String getQuery() {
        return searchBar.getText().toString();
    }

    /**
     * Checks if the device connected to internet
     * source: http://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
     * Returns true if connectivity is available, False otherwise.
     * @return Boolean. True/False.
     */
    public boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //@todo parent activity for enabling the back button on action bar needs work

    /**
     * When invoked this method will return the MasterController object for the entire application.
     * @return MasterController Object.
     */
    public MasterController getMasterController() {
        return masterController;
    }
}
