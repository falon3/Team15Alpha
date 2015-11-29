package com.skilltradiez.skilltraderz;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Stephen on 2015-11-28.
 */
public abstract class ButtonMenuActivity extends GeneralMenuActivity {
    protected Button leftButton, rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.button_menu, null);

        actionBar.setCustomView(view);

        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);
    }

    protected void activateLeftButton() {
        leftButton.setVisibility(View.VISIBLE);
    }

    protected void deactivateLeftButton() {
        leftButton.setVisibility(View.INVISIBLE);
    }

    protected void activateRightButton() {
        rightButton.setVisibility(View.VISIBLE);
    }

    protected void deactivateRightButton() {
        rightButton.setVisibility(View.INVISIBLE);
    }

    protected void setLeftText(CharSequence text) {
        leftButton.setText(text);
    }

    protected void setRightText(CharSequence text) {
        rightButton.setText(text);
    }

    protected abstract void clickOnRightButton(View view);

    protected abstract void clickOnLeftButton(View view);
}
