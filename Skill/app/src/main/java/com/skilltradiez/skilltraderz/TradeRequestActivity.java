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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TradeRequestActivity extends ActionBarActivity {

    private Context tradeContext = this;

    private Button acceptRequest;
    private Button deleteRequest;
    private Button counterOffer;
    private String tradeTitle;
    private TextView tradeDescription;
    private ListView skillsInTrade;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request);
    }

    @Override
    public void onStart(){
        super.onStart();

        acceptRequest = (Button) findViewById(R.id.acceptTrade);
        deleteRequest = (Button) findViewById(R.id.deleteTrade);
        counterOffer = (Button) findViewById(R.id.counterTrade);
        tradeDescription = (TextView) findViewById(R.id.trade_description);
        skillsInTrade = (ListView) findViewById(R.id.skillListInTrade);
        tradeTitle = "";


    }

    /**
     * Accept a trade. Sends a message to the user making the request that you are happy with the
     * terms of the request. Will add the trade to your history
     */
    public void acceptTrade(){
        //Alert dialog that you have accepted the request. Sends a notification to the requester
        //Add the trade to your history
        Toast toast = Toast.makeText(tradeContext, "Accepted a Trade", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Delete a request.
     * @ TODO:
     */
    public void deleteRequest(){
        // alert dialogue for making sure that you want to delete the request. Confirmation.
        Toast toast = Toast.makeText(tradeContext, "Deleted a Request", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * counter offer. Takes you to the Edit Trade Request screen
     * @ TODO:
     */
    public void counterOffer(View view){
        Intent intent = new Intent(tradeContext, EditTradeActivity.class);
        startActivity(intent);
        //need to probably put an intent.putextra with ... info
    }

    /**
     * Set the details of the trade offer.
     * @ TODO:
     */
    public void setTradeDetails(){
        //need a list of skills relevant to the trade and then populate the view
        //skillsInTrade = something based on other things
    }

    /**
     * Set the notes of the trade as given by the trade requester
     * @ TODO:
     */
    public void setTradeNotes(){
        //need to get the notes from the requester to populate this field
        // tradeDescription = something
    }

    /**
     * Who you're trading with
     * @ TODO:
     */
    public void setTradeTitle(){
        //tradeTitle = who you're trading with
    }

}
