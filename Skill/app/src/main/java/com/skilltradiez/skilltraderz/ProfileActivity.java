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

public class ProfileActivity extends ActionBarActivity {

    private Bundle profileExtras;
    private String userProfileName;

    private Context profileContext = this;

    private Button addRemoveFriend;
    private Button blockUser;
    private Button startTrade;
    private Button viewInventory;
    private TextView userDescription;
    private TextView userNameOnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        profileExtras = getIntent().getExtras();
        userProfileName = profileExtras.getString("user_name_for_profile");
        System.out.println(userProfileName);
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
        userNameOnProfile = (TextView) findViewById(R.id.user_name);

        populateProfile();


    }

    /**
     * When you click on a profile it gets data something something @todo make this sound nicer
     */
    public void populateProfile(){
        userNameOnProfile.setText("Hi User");

    }


    /**
     * Begins the inventory activity, and shows the inventory specific to the user whose profile
     * you are browsing
     * @param view
     */
    public void checkInventory(View view){
        Intent intent = new Intent(profileContext, InventoryActivity.class);
        //@todo need to get the id of the user we're looking at, either self or other to send to InventoryActivity
        //intent.putExtra(viewedUser.getUserID())
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
     */
    public void addFriend(){
        //update the view to change the "Add friend" button to "Remove friend" button
        //@todo should this be a controller?
        //viewingUser.FriendsList.addFriend(@todo user id of person you are adding);
        //viewedUser.FriendList.addFriend(@todo user if of person sending request)


        //send a message to the user you want to befriend. Shows an alert dialog that message
        //has been sent instead of starting a new activity. For Demo purposes
        Toast toast = Toast.makeText(profileContext, "Added a friend", Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * Remove friend from user's friendlist
     */
    public void removeFriend(){
        //viewingUser.FriendList.(@todo id of person being removed);
        //viewedUser.FriendList.acceptedFriendREquests(@todo id of person doing the removing);
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
