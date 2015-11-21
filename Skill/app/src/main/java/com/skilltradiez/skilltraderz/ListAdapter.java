package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stephen on 2015-11-07.
 */
public class ListAdapter extends ArrayAdapter<Stringeable> {
    private final Activity context;
    private final List<Stringeable> items;

    public ListAdapter(Activity context, List<Stringeable> itemList) {
        super(context, R.layout.list_item, itemList);
        this.context = context;
        this.items = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        /* TODO FIGURE THIS OUT
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        Skill here = skillz.get(position);

        txtTitle.setText(here.getName());//.toString());

        // If we have received an array of ints
        //imageView.setImageResource(imageId[position]);
        // or If Drawable has been set by constructor
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.iconshape));
        // or a better If Drawable has been set by constructor
        imageView.setImageResource(here.getImage());
        return rowView;*/

        Stringeable item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
            /*viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.desc = (TextView) view.findViewById(R.id.desc);

            viewHolder.img = view.findViewById(R.id.img);
            view.setTag(viewHolder);*/
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(item.getName());
        viewHolder.desc.setText(item.getDescription());
        //TODO IMAGES
        // viewHolder.img.setImageResource(item.getImage());
        //viewHolder.img.setBackground(item.getImage());
        // Return the completed view to render on screen
        return view;
    }

    // Acting As A Struct
    public class ViewHolder {
        TextView name;
        TextView desc;
        View img;
    }
}