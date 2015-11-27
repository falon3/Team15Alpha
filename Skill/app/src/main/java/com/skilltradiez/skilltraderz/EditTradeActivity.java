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
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**~~DESCRIPTION:
 * Since trading is THE major selling point of this application it would only make sense for
 * us to actually be able to modify the trades that we are actually using in this application.
 * Trades are their own object (Please go to that class in order to see all of the details and
 * the description of what the class does and it's methods!). Since trades are their own
 * object it makes life a great deal easier to deal with when we actually have a place within
 * the application where we can actually let the android framework (android studio) manage
 * what the user is going to actually do with the application to interact with this object.
 *
 * How else are we going to have the user interact with it? "Tada here is a class, we hope you
 * somehow figure out a way to interact with it!" Buttons, UI, layouts... this is all essential
 * for any sort of decent user experience. Without it we are in trouble! Big... big trouble!
 *
 * ~~ACCESS:
 * This may seem redundant but for formatting purposes... this is a "public" class, meaning that
 * we can have this class actually be accessed technically anywhere in the application that
 * calls it. But since this is an activity it may seem a bit strange to refer to instantiating
 * an instance of the "EditTradeActivity" object.
 *
 * Instead what is happening is that we are having this activity be called by the onCreate() method
 * as is traditionally done in the android studio framework for android applications. In this
 * instance we're going to create this activity and then we'll have an onstart() method following
 * this which is going to make it so that we have this activate a cascade of events that are all
 * interelated with the main primary goal of allowing us to have a screen where we edit the
 * trading activity!
 *
 *~~CONSTRUCTOR:
 * Upon calling the method onCreate() for this activity the android studio framework will
 * cause the android application to create an instance of this actvity and display it to the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: None yet. Placeholder.
 *
 *
 */

public class EditTradeActivity extends GeneralMenuActivity {
    private TextView tradeTitle;
    private ArrayAdapter<Skill> skillAdapter;
    private ListView skillsInTrade;
    private EditText tradeNotes;
    private Button sendTrade;
    private Button cancelTrade;
    private Button addSkillToTrade;

    private User activeUser, passiveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trade);

        ID userID;

        masterController = new MasterController();
        Bundle profileExtras = getIntent().getExtras();

        // Get active and passive Users
        userID = (ID)profileExtras.get("active_id");
        activeUser = DatabaseController.getAccountByUserID(userID);
        userID = (ID)profileExtras.get("passive_id");
        passiveUser = DatabaseController.getAccountByUserID(userID);

        // Get UI elements
        tradeTitle = (TextView) findViewById(R.id.trading_with);

        //skillsInTrade = (ListView) findViewById(R.id.skill);
    }

    @Override
    public void onStart() {
        super.onStart();
        tradeTitle.setText("Trading With " + passiveUser.getProfile().getUsername());
    }

    public void addSkillToTrade(View view){
        Context context = getApplicationContext();
        Toast.makeText(context, "Doesn't work yet whoops!", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(EditTradeActivity.this, InventoryActivity.class);
        startActivity(intent);*/
    }

    public void deleteRequest(View view){
        Context context = getApplicationContext();
        Toast.makeText(context, "Deleted your request", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(EditTradeActivity.this, ProfileActivity.class);
        startActivity(intent);*/
    }

    public void sendTrade(View view){
        Context context = getApplicationContext();
        Toast.makeText(context, "Trade Request Sent", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(EditTradeActivity.this, ProfileActivity.class);
        startActivity(intent);*/

    }
}
