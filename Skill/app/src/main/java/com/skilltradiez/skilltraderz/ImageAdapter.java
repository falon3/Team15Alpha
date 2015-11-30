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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Image> {
    /**Class Variables:
     * 1: context, a CameraActivity Object will be passed to this ImageAdapter class and this variable
     *     will maintain the record of this. Very core to the functioning of our UI in this class.
     */
    private final CameraActivity context;

    /**CONSTRUCTOR:
     * This constructor will take in a CameraActivity and a list of items and then we will
     * assign (based upon the superclass that this class inherits from) the superclass of
     * ArrayAdapter(Image) (Please note these brackets are meant to be < > ). We then finally
     * assign the context of this object to be equal to the context that is passed into this
     * constructor.
     * @param context CameraActivity Object
     * @param itemList List of Image Objects
     */
    public ImageAdapter(CameraActivity context, List<Image> itemList) {
        super(context, R.layout.image_view, itemList);
        this.context = context;
    }

    /**
     * Given a position, view and parent- make a new View for the ImageAdapter Object.
     * @param position Integer value for position.
     * @param view View Object
     * @param parent ViewGroup object of the parent
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Image item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.image_view, parent, false);
            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            viewHolder.retake = (ImageButton) view.findViewById(R.id.retake);
            viewHolder.cancel = (ImageButton) view.findViewById(R.id.remove);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.img.setImageBitmap(item.getBitmap());
        viewHolder.retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.retakeImage(v, item);
            }
        });
        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteImage(v, item);
            }
        });
        return view;
    }

    /**
     * ViewHolder is in reality acting similar to how a struct in C works. This class
     * when created is going to hold three units of data I will explain here:
     *
     * 1: img, this is going to be a particular ImageView assoicated with a UI image.
     * 2: retake, this is going to be a button allowing the user to retake the image.
     * 3: cancel, if the user doesn't want to continue they can hit this to be finished.
     */
    public class ViewHolder {
        ImageView img;
        ImageButton retake;
        ImageButton cancel;
    }
}
