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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
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
 */

public class EditTradeActivity extends ButtonMenuActivity {
    /**Activity Class Variables:
     * 1: ACTIVE_PARAM, involved with holding the static string "active_id" which will be utilized
     *     in being passed to methods where we need to differentiate IDs given.
     * 2: PASSIVE_PARAM, similar to ACTIVE_PARAM but has the string "passive_id".
     * 3: TRADE_ID_PARAM, similar to ACTIVE_PARAM and PASSIVE_PARAM above, however this string
     *     will be utilized to uniquely identify Trade IDs being passed.
     * 4: TradeAdapters (Yes, all of them), involved with setting up the UI for this object.
     * 5: ListViews (Yes, all of them), invovled with setting up the List Views used by
     *     this object to represent the UI shown to the user.
     * 6: TextViews (Yes, all of them), involved in representing core textual data to the user
     *     through the UI.
     * 7: Buttons (Yes, all of them), involved in allowing the user a method to interact with
     *     the UI in order to carry out a particular desired task.
     * 8: offer, this is  list of Skill Objects that is involved with maintaining the current
     *     offer of the Trade Object this EditTradeActivity is based upon.
     * 9: yourInv, this list of Skill Objects maintains the current user's inventory.
     * 10: request, this list of Skill Objects contains the list of requests that the user has.
     * 11: otherInvList, this list of Skill Objects contains the Skills the user is offering.
     * 12: activeUser, this User Object is going to be the ACTIVE_PARAM user.
     * 13: passiveUser, this User Object is the PASSIVE_PARAM user.
     * 14: trade, this is going to be the ever-critical Trade Object that is associated with this
     *      EditTradeActivity. (No point in this page if we don't have a Trade, right?)
     */
    static String ACTIVE_PARAM = "active_id",
                PASSIVE_PARAM = "passive_id",
                TRADE_ID_PARAM = "trade_id";
    private TradeAdapter offerAdapter, yourInvAdapter, requestAdapter, otherInvAdapter;
    private ListView offerList, yourInvList, requestList, otherInvList;

    private TextView tradeTitle, otherInvTitle;

    private ArrayList<Skill> offer, yourInv, request, otherInv;
    private User activeUser, passiveUser;
    private Trade trade;

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

        ID tradeID = (ID)profileExtras.get(TRADE_ID_PARAM);
        if (tradeID != null)
            trade = DatabaseController.getTradeByID(tradeID);

        //Init Lists
        offer = new ArrayList<Skill>();
        yourInv = new ArrayList<Skill>();
        request = new ArrayList<Skill>();
        otherInv = new ArrayList<Skill>();

        // Get UI elements
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
        otherInvTitle.setText(passiveUser.getProfile().getUsername() + "'s Inventory:");

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

        activateLeftButton();

        setLeftText("Delete Request");
        setRightText("Send Request");
    }

    /**
     * Refresh the Database, obtain all relevant information from the database and initialize
     * all TradeAdapter objects. Essentially this method is going to be critical to starting
     * up this activity and handling all of the work behind this functioning Activity before it
     * even is functioning.
     */
    private void loadItems() {
        DatabaseController.refresh();

        // Fill two Lists
        yourInv.addAll(activeUser.getInventory().cloneSkillz());
        otherInv.addAll(passiveUser.getInventory().cloneSkillz());
        //offer and request begin empty

        //... unless we are editing an existing trade
        if (trade != null) {
            List<ID> offerids = trade.getHalfForUser(activeUser).getOffer();
            for (ID id : offerids) {
                Skill s = DatabaseController.getSkillByID(id);
                offer.add(s);
                yourInv.remove(s);
            }
            List<ID> requestids = trade.getHalfForUser(passiveUser).getOffer();
            for (ID id : requestids) {
                Skill s = DatabaseController.getSkillByID(id);
                request.add(s);
                otherInv.remove(s);
            }
        }

        offerAdapter = new TradeAdapter(this, offer);
        requestAdapter = new TradeAdapter(this, request);
        yourInvAdapter = new TradeAdapter(this, yourInv, true);
        otherInvAdapter = new TradeAdapter(this, otherInv, true);
    }

    public void clickOnLeftButton(View v) {
        deleteRequest(v);
    }

    /**
     * Given a View Object (to identify the trade), this method will remove the Trade Object
     * associated with the given View Object. It will then display a brief message to the user
     * upon success.
     * @param view View Object of the EditTradeActivity.
     */
    public void deleteRequest(View view){
        if (trade != null)
            masterController.deleteTrade(trade);

        Context context = getApplicationContext();
        Toast.makeText(context, "Deleted your request", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void clickOnRightButton(View v) {
        sendTrade(v);
    }

    /**
     * If the user has at least one skill in their trade, when invoked this method will obtain
     * all relevant information from the database and add this trade to the database. Afterwards
     * saving the database and giving the user a brief conformation message.
     * @param view View Object of the EditTradeActivity.
     */
    public void sendTrade(View view) {
        if (request.size() == 0) {
            Toast.makeText(getApplicationContext(), "You need to request at least one skill!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (trade != null){
            trade.set(masterController.getUserDB(), activeUser, passiveUser, offer, request);
            DatabaseController.addTrade(trade);
        } else {
            trade = activeUser.getTradeList().createTrade(masterController.getUserDB(), activeUser, passiveUser, offer, request);
        }
        MasterController.getCurrentUser().getTradeList().addTrade(MasterController.getUserDB(), trade);
        DatabaseController.save();

        Context context = getApplicationContext();
        Toast.makeText(context, "Trade Request Sent", Toast.LENGTH_SHORT).show();

        finish();
    }
}
