package com.skilltradiez.skilltraderz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends GeneralMenuActivity {

    private Button saveSettings;
    private EditText usernameSettings, citySettings, emailSettings;
    private CheckBox downloadPics;
    private String userName, userEmail, userCity;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        masterController = new MasterController();

        saveSettings = (Button) findViewById(R.id.settings_save_button);
        //usernameSettings = (EditText) findViewById(R.id.settings_username_field);
        citySettings = (EditText) findViewById(R.id.settings_user_city_field);
        emailSettings = (EditText) findViewById(R.id.settings_user_contact_field);
        downloadPics = (CheckBox) findViewById(R.id.settings_picture_checkbox);

       /* user = masterController.getUserByName("username");
        emailSettings.setText(user.getProfile().getEmail());
        usernameSettings.setText(user.getProfile().getUsername());*/
    }


    public void saveSettings(View view){

    }

}
