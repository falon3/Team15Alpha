package com.skilltradiez.skilltraderz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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

public class InventoryActivity extends ActionBarActivity {

    public static String USER_INVENTORY = "USER_INVENTORY";

    Context inventoryContext = this;
    private User currentUser;
    private List<Skill> skillz;

    private Button searchButton;
    private Button startTrade;
    private EditText searchField;
    private String searchInventory;
    private Spinner categorySpinner;
    private ListView inventoryList;
    private ArrayAdapter<Skill> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //TODO: change to a user ID passed by intent
        currentUser = MainActivity.userDB.getCurrentUser();

        searchButton = (Button) findViewById(R.id.search_button);
        searchField = (EditText) findViewById(R.id.search_bar);
        startTrade = (Button) findViewById(R.id.maketrade);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        inventoryList = (ListView) findViewById(R.id.search_list);

        searchInventory = "";
    }

    @Override
    public void onStart(){
        super.onStart();

        loadSkillz();
        adapter = new ArrayAdapter<Skill>(this,
                R.layout.list_item, skillz);

        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadSkillz() {
        Inventory inv = currentUser.getInventory();
        skillz = inv.cloneSkillz(MainActivity.userDB);

        //TEST
        //skillz.add(new Skill(MainActivity.userDB, "Drain Runner"+ID.generateRandomID(), "Not for the faint of heart"));
        //skillz.add(new Skill(MainActivity.userDB, "Parkour"+ID.generateRandomID(), "Active"));
        //TODO: Make skill inits work and then,
        //TODO test(I know it works) and after,
        //TODO: Remove the tests
    }

    /**
     * Begin the trade activity
     * @param view
     * @ TODO:
     */
    public void startTrade(View view){
        //need to check that you are friends with this person
        //then un-grey out the 'make trade' button
        Intent intent = new Intent(inventoryContext, TradeRequestActivity.class);
        startActivity(intent);
    }

    /**
     * Search through a user's inventory with a textual query to refine the number of items that
     * are shown
     * @ TODO:
     */
    public void searchInventory(){
        //searchfield = what you're searching for
        //update list of skills based on closest to search field

    }

    /**
     * Refine the number of skills that are shown on screen based on the category that each skill
     * belongs to
     * @ TODO:
     */
    public void refineInventoryByCategory(){
        //inflate the spinner category. Populate it with a list of categories
        //refine skill list based on the category
    }

    /**
     * Sends a query to the database with a specific user ID to get the list of skills in their
     * inventory
     * @ TODO:
     */
    public void populateInventory(){
        //need to populate inventory list with the skills from a specific user by using the
        // USER_INVENTORY string and calling whatever database thing that'll do it
    }

    /**
     * onClick for a chosen skill. Will being a Skill Description activity
     * @param view
     */
    public void skillDetails(View view){
        Intent intent = new Intent(inventoryContext, TradeRequestActivity.class);
        startActivity(intent);

    }
}
