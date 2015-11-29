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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire trade request process that the user will need to interact with
 * through our application.
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
 * 1: ACCEPTTRADE:
 *     This is going to be the method called when the user interacts with the UI where the user
 *     actually is going to accept a trade that is presented to them. When this is hit
 *     a series of steps will be activated in which the trade will actually be accepted by the user.
 *
 *
 *  2: DELETEREQUEST:
 *      When the user actually wants to DELETE a request they can interact with the UI and then
 *      this method will be called. This will outright remove the request of trading from the
 *      user's application permanently. Fabulous.
 *
 * 3: COUNTEROFFER:
 *     Suppose a user isn't happy with what they currently are seeing/offering for a particular
 *     trade in our application, interacting with the UI will allow us to have a counteroffer
 *     method called (this method) that will allow the user to edit their trade agreement in
 *     the application in a glorious and beautiful fashion!
 *     MAJESTIC! AS! HECK!
 *
 * 4: SETTRADEDETAILS:
 *     When a user interacts with their application, it is stupidly essential to give the user
 *     the DETAILS of a particular trade that they are performing on. This method is called when
 *     the user interacts with the UI in a fashion that allows the user to view the details
 *     of a given trade.
 *
 *
 * 5: SETTRADENOTES:
 *     This is going to obtain the trade notes from the user and then will present them to the
 *     user of the application once the application framework is prompted to actually go
 *     through these series of steps!
 *
 *
 * 6: SETTRADETITLE:
 *     This is going to be more "UI related" but it also has the effect that we now have some
 *     sort of special unique identifier (aside from ID) that the user can relate to when they are
 *     dealing with the entire trade process. This gives the user that power to set the
 *     title of the trade! GLORIOUS!
 */

//TODO EVERYTHING: this is more the result, not the action; of trading
public class TradeRequestActivity extends GeneralMenuActivity {
    static String TRADE_ID_PARAM = "trade_id",
                ACTIVE_USER_ID_PARAM = "user_id";
    private Context tradeContext = this;

    private Button sendTrade, counterTrade, cancelTrade;
    private TextView tradeTitle, offerer, requestee;
    private ListView offerList, requestList;

    private ListAdapter offerAdapter, requestAdapter;

    private Trade trade;
    private User activeUser, passiveUser;
    private List<Stringeable> offer, request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request);

        // Check Intent for Extras
        masterController = new MasterController();
        Bundle profileExtras = getIntent().getExtras();

        // Get active and passive Users
        ID tradeID = (ID)profileExtras.get(TRADE_ID_PARAM);
        trade = DatabaseController.getTradeByID(tradeID);
        ID userID = (ID)profileExtras.get(ACTIVE_USER_ID_PARAM);
        activeUser = DatabaseController.getAccountByUserID(userID);

        //Init Lists
        offer = masterController.getStringeableSkillList(trade.getHalfForUser(activeUser).getOffer());
        request = masterController.getStringeableSkillList(trade.getHalfForUser(passiveUser).getOffer());

        // Get UI elements
        sendTrade = (Button) findViewById(R.id.sendTrade);
        counterTrade = (Button) findViewById(R.id.counterTrade);
        cancelTrade = (Button) findViewById(R.id.deleteTrade);

        tradeTitle = (TextView) findViewById(R.id.trading_with);
        offerer = (TextView) findViewById(R.id.offering);
        requestee = (TextView) findViewById(R.id.requesting);

        offerList = (ListView) findViewById(R.id.offerList);
        requestList = (ListView) findViewById(R.id.requestList);

        offerAdapter = new ListAdapter(this, offer);
        requestAdapter = new ListAdapter(this, request);
    }

    @Override
    public void onStart(){
        super.onStart();
        tradeTitle.setText("Trading With " + passiveUser.getProfile().getUsername());

        offerList.setAdapter(offerAdapter);
        requestList.setAdapter(requestAdapter);

        offerAdapter.notifyDataSetChanged();
        requestAdapter.notifyDataSetChanged();

        offerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                clickOnSkill(skill);
            }
        });

        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                clickOnSkill(skill);
            }
        });

        if (!trade.isActive()) {
            sendTrade.setVisibility(View.INVISIBLE);
            cancelTrade.setVisibility(View.INVISIBLE);

            counterTrade.setEnabled(false);
            counterTrade.setText("TRADE COMPLETE");
        }
    }

    public void clickOnSkill(Skill skill) {
        Intent intent = new Intent(this, SkillDescriptionActivity.class);
        intent.putExtra(SkillDescriptionActivity.ID_PARAM, skill.getSkillID());
        startActivity(intent);
    }

    public void deleteRequest(View view){
        masterController.deleteTrade(trade.getTradeID());

        Context context = getApplicationContext();
        Toast.makeText(context, "Deleted your request", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void counterRequest(View view) {
        Intent intent = new Intent(tradeContext, EditTradeActivity.class);
        intent.putExtra(EditTradeActivity.ACTIVE_PARAM, activeUser.getUserID());
        intent.putExtra(EditTradeActivity.PASSIVE_PARAM, passiveUser.getUserID());
        intent.putExtra(EditTradeActivity.TRADE_ID_PARAM, trade.getTradeID());
        startActivity(intent);
    }

    public void acceptRequest(View view){
        trade.getHalfForUser(activeUser).setAccepted(true);
        trade.checkIfComplete();

        Context context = getApplicationContext();
        Toast.makeText(context, "Traded With "+ passiveUser.getProfile().getName(), Toast.LENGTH_SHORT).show();

        finish();
    }
}
