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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * List adapter is going to be a subclass if the stringable superclass, where the core purpose
 * of this class is to grant us a constructor and various methods for dealing with viewing the
 * data presented. This is achieved by handing this class a particular activity of interest and a
 * list of potential items to be processed by this list.
 *
 * When used, this class will be allowing us to obtain view details to be used in displaying
 * the information on the given activity through it's two methods.
 */
public class ListAdapter extends ArrayAdapter<Stringeable> {
    private final Activity context;
    private final List<Stringeable> items;
    /**
     * Takes in a context and list of items and will assign the ListAdapter Object's variables
     * to these passed in parameters.
     * @param context Activity.
     * @param itemList List of Stringeable Objects.
     */
    public ListAdapter(Activity context, List<Stringeable> itemList) {
        super(context, R.layout.list_item, itemList);
        this.context = context;
        this.items = itemList;
    }

    /**
     * This method will be focused upon setting up the UserInterface elements of this
     * ListAdapter Object for use in other parts of the application.
     * @param position Integer value.
     * @param view View Object.
     * @param parent ViewGroup Object.
     * @return View Object.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Stringeable item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.cat = (TextView) view.findViewById(R.id.category);
            viewHolder.desc = (TextView) view.findViewById(R.id.desc);
            viewHolder.topNum = (TextView) view.findViewById(R.id.topNum);
            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(item.getName());
        viewHolder.cat.setText(item.getCategory());
        viewHolder.desc.setText(item.getDescription());

        String pre="";
        if (item instanceof Skill) {
            pre = "NumOwners:";
        } else if (item instanceof Trade) {
            pre="Other Guy's Successful Trades:";
        } else {
            pre="Successful Trades:";
        }

        if (!(item instanceof Skill))
            viewHolder.topNum.setText(pre+item.getTop());
        if (item.getImage() != null)
            viewHolder.img.setImageBitmap(item.getImage().getBitmap());
        return view;
    }

    /**
     * This class acts as a struct, having four variables that it will bind together.
     * Three TextView objects, the name, description and category. Fourthly, and finally,
     * an ImageView object of the image.
     */
    public class ViewHolder {
        TextView name;
        TextView desc;
        TextView topNum;
        TextView cat;
        ImageView img;
    }
}