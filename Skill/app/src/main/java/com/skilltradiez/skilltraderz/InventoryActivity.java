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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**~~DESCRIPTION:
 * We want users to interact with our application, it just makes things a lot easier when
 * we have a proper functioning framework that the user can mindlessly use without neeeding to worry
 * and fret about using seemingly esoterric code. Therefore we have activities provided to us
 * through the android studio android framework! It is glorious!
 *
 * This is going to be the interactive framework that is going to allow the user to interact
 * with the inventory classes (and all of the things connected to said inventory class) in an
 * appropriate and managable format!
 */

public class InventoryActivity extends SearchMenuActivity {

    /**Activity Class Variables:
     * 1: USER_INVENTORY: Assigned a string of the same name used to pass a constant into methods.
     * 2: ID_PARAM: Assigned the string "user_id" for use in method calls.
     * 3: inventoryContext: The UI object of context for this particular Activity, helps preserve
     *    information for use in methods being called.
     * 4: currentUser: Stores the User Object of the current user that is on this application.
     * 5: skillz: A list of Skill Objects that represent all skills in the user's inventory.
     * 6: foundSkillz: A list of Stringeable Objects that allow simple database interactions.
     * 7: searchButton: A Button that is used to invoke the search methods in this activity.
     * 8: startTrade: A Button that when clicked will invoke the methods to start a trade.
     * 9: searchField: An EditText that the user can use to input their search queries.
     * 10: searchInventory: A string that is passed into methods with the search query
     * 11: categorySpinner: A UI Spinner Object that shows to the user the various categories
     *    that they may choose from, also has category pulled out to pass selected category into
     *    methods.
     * 12: inventoryList: A ListView Object that displays data in a listView-fashion to the user.
     * 13: adapter: An ArrayAdapter of Stringeable Objects that will allow us to update the UI
     *    of this activity when there are modifications to the dataset meant to be represented.
     *
     */
    static String USER_INVENTORY = "USER_INVENTORY",
                    ID_PARAM = "user_id";

    private Context inventoryContext = this;
    private User currentUser;
    private List<Skill> skillz;
    private List<Stringeable> foundSkillz;

    private Button searchButton, startTrade;
    private EditText searchField;
    private String searchInventory;
    private Spinner categorySpinner, sortingSpinner;
    private ListView inventoryList;
    private ArrayAdapter<Stringeable> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        
        masterController = new MasterController();
        currentUser = masterController.getUserByID((ID) getIntent().getExtras().get(ID_PARAM));

        searchButton = (Button) findViewById(R.id.search_button);
        searchField = (EditText) findViewById(R.id.search_bar);
        startTrade = (Button) findViewById(R.id.maketrade);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        sortingSpinner = (Spinner) findViewById(R.id.sort_spinner);
        inventoryList = (ListView) findViewById(R.id.results_list);

        if (masterController.getCurrentUser().equals(currentUser))
            startTrade.setVisibility(View.INVISIBLE);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //        R.array.category_All, android.R.layout.simple_spinner_item);
        CharSequence[] strings = getResources().getStringArray(R.array.category_All);
        SpinnerAdapter<CharSequence> adapter = new SpinnerAdapter<CharSequence>(this, strings);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchInventory(getQuery());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Shouldn't need to be used
            }
        });

        CharSequence[] strings2 = getResources().getStringArray(R.array.sort);
        SpinnerAdapter<CharSequence> adapter2 = new SpinnerAdapter<CharSequence>(this, strings2);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(adapter2);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchInventory(getQuery());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Shouldn't need to be used
            }
        });

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

        searchInventory();
    }

    /**
     * Loads from the database all of the Inventory for the current user, then assigns the class
     * variable skillz those Skill Objects.
     *
     * Obtains Inventory Object for the current user from the database.
     * Assigns the class variable for this activity the clone of all of those Skill Objects.
     * Creates a new ArrayList of Stringeables and assigns it to the class variable foundSkillz.
     */
    private void loadSkillz() {
        Inventory inv = currentUser.getInventory();
        skillz = inv.cloneSkillz(masterController.getUserDB());
        foundSkillz = new ArrayList<Stringeable>();
    }

    /**
     * Begin the trade activity, will begin a new activity based upon the EditTradeActivity class.
     *
     * Given a View Object to differentiate where this method was called from, this method will
     *    create a new Intent Object based upon the context of the given Inventory View Object
     *    and the EditTradeActivity class.
     *
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
     * Begins a new search based upon a String passed into this method.
     *
     * Will quite literally invoke the searchInventory method with the query given.
     * @param query A String representing what the user is searching for.
     */
    public void startSearch(String query){
        searchInventory(query);
    }

    /**
     * Search through a user's inventory with a textual query to refine the number of items that
     * are shown.
     */
    public void searchInventory(){
        searchInventory("");
    }

    /**
     * Searches the inventory for Skills matching the string passed into this method, will however
     * not return any value- instead will notify adapters and change the UI representation of the
     * information.
     *
     * Obtain a String of the user's search string, the query. (Regex is a misnomer here.)
     * Obtain a list of Skill Objects from the method refineInventoryByCategory.
     * Obtain a second list of Skill Objects from the finding the User's inventory based upon
     *    the findByName method by passing in the UserDatabase Object and the query string.
     * Clears the default variable for the class of found skills to ensure a pristine search.
     * Iterates through the SECOND list of Skill Objects and will append them to the default
     *    variable for the class of found skills cleared above. But the skills will be appended only
     *    if they are also present in the other list of Skill Objects we created in this method.
     * We then notify the adapter for the UI that there has been a change in the data set that it is
     *    meant to represent.
     *
     * @param query A String of what the user is searching for.
     */
    public void searchInventory(String query){
        //searchfield = what you're searching for
        //update list of skills based on closest to search field
        String regex = query; // not a regex
        List<Skill> categorySkillz = refineInventoryByCategory(),
                nameSkillz = currentUser.getInventory().findByName(masterController.getUserDB(), regex);

        foundSkillz.clear();
        for (Skill s:nameSkillz)
            if (categorySkillz.contains(s))
                foundSkillz.add(s);

        adapter.notifyDataSetChanged();
    }

    /**
     *  Returns back the List of Skill Objects being refined by a particular category dicated
     *  by the spinner.
     *
     *  Obtain the category by pulling the string out from the spinner.
     *  Have an if statement that if the spinner says all categories then we will be returning
     *     a cloned list of Skill Objects from the current User's Inventory.
     *  If the category, however, is NOT "all" then we will invoke the findByCategory method and
     *  pass into that method the UserDatabase Object and the category pulled out from the spinner.
     *  We then return the List of Skills.
     *
     * @return List of Skill Objects.
     */

    public List<Skill> refineInventoryByCategory(){
        //inflate the spinner category. Populate it with a list of categories
        //refine skill list based on the category
        String category = categorySpinner.getSelectedItem().toString().toLowerCase();
        if (category.equals("all"))
            return currentUser.getInventory().cloneSkillz(masterController.getUserDB());
        return currentUser.getInventory().findByCategory(masterController.getUserDB(), category);
    }

    /**
     * Will display the details for a given Skill Object.
     *
     * Typically invoked here by an onClick method.
     * @param skill Skill Object
     */
    public void skillDetails(Skill skill){
        Intent intent = new Intent(inventoryContext, SkillDescriptionActivity.class);
        intent.putExtra(SkillDescriptionActivity.ID_PARAM, skill.getSkillID());
        startActivity(intent);
    }
}
