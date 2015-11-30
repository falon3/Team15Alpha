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
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerAdapter<T extends Object> extends ArrayAdapter {
    private Context context;
    private List<T> itemList;
    SpinnerAdapter(Context context, T itemList[]) {
        this(context, Arrays.asList(itemList));
    }

    SpinnerAdapter(Context context, List<T> itemList) {
        super(context, R.layout.spinner_item, itemList);
        this.context = context;
        this.itemList = itemList;

        setDropDownViewResource(R.layout.spinner_item);
    }

    /**
     * Returns a View with a drop down view, specifically by invoking the getView method and
     * returning the result of that method.
     * @param position Integer value.
     * @param convertView View Object.
     * @param parent ViewGroup Object.
     * @return View Object.
     */
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    /**
     * Takes in a set of parameters and greates a new specialized UI view for the dropdown view.
     * @param position Integer value.
     * @param view View Object.
     * @param parent ViewGroup Object.
     * @return View Object.
     */
    public View getView(int position, View view, ViewGroup parent) {
        T item = itemList.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.spinner_item, parent, false);
            viewHolder.item = (TextView) view.findViewById(R.id.item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.item.setText(item.toString());
        return view;
    }

    /**
     * This class is acting in a similar manner to a struct here.
     */
    public class ViewHolder {
        TextView item;
    }
}
