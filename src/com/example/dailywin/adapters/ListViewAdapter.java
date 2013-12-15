package com.example.dailywin.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.dailywin.R;

/**
 * Created by Uros on 15.12.13..
 */
public class ListViewAdapter extends SimpleCursorAdapter {
    private int mSelectedPosition;
    private final Context context;
    private final int layout;

    public ListViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Cursor c = getCursor();

        final LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(layout, parent, false);


//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView Labela = (TextView) rowView.findViewById(R.id.listItemLabel);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        Labela.setText(c.getString(1));
        // Change the icon for Windows and iPhone
        int s = c.getInt(7);
        if (s == 1) {
            imageView.setImageResource(R.drawable.checked);
        } else {
            imageView.setImageResource(R.drawable.yogaicon);
        }

        return rowView;
    }

    @Override
    public void bindView(View rowView, Context context, Cursor c) {

        TextView Labela = (TextView) rowView.findViewById(R.id.listItemLabel);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        Labela.setText(c.getString(1));

        //name_text.setTextColor(Color.GREEN);

        int position = c.getPosition();
        // Change the icon for Windows and iPhone
        int s = c.getInt(7);
        if (s == 1) {
            imageView.setImageResource(R.drawable.checked);
        } else {
            imageView.setImageResource(R.drawable.yogaicon);
        }

    }


    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();

    }
}
