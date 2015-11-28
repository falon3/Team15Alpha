package com.skilltradiez.skilltraderz;

import android.app.ActionBar;
import android.os.Bundle;
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

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        searchBar = (EditText) findViewById(R.id.search_bar);

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_bar_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch(getQuery());
            }
        });
    }

    @Override
    protected abstract void startSearch(String query);
}
