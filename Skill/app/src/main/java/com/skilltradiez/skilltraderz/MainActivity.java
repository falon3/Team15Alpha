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

import com.skilltradiez.skilltraderz.R;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire skill description process that the user will need to interact
 * with through our application.
 */

public class MainActivity extends GeneralMenuActivity {
    /**Activity Class Variables:
     * 1: connected: A basic boolean starting TRUE if we are connected to the internet or FALSE
     *      if we are not connected to the internet.
     * 2: newUserName: A string associated with the user input for a new username.
     * 3: newUserEmail: A string associated with user input for a new user email.
     * 4: makeNewUser: A UI Button that when clicked prompts method calls.
     */
    private Context mainContext = this;
    //Main screen
    public static boolean connected;

    //First time user screen
    private EditText newUserName;
    private EditText newUserEmail;
    private Button makeNewUser;
    private ListView recentActivities;
    private RecentActivityAdapter adapter;
    private List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        masterController = new MasterController();
        masterController.initializeController();

        //TODO HACK
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (DatabaseController.isLoggedIn()) {
            setContentView(R.layout.activity_main);

            recentActivities = (ListView) findViewById(R.id.activitiesList);
            notifications = new ArrayList<Notification>();
            adapter = new RecentActivityAdapter(this, notifications);

            recentActivities.setAdapter(adapter);
        } else {
            setContentView(R.layout.first_time_user);

            // first_time (login)
            newUserName = (EditText) findViewById(R.id.usernameField);
            newUserEmail = (EditText) findViewById(R.id.emailField);
            makeNewUser = (Button) findViewById(R.id.beginApp);
        }

        // Checks internet connectivity every second on separate thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    connected = isConnected();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);

        //disable go to Home menu button from menubar when already in home menu
        MenuItem item = menu.findItem(R.id.Go_Home_Menu);
        item.setEnabled(false);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DatabaseController.isLoggedIn()) {
            notifications.clear();
            notifications.addAll(masterController.getUserDB().getChangeList().getChangedNotifications());
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Create a new user when you first open up the app.
     *
     * If the username or email fields are empty, prompt the user with a toast warning.
     * If both fields are entered, take the fields and assign them to string variables.
     *    Put these string variables into the database by calling the DatabaseController method
     *    to create a new user.
     *
     * However this also has another core functionality. If the user is already there then we
     *    give the user a welcome message and forward them to the next activity.
     * @param view View Object.
     */
    public void newUser(View view) {
        final Context context = getApplicationContext();
        final String username;
        User new_guy = null;

        if (newUserName.getText().toString().isEmpty() || newUserName.getText().toString().equals(" ")) {
            Toast.makeText(context, "You need a name!", Toast.LENGTH_SHORT).show();
        } else if (newUserEmail.getText().toString().isEmpty() || newUserEmail.getText().toString().equals(" ")) {
            Toast.makeText(context, "You need an email!", Toast.LENGTH_SHORT).show();
        } else {
            username = newUserName.getText().toString();

            // Used for error checking
            // If null, then it failed
            try {
                new_guy = DatabaseController.createNewUser(username, newUserEmail.getText().toString());
            } catch (UserAlreadyExistsException e) {
                e.printStackTrace();
            } catch (RuntimeException ex) {
                Toast.makeText(context, "Need to be online to create an account!" + username, Toast.LENGTH_SHORT).show();
            }
            DatabaseController.save();

            if (new_guy != null) {
                Toast.makeText(context, "Welcome, " + username, Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_main);

                recentActivities = (ListView) findViewById(R.id.activitiesList);
                notifications = new ArrayList<Notification>();
                adapter = new RecentActivityAdapter(this, notifications);

                recentActivities.setAdapter(adapter);
            } else {
                Toast.makeText(context, username + " Already Exists!", Toast.LENGTH_SHORT).show();
                // Do nothing
            }
        }
    }

    /**
     * Deletes the database. This is a method primarily based around testing.
     * @param view View Object.
     */
    public void beginAllSearch(View view) {
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        if (view.getId() == R.id.All_Skillz) {
            intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 0);
        } else if (view.getId() == R.id.All_Users) {
            intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 1);
        } else if ((view.getId() == R.id.My_Friends)){
            intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 1);
            intent.putExtra(SearchScreenActivity.FILTER_PARAM, "Friends");
        } else {
            intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 2);
        }
        startActivity(intent);
    }

    /**
     * Take user to their own profile when "Your Profile" button is pressed
     * @param view
     */
    public void showProfile(View view) {
        Intent intent = new Intent(mainContext, ProfileActivity.class);
        intent.putExtra(ProfileActivity.UNIQUE_PARAM, masterController.getCurrentUserUsername());
        startActivity(intent);
    }

    /**
     * Sends user to the EditSkill activity to make a new skill
     * @param view
     */
    public void createNewSkill(View view) {
        Intent intent = new Intent(mainContext, EditSkillActivity.class);
        startActivity(intent);
    }


    public void deleteDatabase(View view) {
        DatabaseController.deleteAllData();
        Toast.makeText(getApplicationContext(), "Complete online database has been deleted!!!!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Basic getter method that returns the EditText associated with the username.
     * @return EditText UI Element
     */
    public EditText getNameField() {
        return newUserName;
    }

    /**
     * Basic getter method that returns the EditText associated with email.
     * @return EditText UI Element
     */
    public EditText getEmailField() {
        return newUserEmail;
    }

    /**
     * Basic getter method that returns the Button associated with the login.
     * @return Button UI Element
     */
    public Button getLoginButton() {
        return makeNewUser;
    }

    /**
     * Clears the fields that are present on the Activity.
     */
    public void clearFields() {
        runOnUiThread(new Runnable() {
            public void run() {
                newUserName.setText("");
                newUserEmail.setText("");
            }
        });
    }

    /**
     * Checks if the device connected to internet
     * source: http://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
     * Returns true if connectivity is available, False otherwise.
     * @return Boolean. True/False.
     */
    public boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
