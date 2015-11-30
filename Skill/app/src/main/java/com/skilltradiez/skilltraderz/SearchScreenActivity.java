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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire search activity process that the user will need to interact
 * with through our application.
 */
public class SearchScreenActivity extends SearchMenuActivity {
    /**Activity Class Variables:
     * 1: SEARCH_TYPE_PARAM: A constant string that is passed into methods to specify search type.
     * 2: FILTER_PARAM: A constant string that is passed into methods.
     * 3: SEARCH_QUERY: A constant string that signals that there is a query.
     * 4: screenType: An integer that indicates what sort of screen the application is on.
     * 5: categorySpinner: A Spinner Object that the user can interact with in order to select
     *      a category to search for.
     * 6: sortingSpinner: A Spinner Object that the user can interact with to choose how the
     *      data presented on the screen will be sorted.
     * 7: searchExtras: A Bundle Object that will allow extra searches.
     * 8: searchAdapter: An Adapter Object that will help represent the data onto the UI.
     * 9: items: A List of Stringeable Objects that can be displayed on the UI.
     * 10: resultsList: A ListView Object that represents all of the results to the user on the UI.
     */
    static String SEARCH_TYPE_PARAM = "All_search",
                FILTER_PARAM = "filter",
                FILTER2_PARAM = "filter2",
                USER_FILTER = "user_filter",
                SEARCH_QUERY = "query";
    private int screenType;
    private Spinner categorySpinner, sortingSpinner;
    private Bundle searchExtras;
    private ListAdapter searchAdapter;
    private List<Stringeable> items;
    private ListView resultsList;
    private User userFilter = null;

    /**
     * Handles UI affairs.
     * @param savedInstanceState Bundle Object.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        masterController = new MasterController();

        items = new ArrayList<Stringeable>();

        searchExtras = getIntent().getExtras();
        screenType = searchExtras.getInt(SEARCH_TYPE_PARAM);

        setSearchParam(screenType);

        String filter = "All";
        if (searchExtras.containsKey(FILTER_PARAM))
            filter = searchExtras.getString(FILTER_PARAM);

        String filter2 = "Name";
        if (searchExtras.containsKey(FILTER2_PARAM))
            filter2 = searchExtras.getString(FILTER2_PARAM);

        if (searchExtras.containsKey(USER_FILTER))
            userFilter = DatabaseController.getAccountByUserID((ID) searchExtras.get(USER_FILTER));

        resultsList = (ListView) findViewById(R.id.results_list);
        searchAdapter = new ListAdapter(this, items);

        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        sortingSpinner = (Spinner) findViewById(R.id.sorting_spinner);

        SpinnerAdapter<CharSequence> adapter, sortAdapter;
        CharSequence[] stringArray;

        // Categories
        if (screenType == 0) {
            stringArray = getResources().getStringArray(R.array.category_All);
        } else if (screenType == 1) {
            stringArray = getResources().getStringArray(R.array.friends_All);
        } else {
            stringArray = getResources().getStringArray(R.array.trades_All);
        }

        adapter = new SpinnerAdapter<CharSequence>(this, stringArray);

        // Sorting
        stringArray = getResources().getStringArray(R.array.sort);
        sortAdapter = new SpinnerAdapter<CharSequence>(this, stringArray);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(adapter.getPosition(filter));

        sortingSpinner.setAdapter(sortAdapter);
        sortingSpinner.setSelection(sortAdapter.getPosition(filter2));
    }

    /**
     * Handles UI Affairs.
     */
    @Override
    public void onStart() {
        super.onStart();
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refineSearch(getQuery());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Shouldn't need to be used
            }
        });

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refineSearch(getQuery());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Shouldn't need to be used
            }
        });

        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (screenType == 0) {
                    Skill skill = (Skill) parent.getItemAtPosition(position);
                    clickOnSkill(skill);
                } else if (screenType == 1) {
                    Profile user = (Profile) parent.getItemAtPosition(position);
                    clickOnUser(user);
                } else if (screenType == 2) {
                    Trade trade = (Trade) parent.getItemAtPosition(position);
                    clickOnTrade(trade);
                }
            }
        });

        loadItems();
        resultsList.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }

    /**
     * Loads items from the database of the application to be displayed on this SearchScreenActivity
     */
    public void loadItems() {
        //Refresh the database :D
        DatabaseController.refresh();
        items.clear();
        refineSearch(); // search for nothing initially

        if (screenType == 0) {
            // all skills
            setTitle("Search Skillz");
        } else if (screenType == 1) {
            // all users
            setTitle("Search Users");
        } else if (screenType == 2) {
            // all trades
            setTitle("Trade History");
        }
    }

    /**
     * This method when invoked with a query String of the user's search will call the
     * method to refineSearch with that query.
     * @param query String of what is being obtained by the user's inquisiton.
     */
    protected void startSearch(String query) {
        refineSearch(query);
    }

    /**
     * Provides a refined search with no parameters given and thus no string.
     */
    protected void refineSearch() {
        refineSearch("");
    }


    /**
     * Provides a refined search to the user, passed in the user's search query as a parameter.
     * Doesn't return anything in particular, however just updates the User Interface to display
     * the changes.
     * @param query String input.
     */
    public void refineSearch(String query){
        //get whatever is in searchField
        //apply it to the list of results
        //update view
        String search = query.toLowerCase(),
               category = categorySpinner.getSelectedItem().toString().toLowerCase();

        items.clear();
        if (screenType == 0) {
            // search skills
            Set<Skill> skills = masterController.getAllSkillz();
            for (Skill s : skills)
                if ((userFilter == null || userFilter.getInventory().hasSkill(s)) &&
                        s.toString().toLowerCase().contains(search) &&
                        (s.getCategory().toLowerCase().equals(category) || category.equals("all")) &&
                        (s.isVisible() || masterController.userHasSkill(s)))
                    items.add(s);
        } else if (screenType == 1) { // Search users
            Set<User> onlineUsers = masterController.getAllUserz();
            for (User u : onlineUsers)
                if (u.getProfile().getUsername().toLowerCase().contains(search) &&
                        (category.equals("all") ||
                                (category.equals("friends") && masterController.hasFriend(u)) ||
                                (category.equals("non-friends") && !masterController.hasFriend(u))))
                    items.add(u.getProfile());
        } else if (screenType == 2) { // Trade History
            List<Trade> trades = masterController.getAllTradezForCurrentUser();
            for (Trade t : trades) {
                if (t.toString().toLowerCase().contains(search) &&
                        (category.equals("all") ||
                                (category.equals("active") && t.isActive()) ||
                                (category.equals("inactive") && !t.isActive())))// && t.getHalfForUser(masterController.getCurrentUser()) != null)
                    items.add(t);
            }
        }
        orderBySpinner(items);
        searchAdapter.notifyDataSetChanged();
    }

    /**
     * Orders the List of Stringeable Objects (Skills) passed in by the selected spinner option.
     * @param items List of Stringeable Objects!
     */
    public void orderBySpinner(List<Stringeable> items) {
        String sort = sortingSpinner.getSelectedItem().toString();
        if (sort.equals("Name")) {
            orderByName(items);
        } else if (sort.equals("Category")) {
            orderByCategory(items);
        } else if (sort.equals("Top Trader")) {
            orderByTop(items);
        }

    }

    /**
     * Returns a copy of the list of skills, sorted ascending by name.
     * @param sorted List of Stringeable Objects (Skill Objects)
     * @return List of Stringeable Objects
     */
    public List<Stringeable> orderByTop(List<Stringeable> sorted) {
        Collections.sort(sorted, new Comparator<Stringeable>() {
            @Override
            public int compare(Stringeable lhs, Stringeable rhs) {
                return rhs.getTop() - lhs.getTop();
            }
        });
        return sorted;
    }

    /**
     * Returns a copy of the list of stringeables, sorted ascending by name.
     * @param sorted List of Stringeable Objects (Skill Objects)
     * @return List of Stringeable Objects
     */
    public List<Stringeable> orderByName(List<Stringeable> sorted) {
        Collections.sort(sorted, new Comparator<Stringeable>() {
            @Override
            public int compare(Stringeable lhs, Stringeable rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
        return sorted;
    }

    /**
     * Returns a copy of the list of stringeables, sorted ascending by category.
     * @param sorted List of Stringeable Objects
     * @return List of Stringeable Objects
     */
    public List<Stringeable> orderByCategory(List<Stringeable> sorted) {
        Collections.sort(sorted, new Comparator<Stringeable>() {
            @Override
            public int compare(Stringeable lhs, Stringeable rhs) {
                return lhs.getCategory().toLowerCase().compareTo(rhs.getCategory().toLowerCase());
            }
        });
        return sorted;
    }

    /**
     * This method, when invoked by clicking on a user, will start the new activity for Profiles.
     * @param u Profile Object.
     */
    public void clickOnUser(Profile u) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.UNIQUE_PARAM, u.getUsername());
        startActivity(intent);
    }

    /**
     * This method, when invoked by clicking on a skill, will start the new activity for Skills.
     * @param s Skill Object.
     */
    public void clickOnSkill(Skill s) {
        Intent intent = new Intent(this, SkillDescriptionActivity.class);
        intent.putExtra(SkillDescriptionActivity.ID_PARAM, s.getSkillID());
        startActivity(intent);
    }

    /**
     * This method, when invoked by clicking on a trade, will start the new activity for Trades.
     * @param t Trade Object.
     */
    public void clickOnTrade(Trade t) {
        Intent intent = new Intent(this, TradeRequestActivity.class);
        intent.putExtra(TradeRequestActivity.TRADE_ID_PARAM, t.getTradeID());
        intent.putExtra(TradeRequestActivity.ACTIVE_USER_ID_PARAM, masterController.getCurrentUser().getUserID());
        intent.putExtra(TradeRequestActivity.PASSIVE_USER_ID_PARAM, t.getOppositeHalf(masterController.getCurrentUser()).getUser());
        startActivity(intent);
    }

    /**
     * This method will clone Stringeable Objects into a new List of Stringeable Objects and
     * return said List of Stringeable Objects to the caller.
     * @return List of Stringeable Objects.
     */
    public List<Stringeable> cloneStringeable() {
        List<Stringeable> stringeables = new ArrayList<Stringeable>();
        for (Stringeable string:items)
            stringeables.add(string);
        return stringeables;
    }
}
