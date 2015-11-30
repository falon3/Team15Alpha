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

        DatabaseController.save();
        finish();
    }

}
