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
 * When we have our application, we care about activities that have occured that were relatively
 * recent in time. This class is a mostly UI-centric effort to establish an Adapter that can
 * be utilized throughout the application for Recent Activities.
 */
public class RecentActivityAdapter extends ArrayAdapter<Notification> {
    private final Activity context;
    private final List<Notification> items;

    /**
     * This constructor will assign the context and List of Notification Objects from the
     * parameters passed into this constructor.
     * @param context Activity.
     * @param itemList List of Notification Objects.
     */
    public RecentActivityAdapter(Activity context, List<Notification> itemList) {
        super(context, R.layout.recent_act_item, itemList);
        this.context = context;
        this.items = itemList;
    }

    /**
     * This method is involved in setting up the UI elements on the android applcation in such a
     * fashion that they may appropriately represent the most recent activities that have
     * occured with our application.
     * @param position Integer of position on the User Interface.
     * @param view View Object.
     * @param parent ViewGroup Object.
     * @return View Object.
     */
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

    /**
     * This ViewHolder class is acting similar to a struct, holding TextView variables for name,
     * desceiption, and status.
     */
    public class ViewHolder {
        TextView name;
        TextView desc;
        TextView status;
    }
}
