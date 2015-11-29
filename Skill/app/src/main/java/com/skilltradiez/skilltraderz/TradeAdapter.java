package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stephen on 2015-11-27.
 */
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
