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
    private TextView usernameSettings;
    private EditText citySettings, emailSettings;
    private CheckBox downloadPics;

    private Profile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        masterController = new MasterController();

        saveSettings = (Button) findViewById(R.id.settings_save_button);
        usernameSettings = (TextView) findViewById(R.id.settings_username);
        citySettings = (EditText) findViewById(R.id.settings_user_city_field);
        emailSettings = (EditText) findViewById(R.id.settings_user_contact_field);
        downloadPics = (CheckBox) findViewById(R.id.settings_picture_checkbox);

        user = masterController.getCurrentUser().getProfile();

        usernameSettings.setText(user.getUsername());

        emailSettings.setText(user.getEmail());
        citySettings.setText(user.getLocation());
        downloadPics.setChecked(user.getShouldDownloadImages());

    }


    public void saveSettings(View view){
        user.setEmail(emailSettings.getText().toString());
        user.setShouldDownloadImages(downloadPics.isChecked());
        user.setLocation(citySettings.getText().toString());
        finish();
    }

}
