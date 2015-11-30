package com.skilltradiez.skilltraderz;

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
}
