package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire search activity process that the user will need to interact
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
 * interelated with the main primary goal of allowing us to have a screen where we search the
 * actual screen of activities.
 *
 *~~CONSTRUCTOR:
 * Upon calling the method onCreate() for this activity the android studio framework will
 * cause the android application to create an instance of this actvity and display it to the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: SKILLS:
 *     We have a ton of skills involved in our application that are assocaited with every and
 *     any potential user. We're just going to store here in the activity a skill. Considering
 *     how our application is based around these skills, it is rather critical that we have
 *     a way of actually displaying this and letting the user actually do this.
 *
 * 2: USERS:
 *     Is it not essential to keep track of the users? Well it is! So we're going to maintain
 *     an attribute of the users that is going to actually have the users that are involved
 *     within this current search!
 *
 *
 *~~ MISC METHODS:
 * 1: REFINESEARCH:
 *     Suppose we want to refine a search, this method will be invoked when the user interacts
 *     with the UI with the intention to modify the search and then we have the user enter
 *     a string of what they want to search and this method will be invoked and search through
 *     all of the things and then update all views.
 *
 *
 * 2: CHANGECATEGORY:
 *     This will allow the user to actually be able to choose a particular category that they are
 *     interesting in viewing through the user interface, following this the app will go through
 *     a cascade of statements here that will allow the user to modify all of the search in order
 *     to be tailored to something that is directly related to the category of the user's
 *     choosing!
 *
 * 3: POPULATESEARCHRESULTS:
 *     Is it not critical to actually populate a pool of search results? Yes? YES IT IS!
 *     Without a pool of results for a user TO be sorted there is NO point in having a search screen
 *     and so when this activity is called and presenting the UI to the user we will actually
 *     be giving the UUI the method to actually populate the application being shown through
 *     the UI to the user through this particular method.
 *
 *
 *
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

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

public class SearchScreenActivity extends ActionBarActivity {

    //@todo what context are we getting things from? private int searchContext?

    private Context searchScreenContext = this;

    private List<User> users;
    private List<Skill> skillz;
    private int screenType;

    private Button searchButton;
    private EditText searchField;
    private Spinner categorySpinner;
    private Bundle searchExtras;

    private ArrayAdapter<Skill> skillAdapter;
    private ArrayAdapter<User> userAdapter;
    private ListView resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

   /*     searchExtras = getIntent().getExtras();
        screenType = searchExtras.getInt("All_search");

        resultsList = (ListView) findViewById(R.id.search_list);
        skillAdapter = new ArrayAdapter<Skill>(searchScreenContext, R.layout.list_item, skillz);
        userAdapter = new ArrayAdapter<User>(searchScreenContext, R.layout.list_item, users);
        searchButton = (Button) findViewById(R.id.search_button);
        searchField = (EditText) findViewById(R.id.search_bar);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);

        if(screenType == 0){
            // all skills
            resultsList.setAdapter(skillAdapter);
        } else {
            // all users
            resultsList.setAdapter(userAdapter);

        }*/




    }


    /**
     * Take a string and refine the list of Users/Skills
     * @ TODO:
     */
    public void refineSearch(){
        //get whatever is in searchField
        //apply it to the list of results
        //update view
    }

    /**
     * Change category. Yes I know this is a bad comment I'll come back to it.
     * @ TODO:
     */
    public void changeCategory(){
        //inflate the category spinner
        //refine the search results
    }

    /**
     * Populate the screen with Users or Skills based on requirements ie: search strings
     * @ TODO:
     */
    public void populateListView(){



    }

}
