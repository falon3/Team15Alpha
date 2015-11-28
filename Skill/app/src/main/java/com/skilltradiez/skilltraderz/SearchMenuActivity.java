package com.skilltradiez.skilltraderz;

import android.app.ActionBar;
import android.os.Bundle;

/**
 * Created by Stephen on 2015-11-28.
 */
public abstract class SearchMenuActivity extends GeneralMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    protected abstract void startSearch(String query);
}
