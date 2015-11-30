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

    public TradeAdapter(Activity context, List<Skill> itemList) {
        super(context, R.layout.trade_offer, itemList);
        this.context = context;
        this.items = itemList;
        offer = true;
    }

    @SuppressWarnings("unused") // boolean is used to identify arrow direction
    public TradeAdapter(Activity context, List<Skill> itemList, boolean inventory) {
        super(context, R.layout.trade_inv, itemList);
        this.context = context;
        this.items = itemList;
        offer = false;
    }

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

    // Acting As A Struct
    public class ViewHolder {
        TextView name;
        TextView desc;
    }
}
