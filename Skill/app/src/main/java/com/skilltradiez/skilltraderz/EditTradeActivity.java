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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    static String ACTIVE_PARAM = "active_id",
                PASSIVE_PARAM = "passive_id";
    private TradeAdapter offerAdapter, yourInvAdapter, requestAdapter, otherInvAdapter;
    private ListView offerList, yourInvList, requestList, otherInvList;

    private TextView tradeTitle, otherInvTitle;
    private Button sendTrade, cancelTrade;

    private ArrayList<Skill> offer, yourInv, request, otherInv;
    private User activeUser, passiveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trade);

        masterController = new MasterController();
        Bundle profileExtras = getIntent().getExtras();

        // Get active and passive Users
        ID userID = (ID)profileExtras.get(ACTIVE_PARAM);
        activeUser = DatabaseController.getAccountByUserID(userID);
        userID = (ID)profileExtras.get(PASSIVE_PARAM);
        passiveUser = DatabaseController.getAccountByUserID(userID);

        //Init Lists
        offer = new ArrayList<Skill>();
        yourInv = new ArrayList<Skill>();
        request = new ArrayList<Skill>();
        otherInv = new ArrayList<Skill>();

        // Get UI elements
        sendTrade = (Button) findViewById(R.id.sendTrade);
        cancelTrade = (Button) findViewById(R.id.deleteTrade);

        tradeTitle = (TextView) findViewById(R.id.trading_with);
        otherInvTitle = (TextView) findViewById(R.id.other_inv);

        offerList = (ListView) findViewById(R.id.offerList);
        yourInvList = (ListView) findViewById(R.id.your_invList);

        requestList = (ListView) findViewById(R.id.requestList);
        otherInvList = (ListView) findViewById(R.id.other_invList);
    }

    @Override
    public void onStart() {
        super.onStart();
        tradeTitle.setText("Trading With " + passiveUser.getProfile().getUsername());
        otherInvTitle.setText(passiveUser.getProfile().getUsername() + "/'s Inventory:");

        offerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                offer.remove(skill);
                yourInv.add(skill);

                offerAdapter.notifyDataSetChanged();
                yourInvAdapter.notifyDataSetChanged();
            }
        });

        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                request.remove(skill);
                otherInv.add(skill);

                requestAdapter.notifyDataSetChanged();
                otherInvAdapter.notifyDataSetChanged();
            }
        });

        yourInvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                yourInv.remove(skill);
                offer.add(skill);

                offerAdapter.notifyDataSetChanged();
                yourInvAdapter.notifyDataSetChanged();
            }
        });

        otherInvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                otherInv.remove(skill);
                request.add(skill);

                requestAdapter.notifyDataSetChanged();
                otherInvAdapter.notifyDataSetChanged();
            }
        });

        loadItems();

        offerList.setAdapter(offerAdapter);
        yourInvList.setAdapter(yourInvAdapter);
        requestList.setAdapter(requestAdapter);
        otherInvList.setAdapter(otherInvAdapter);

        offerAdapter.notifyDataSetChanged();
        yourInvAdapter.notifyDataSetChanged();
        requestAdapter.notifyDataSetChanged();
        otherInvAdapter.notifyDataSetChanged();
    }

    private void loadItems() {
        DatabaseController.refresh();

        // Fill four Lists
        yourInv.addAll(activeUser.getInventory().cloneSkillz(MasterController.getUserDB()));
        otherInv.addAll(passiveUser.getInventory().cloneSkillz(MasterController.getUserDB()));
        //offer and request begin empty

        offerAdapter = new TradeAdapter(this, offer);
        requestAdapter = new TradeAdapter(this, request);
        yourInvAdapter = new TradeAdapter(this, yourInv, true);
        otherInvAdapter = new TradeAdapter(this, otherInv, true);
    }

    public void deleteRequest(View view){
        //TODO delete the trade
        Context context = getApplicationContext();
        Toast.makeText(context, "Deleted your request", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(EditTradeActivity.this, ProfileActivity.class);
        startActivity(intent);*/
    }

    public void sendTrade(View view){
        //TODO Send The Trade
        Trade trade = activeUser.getTradeList().createTrade(MasterController.getUserDB(), activeUser, passiveUser, offer, request);

        Context context = getApplicationContext();
        Toast.makeText(context, "Trade Request Sent", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(EditTradeActivity.this, ProfileActivity.class);
        startActivity(intent);*/
    }
}
