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
/**
 * This class is based upon the premise that Trades are a little involved to implement, so we're
 * better off in the long run to have the TradeAdapter class stated here for simplicity's sake so
 * that implementing an adapter for Trades is less intensive elsewhere.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TradeAdapter extends ArrayAdapter<Skill> {
    private final Activity context;
    private final List<Skill> items;
    private final boolean offer;

    /**
     * Accepts an activity that the TradeAdapter is working on and a List of Skill Objects in order
     * to flesh out some useful UI elements. Also assigns the basic boolean "offer" to be true,
     * as in we will have this TradeAdapter have an offer associated with it.
     * @param context Activity Class.
     * @param itemList List of Skill Objects.
     */
    public TradeAdapter(Activity context, List<Skill> itemList) {
        super(context, R.layout.trade_offer, itemList);
        this.context = context;
        this.items = itemList;
        offer = true;
    }

    /**
     * This constructor takes in one more parameter than the one above, the inventory boolean.
     * @param context Activity Class.
     * @param itemList List of Skill Objects.
     * @param inventory BOOLEAN (NO! NOT AN INVENTORY OBJECT!)
     */
    @SuppressWarnings("unused") // boolean is used to identify arrow direction
    public TradeAdapter(Activity context, List<Skill> itemList, boolean inventory) {
        super(context, R.layout.trade_inv, itemList);
        this.context = context;
        this.items = itemList;
        offer = false;
    }

    /**
     * This method is a UI heavy caretaker that will handle all of the specific details of setting
     * up the TradeAdapter with a View- and will return the View after it is finished.
     * @param position Integer of position on UI.
     * @param view View Object.
     * @param parent ViewGroup Object.
     * @return View Object.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Skill item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (offer)
                view = inflater.inflate(R.layout.trade_offer, parent, false);
            else
                view = inflater.inflate(R.layout.trade_inv, parent, false);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.desc = (TextView) view.findViewById(R.id.desc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(item.getName());
        viewHolder.desc.setText(item.getCategory());
        return view;
    }

    /**
     * This class is acting as if it were a struct. Holding the variables name and desc that are
     * both Textview variables.
     */
    public class ViewHolder {
        TextView name;
        TextView desc;
    }
}
