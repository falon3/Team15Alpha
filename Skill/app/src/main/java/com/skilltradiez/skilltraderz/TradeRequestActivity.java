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

/**
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire trade request process that the user will need to interact with
 * through our application.
 */

public class TradeRequestActivity extends GeneralMenuActivity {
    static String TRADE_ID_PARAM = "trade_id",
                ACTIVE_USER_ID_PARAM = "user_id",
                PASSIVE_USER_ID_PARAM = "puser_id";
    private Context tradeContext = this;
    private Button sendTrade, counterTrade, cancelTrade;
    private TextView tradeTitle, offerer, requestee;
    private ListView offerList, requestList;
    private ListAdapter offerAdapter, requestAdapter;
    private Trade trade;
    private User activeUser, passiveUser;
    private List<Stringeable> offer, request;

    /**
     * Android Standard onCreate method, involved in setting up most of the UI aspects of
     * the application.
     * @param savedInstanceState Bundle Object.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request);

        // Check Intent for Extras
        masterController = new MasterController();

        // Get active and passive Users
        offer = new ArrayList<Stringeable>();
        request = new ArrayList<Stringeable>();

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

    /**
     * Android Standard onStart method, involved in setting up the UI elements for the
     * application to run in this Activity.
     */
    @Override
    public void onResume(){
        super.onResume();

        DatabaseController.refresh();

        Bundle profileExtras = getIntent().getExtras();
        ID tradeID = (ID)profileExtras.get(TRADE_ID_PARAM);
        trade = DatabaseController.getTradeByID(tradeID);
        ID userID = (ID)profileExtras.get(ACTIVE_USER_ID_PARAM);
        activeUser = DatabaseController.getAccountByUserID(userID);
        userID = (ID)profileExtras.get(PASSIVE_USER_ID_PARAM);
        passiveUser = DatabaseController.getAccountByUserID(userID);

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

        //Init Lists
        offer.clear();
        request.clear();
        offer.addAll(masterController.getStringeableSkillList(trade.getHalfForUser(activeUser).getOffer()));
        request.addAll(masterController.getStringeableSkillList(trade.getHalfForUser(passiveUser).getOffer()));
        offerAdapter.notifyDataSetChanged();
        requestAdapter.notifyDataSetChanged();

        if (trade.getHalfForUser(activeUser).isAccepted())
            sendTrade.setVisibility(View.INVISIBLE);
        else
            sendTrade.setVisibility(View.VISIBLE);
    }

    /**
     * This method takes in a Skill Object and will start a new SkillDescriptionActivity
     * Intent with the skill when clicked.
     * @param skill Skill Object.
     */
    public void clickOnSkill(Skill skill) {
        Intent intent = new Intent(this, SkillDescriptionActivity.class);
        intent.putExtra(SkillDescriptionActivity.ID_PARAM, skill.getSkillID());
        startActivity(intent);
    }

    /**
     * Delete the current Request from the application.
     * Provides an adorable little toast message to confirm success!
     * @param view View Object.
     */
    public void deleteRequest(View view){
        masterController.deleteTrade(trade);

        Context context = getApplicationContext();
        Toast.makeText(context, "Deleted your request", Toast.LENGTH_SHORT).show();

        finish();
    }

    /**
     * Begins a new activity for the user of the application to go to the EditTradeActivity page,
     * as a plethora of extra information passed along to this activity to allow specificity.
     * @param view View Object.
     */
    public void counterRequest(View view) {
        Intent intent = new Intent(tradeContext, EditTradeActivity.class);
        intent.putExtra(EditTradeActivity.ACTIVE_PARAM, activeUser.getUserID());
        intent.putExtra(EditTradeActivity.PASSIVE_PARAM, passiveUser.getUserID());
        intent.putExtra(EditTradeActivity.TRADE_ID_PARAM, trade.getTradeID());
        startActivity(intent);
    }

    /**
     * Accept a Trade Request.
     * @param view View Object.
     */
    public void acceptRequest(View view){
        trade.getHalfForUser(activeUser).setAccepted(true);
        trade.checkIfComplete();
        DatabaseController.save();

        Context context = getApplicationContext();
        Toast.makeText(context, "Traded With "+ passiveUser.getProfile().getName(), Toast.LENGTH_SHORT).show();

        finish();
    }
}
