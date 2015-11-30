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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the profile process that the user will need to interact
 * with through our application.
 */

public class ProfileActivity extends ButtonMenuActivity {
    /**Activity Class Variables:
     * 1: UNIQUE_PARAM: A constant string that is passed into methods to maintain consistency.
     * 2: profileExtras: A Bundle variable that is involved in the UI.
     * 3: userProfileName: The current Profile's username.
     * 4: owner: The username of the owner of the Profile being viewed.
     * 5: hasFriend: A boolean flag stating if this profile's user is or is not a friend of the
     *      current user.
     * 6: profileContext: A variable that is involved in the UI.
     * 7: viewInventory: A Button that the user may click to view inventories.
     * 8: friendListButton: A Button that the user may click to view the friendslist.
     * 9: userContactInfo: A TextView Object that allows the user to see the contact info of
     *      the profile.
     * 10: profileTitle A TextView Object that lets the current user view the Profile's title.
     * 11: checkBox: A CheckBox Object that lets the user decide if they want to let images
     *      be downloaded or not.
     */
    static String UNIQUE_PARAM = "user_name_for_profile";
    static String CITY_PARAM = "Go to settings to input a city!";
    private Bundle profileExtras;
    private String userProfileName;
    private User owner;
    private Boolean hasFriend;
    private Context profileContext = this;
    private Button viewInventory, friendListButton;
    private TextView userContactInfo, profileTitle, userCityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        masterController = new MasterController();
        profileExtras = getIntent().getExtras();
        userProfileName = profileExtras.getString(UNIQUE_PARAM);

        viewInventory = (Button) findViewById(R.id.inventory);
        profileTitle = (TextView) findViewById(R.id.user_name);
        userContactInfo = (TextView) findViewById(R.id.user_description);
        userCityInfo = (TextView) findViewById(R.id.user_city);
        friendListButton = (Button) findViewById(R.id.friend_list_button);
    }

    @Override
    public void onResume(){
        super.onResume();
        DatabaseController.refresh();
        owner = masterController.getUserByName(userProfileName);
        populateProfile();
        enableButtons();
    }

    /**
     * Sets up the UI for the Menu of this Activity.
     * @param menu Menu Object.
     * @return Boolean. True/False.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);

        //disable go to profile button from menubar when already in profile
        MenuItem item = menu.findItem(R.id.Go_Profile_Menu);
        item.setEnabled(false);
        return true;
    }

    /**
     * When you click on a profile it gets the profile data and displays
     * the correct button in corner of whether can "add" or "remove" friend depending if you have
     * added them already or not.
     */
    public void populateProfile() {
        hasFriend = masterController.hasFriend(owner);

        profileTitle.setText(owner.getProfile().getUsername());
        userContactInfo.setText(owner.getProfile().getEmail());

        if(owner.getProfile().getLocation()==null){
            userCityInfo.setText(CITY_PARAM);
        } else {
            userCityInfo.setText(owner.getProfile().getLocation());
        }
        System.out.println("Location?" + owner.getProfile().getLocation());
    }

    /**
     * Enable all of the buttons (UI elements) that are present on the Profile.
     * Basically this is a method that just sets up the entire UI for Profiles.
     */
    public void enableButtons() {
        if (hasFriend) {
            //check if user is your friend you can make trade request
            setRightText("Remove Friend");
            activateLeftButton();
        } else {
            //do not show start trade button since not a friend
            setRightText("Add Friend");
            deactivateLeftButton();
        }

        // You can't be friends with yourself, go get some real friends
        if (masterController.getCurrentUser().equals(owner)) {
            deactivateRightButton();
            deactivateLeftButton();
        } else {
            friendListButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Begins the inventory activity, and shows the inventory specific to the user whose profile
     * you are browsing
     * @param view View Objeckt.
     */
    public void checkInventory(View view){
        Intent intent = new Intent(profileContext, SearchScreenActivity.class);
        intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 0);
        intent.putExtra(SearchScreenActivity.USER_FILTER, owner.getUserID());
        startActivity(intent);
    }

    /**
     * This method activates when the left button on the UI is clicked. This will cause the
     * startTrade method to be invoked with the View
     * @param v View Object.
     */
    @Override
    public void clickOnLeftButton(View v) {
        startTrade(v);
    }

    /**
     * When called this method will trigger the activities related to Trading with a user that they
     * are viewing.
     * @param view View Object.
     */
    public void startTrade(View view){
        Intent intent = new Intent(profileContext, EditTradeActivity.class);
        intent.putExtra(EditTradeActivity.ACTIVE_PARAM, masterController.getCurrentUser().getUserID());
        intent.putExtra(EditTradeActivity.PASSIVE_PARAM, owner.getUserID());
        startActivity(intent);
    }

    /**
     * When the button to remove/add a friend is clicked will invoke the addRemoveFriend method.
     * @param v View Object.
     */
    @Override
    public void clickOnRightButton(View v) {
        addRemoveFriend(v);
    }

    /**
     * This method is a double-edged sword. It will reverse whatever the boolean flag is for
     * the particular profile the current user is viewing. Either they are a friend and will
     * be given the option to remove them from their friend's list OR they are not friends and
     * the current user will be given the option to add the user to their friend's list.
     * @param view View Object.
     */
    public void addRemoveFriend(View view){
        if(hasFriend){
            removeFriend();
            //do not show start trade button anymore since not a friend
            deactivateLeftButton();
        }else{
            addFriend();
            activateLeftButton();
        }
        hasFriend = !hasFriend;
    }

    /**
     * When the current user is on the profile of a non-friendd, this method can be called to add
     * that user to the current user's friends list.
     */
    public void addFriend(){
        //Function call to the master controller to deal with all this!
        masterController.addFriend(owner);

        DatabaseController.save();

        //Toasties!
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Added "+owner.getProfile().getUsername()+" as a friend", Toast.LENGTH_SHORT);
        toast.show();

        //Modify the displayed text to remove friend option.
        setRightText("Remove Friend");
    }

    /**
     * When the current user is on the profile of a friend, this method can be called to remove
     * that user from the current user's friends list.
     */
    public void removeFriend(){
        masterController.removeFriend(owner);

        DatabaseController.save();

        //Toasties!!!!!!
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Removed "+ owner.getProfile().getUsername() +" from FriendsList", Toast.LENGTH_SHORT);
        toast.show();

        //Modify the displayed text the user sees to keep them aware of their choices.
        setRightText("Add Friend");
    }

    /**
     * Show the friends list for the current user on the UI.
     * @param v View Object.
     */
    public void showFriendsList(View v) {
        Intent intent = new Intent(this, SearchScreenActivity.class);
        intent.putExtra(SearchScreenActivity.FILTER_PARAM, "Friends");
        intent.putExtra(SearchScreenActivity.SEARCH_TYPE_PARAM, 1);
        startActivity(intent);
    }
}
