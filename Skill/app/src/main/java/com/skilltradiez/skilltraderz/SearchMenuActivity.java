package com.skilltradiez.skilltraderz;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Stephen on 2015-11-28.
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

    protected abstract void startSearch(String query);
}
