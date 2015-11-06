package com.skilltradiez.skilltraderz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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

    private User users;
    private Skill skillz;
    private int searchScreenType;


    private Button searchButton;
    private EditText searchField;
    private String searchInventory;
    private Spinner categorySpinner;
    private Bundle searchExtras;

    private ArrayAdapter<Skill> searchViewSkillAdapter;
    private ArrayAdapter<User> searchViewUserAdapter;
    private ListView searchResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        searchExtras = getIntent().getExtras();
        searchScreenType = searchExtras.getInt("All_search");
        searchViewSkillAdapter = new ArrayAdapter<Skill>(searchScreenContext, R.layout.list_item);
        searchViewUserAdapter = new ArrayAdapter<User>(searchScreenContext, R.layout.list_item);
        searchButton = (Button) findViewById(R.id.search_button);
        searchField = (EditText) findViewById(R.id.search_bar);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
    }

    @Override
    public void onStart(){
        super.onStart();
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
    public void populateSearchResults(){

        if(searchScreenType == 0){
            //all skills

        }else{
            //all users
        } //@todo populate with refined search

    }

}
