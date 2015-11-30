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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * This is an abstract class that will provide the outline for functionality of a search menu.
 * The core of this class is to set up a variety of User Interface elements to be utilized across
 * other parts of the application and implemented with relative ease.
 */
public abstract class SearchMenuActivity extends GeneralMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.setVisibility(View.VISIBLE);

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_bar_button);
        searchButton.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch(getQuery());
            }
        });
    }

    /**
     * Provides the framework for a search to be started, given a String of the user's search
     * query as a parameter.
     * @param query String of what is being obtained by the user's inquisiton.
     */
    protected abstract void startSearch(String query);

    protected void setHint(String hint) {
        searchBar.setHint(hint);
    }
}
