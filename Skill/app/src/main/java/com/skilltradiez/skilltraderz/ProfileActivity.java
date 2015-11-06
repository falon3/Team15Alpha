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

    private User currentUser;
    private Boolean hasFriend;

    private Context profileContext = this;

    private Button addRemoveFriend;
    private Button startTrade;
    private Button viewInventory;
    private TextView userContactInfo;
    private TextView profileTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileExtras = getIntent().getExtras();
        userProfileName = profileExtras.getString("user_name_for_profile");

        addRemoveFriend = (Button) findViewById(R.id.add_friend);
        startTrade = (Button) findViewById(R.id.maketrade);
        viewInventory = (Button) findViewById(R.id.inventory);
        userContactInfo = (TextView) findViewById(R.id.user_description);
        profileTitle = (TextView) findViewById(R.id.user_name);

        populateProfile();
        profileTitle.setText(userProfileName);
        userContactInfo.setText(currentUser.getProfile().getEmail());
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
        populateProfile();
    }

    /**
     * When you click on a profile it gets data something something @todo make this sound nicer
     */
    public void populateProfile(){
        currentUser = MainActivity.userDB.getAccountByUsername(userProfileName);
        hasFriend = currentUser.getFriendsList().hasFriend(MainActivity.userDB.getCurrentUser());

    }


    /**
     * Begins the inventory activity, and shows the inventory specific to the user whose profile
     * you are browsing
     * @param view
     */
    public void checkInventory(View view){
        Intent intent = new Intent(profileContext, InventoryActivity.class);
        intent.putExtra("user_id", currentUser.getUserID());
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

    public void addRemoveFriend(View view){
        if(hasFriend){
            removeFriend();
        }else{
            addFriend();
        }
        hasFriend = !hasFriend;
    }

    /**
     * Add a user as a friend.
     */
    public void addFriend(){

        //@todo send friend to be added to friend list
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Added "+currentUser.getProfile().getUsername()+" as a friend", Toast.LENGTH_SHORT);
        toast.show();

        addRemoveFriend.setText(R.string.remove_friend);
    }

    /**
     * Remove friend from user's friendlist
     */
    public void removeFriend(){
        //@todo send friend to remove from friend list
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Removed "+currentUser.getProfile().getUsername()+" from FriendsList", Toast.LENGTH_SHORT);
        toast.show();

        addRemoveFriend.setText(R.string.add_friend);
    }
}
