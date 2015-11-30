package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 2015-11-29.
 */
public class RecentActivityAdapter extends ArrayAdapter<Notification> {
    private final Activity context;
    private final List<Notification> items;
    public RecentActivityAdapter(Activity context, List<Notification> itemList) {
        super(context, R.layout.recent_act_item, itemList);
        this.context = context;
        this.items = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Notification item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.recent_act_item, parent, false);
            viewHolder.name = (TextView) view.findViewById(R.id.type);
            viewHolder.status = (TextView) view.findViewById(R.id.status);
            viewHolder.desc = (TextView) view.findViewById(R.id.desc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(item.getType());
        viewHolder.status.setText(item.getStatus());
        viewHolder.desc.setText(item.getDescription());
        return view;
    }

    // Acting As A Struct
    public class ViewHolder {
        TextView name;
        TextView desc;
        TextView status;
    }
}
