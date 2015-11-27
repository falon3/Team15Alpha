package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Stephen on 2015-11-27.
 */
public class ImageAdapter extends ArrayAdapter<Image> {
    private final CameraActivity context;
    private final List<Image> items;
    private Bitmap lastImage;

    public ImageAdapter(CameraActivity context, List<Image> itemList) {
        super(context, R.layout.image, itemList);
        this.context = context;
        this.items = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Image item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.img = (ImageView) view.findViewById(R.id.image);
            viewHolder.retake = (ImageButton) view.findViewById(R.id.retake);
            viewHolder.cancel = (ImageButton) view.findViewById(R.id.remove);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.img.setImageBitmap((Bitmap)item.getBitmap());
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

    // Acting As A Struct
    public class ViewHolder {
        ImageView img;
        ImageButton retake;
        ImageButton cancel;
    }
}
