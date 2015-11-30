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
 * An abstract class that can be utilized in other classes.
 * This class is purely based around giving us a generalizable set of tools that we can easily
 * put into another class for buttons and text. This is widely seen across our application- this
 * particular arrangement of buttons and text.
 */
public abstract class ButtonMenuActivity extends GeneralMenuActivity {
    /** Class Variables:
     * 1: leftButton, in the UI we usually have a very distinct button on the left side. This
     *    variable represents and holds the button.
     * 2: rightButton, in the UI we usually have a very distinct button on the right side. Thios
     *    variable represents and holds that button.
     */
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

    /**
     * Set the Left Button on the User Interface to be Visible.
     */
    public void activateLeftButton() {
        leftButton.setVisibility(View.VISIBLE);
    }

    /**
     * Set the Left Button on the User Interface to be Invisible.
     */
    public void deactivateLeftButton() {
        leftButton.setVisibility(View.INVISIBLE);
    }

    /**
     * Set the Right Button on the User Interface to be Visible.
     */
    public void activateRightButton() {
        rightButton.setVisibility(View.VISIBLE);
    }

    /**
     * Set the Right Button on the User Interface to be Invisible.
     */
    public void deactivateRightButton() {
        rightButton.setVisibility(View.INVISIBLE);
    }


    /**
     * Set the Left Text on the User Interface to the given CharSequence
     * @param text CharSequence Object of what to change the text to show.
     */
    public void setLeftText(CharSequence text) {
        leftButton.setText(text);
    }

    /**
     * Set the Right Text on the User Interface to the given CharSequence
     * @param text CharSequence Object of what to change the text to show.
     */
    public void setRightText(CharSequence text) {
        rightButton.setText(text);
    }

    /**
     * Abstract function used for identifying when we click on the Right Button of the UI.
     * @param view View Object.
     */
    public abstract void clickOnRightButton(View view);

    /**
     * Abstract function used for identifying when we click on the Left Button of the UI.
     * @param view View Object.
     */
    public abstract void clickOnLeftButton(View view);
}
