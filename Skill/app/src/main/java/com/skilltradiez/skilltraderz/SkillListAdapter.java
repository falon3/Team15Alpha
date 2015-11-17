package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stephen on 2015-11-07.
 */
/*
public class SkillListAdapter extends ArrayAdapter<Skill> {
    private final Activity context;
    private final List<Skill> skillz;

    public SkillListAdapter(Activity context, List<Skill> skills) {
        super(context, R.layout.list_item, skills);
        this.context = context;
        this.skillz = skills;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        /*LayoutInflater inflater = context.getLayoutInflater();
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
/*

        Skill skill = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.desc = (TextView) view.findViewById(R.id.desc);

            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(skill.getName());
        viewHolder.desc.setText(skill.getDescription());
        viewHolder.img.setImageResource(skill.getImage());
        // Return the completed view to render on screen
        return view;
    }

    // Acting Struct
    public class ViewHolder {
        TextView name;
        TextView desc;
        ImageView img;
    }
}
*/