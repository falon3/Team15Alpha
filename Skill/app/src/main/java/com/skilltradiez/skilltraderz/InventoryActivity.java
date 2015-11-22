package com.skilltradiez.skilltraderz;
/**~~DESCRIPTION:
 * We want users to interact with our application, it just makes things a lot easier when
 * we have a proper functioning framework that the user can mindlessly use without neeeding to worry
 * and fret about using seemingly esoterric code. Therefore we have activities provided to us
 * through the android studio android framework! It is glorious!
 *
 * This is going to be the interactive framework that is going to allow the user to interact
 * with the inventory classes (and all of the things connected to said inventory class) in an
 * appropriate and managable format!
 *
 * ~~ACCESS:
 * This may seem redundant but for formatting purposes... this is a "public" class, meaning that
 * we can have this class actually be accessed technically anywhere in the application that
 * calls it. But since this is an activity it may seem a bit strange to refer to instantiating
 * an instance of the "InventoryActivity" activity.
 *
 *
 * ~~CONSTRUCTOR:
 * Upon calling the method onCreate() for this activity the android studio framework will
 * cause the android application to create an instance of this actvity and display it to the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: CATEGORYSPINNER:
 *     Suppose we want to have a way to go through the various categories, this is the way we will
 *     achieve this! This is more UI related so I'm keeping this description short and sweet.
 *
 * 2: SEARCHINVENTORY (ATTRIBUTE):
 *     Wouldn't it be ideal if through the framework we could actually interact with the
 *     inventory in a manner that does not seem overwhelming? Well that is why we implemented
 *     this string! We will take a user given string and then search through the entire inventory
 *     for things that match this string. UI-ish but it has relevance in the code. So I detailed it.
 *
 *
 * 3: STARTTRADE:
 *     Is it not critical to START a trade with another user? Well this is going to be how
 *     we actually start a trade with another user! This is going to be the method that is called
 *     when the UI is activated to cascade through a series of statements that will fully set up
 *     the entire trading process with another user.
 *
 *
 * 4: SEARCHINVENTORY (METHOD):
 *     This is associated with the searchInventory attribute mentioned above, in this instance
 *     we're going to take the user's inputted string (the attribute saves it) and then we will
 *     filter through the entire inventory and obtain the results in this fashion that are
 *     actually going to be related to this search string.
 *
 *
 * 5: REFINEINVENTORYBYCATEGORY:
 *     We'll take the input of the spinner and then associate it with the categories that our
 *     application supports and then present to the user the inventory sorted out by a particular
 *     category that they selected through the spinner.
 *
 *
 * 6: POPULATEINVENTORY:
 *     We want the user to be able to browse other users (or even themselves right?) where they
 *     have an inventory. So we utilize this method to begin the entire process of obtaining
 *     the entire inventory of a user and then we present this entire bulky thing to the user.
 *     It's like magic.
 *
 * 7: SKILLDETAILS:
 *     Suppose we have an inquisitive user who looks at the "cat bathing" skill ... yet they want
 *     to know MORE about this particular skill, when they inqurie about this skill THIS method
 *     is called that will represent to the user through this activity (and thus the android
 *     framework) all of the details that are currently saved within the database that are related
 *     to that particular skill.
 *
 *
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
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

public class InventoryActivity extends GeneralMenuActivity {

    public static String USER_INVENTORY = "USER_INVENTORY";

    Context inventoryContext = this;
    private User currentUser;
    private List<Skill> skillz;//All skillz in inventory
    private List<Stringeable> foundSkillz;

    private Button searchButton;
    private Button startTrade;
    private EditText searchField;
    private String searchInventory;
    private Spinner categorySpinner;
    private ListView inventoryList;
    private ArrayAdapter<Stringeable> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        masterController = new MasterController();
        currentUser = masterController.getUserByID((ID) getIntent().getExtras().get("user_id"));

        searchButton = (Button) findViewById(R.id.search_button);
        searchField = (EditText) findViewById(R.id.search_bar);
        startTrade = (Button) findViewById(R.id.maketrade);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        inventoryList = (ListView) findViewById(R.id.search_list);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_spinner_strings, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Skill skill = (Skill) adapter.getItemAtPosition(position);
                skillDetails(skill);
            }
        });

        searchInventory = "";
    }

    @Override
    public void onStart(){
        super.onStart();

        loadSkillz();
        adapter = new ListAdapter(this, foundSkillz);

        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchInventory(null);


    }

    private void loadSkillz() {
        Inventory inv = currentUser.getInventory();
        skillz = inv.cloneSkillz(masterController.getUserDB());

        foundSkillz = new ArrayList<Stringeable>();
    }

    /**
     * Begin the trade activity
     * @param view
     * @ TODO:
     */
    public void startTrade(View view){
        //need to check that you are friends with this person
        //then un-grey out the 'make trade' button
        Intent intent = new Intent(inventoryContext, EditTradeActivity.class);
        startActivity(intent);
    }

    /**
     * Search through a user's inventory with a textual query to refine the number of items that
     * are shown
     */
    public void searchInventory(View v){
        //searchfield = what you're searching for
        //update list of skills based on closest to search field
        String regex = searchField.getText().toString(); // not a regex

        foundSkillz.clear();
        foundSkillz.addAll(currentUser.getInventory().findByName(masterController.getUserDB(), regex));
        adapter.notifyDataSetChanged();
    }

    /**
     * Refine the number of skills that are shown on screen based on the category that each skill
     * belongs to
     * @ TODO: CATEGORIES
     */
    public void refineInventoryByCategory(){
        //inflate the spinner category. Populate it with a list of categories
        //refine skill list based on the category
        //TODO: Current CATS ARE: MISC, Physical, Analytical, Creative, Computer, Household, Communication, Parenting and Stealth
        //TODO: Categories are subject to change
    }

    /**
     * onClick for a chosen skill. Will bring a Skill Description activity
     * @param skill
     */
    public void skillDetails(Skill skill){
        Intent intent = new Intent(inventoryContext, SkillDescriptionActivity.class);
        intent.putExtra("skill_id", skill.getSkillID());
        startActivity(intent);
    }
}
