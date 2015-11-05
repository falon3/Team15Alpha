package com.skilltradiez.skilltraderz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends ActionBarActivity {

    public static String USER_PROFILE = "USER_PROFILE";

    private Context profileContext = this;

    private Button addRemoveFriend;
    private Button blockUser;
    private Button startTrade;
    private Button viewInventory;
    private TextView userDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onStart(){
        super.onStart();

        addRemoveFriend = (Button) findViewById(R.id.add_remove_friend);
        blockUser = (Button) findViewById(R.id.block);
        startTrade = (Button) findViewById(R.id.maketrade);
        viewInventory = (Button) findViewById(R.id.inventory);
        userDescription = (TextView) findViewById(R.id.user_description);


    }

    /**
     * User description will be populated by a specific description connected to a specific user
     * @ TODO:
     */
    public void userDescription(){
        //will need to get the id of the user we're looking at, self or otherwise
        //userDescription = the description that is connected to the user we're looking at
        //update the view to show the description

    }

    /**
     * Begins the inventory activity, and shows the inventory specific to the user whose profile
     * you are browsing
     * @param view
     */
    public void checkInventory(View view){
        Intent intent = new Intent(profileContext, InventoryActivity.class);
        //intent.putExtra(the id of the person we're viewing')
        startActivity(intent);
    }

    /**
     *
     * @param view
     * @ TODO: 11/4/2015
     */
    public void startTrade(View view){
        //check if user you are making a trade with is your friend
        //do not show start trade button
        Intent intent = new Intent(profileContext, TradeRequestActivity.class);
        startActivity(intent);
    }

    /**
     * Add a user as a friend.
     * @ TODO:
     */
    public void addRemoveFriend(){
        //send a message to the user you want to befriend. Shows an alert dialog that message
        //has been sent instead of starting a new activity
        //update the view to change the "Add friend" button to "Remove friend" button
        Toast toast = Toast.makeText(profileContext, "Added a friend", Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * Blocks a user. This user will be added to a list of users that are not able to view your
     * profile and inventory.
     * @ TODO:
     */
    public void blockUnblockUser(){
        //User will be notified with first an "are you sure" dialog and then a confirmation
        // update view to change the "Block user" button to "Unblock User"
        Toast toast = Toast.makeText(profileContext, "blocked a user", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Check the user that we are looking at and change the USER_ID to match.
     * @ TODO:
     */
    public void checkUser(){
        //USER_ID = some function to tell us who we're looking at
    }



}
