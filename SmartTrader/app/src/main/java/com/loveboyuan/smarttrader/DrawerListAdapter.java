package com.loveboyuan.smarttrader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jiahui on 11/29/15.
 */
public class DrawerListAdapter extends ArrayAdapter<DrawerListEntry> {
    private final Context context;
    private final ArrayList<DrawerListEntry> drawerListEntryArrayList;

    public DrawerListAdapter(Context context, ArrayList<DrawerListEntry> drawerListEntryArrayList) {

        super(context, R.layout.list_entry, drawerListEntryArrayList);

        this.context = context;
        this.drawerListEntryArrayList = drawerListEntryArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        if (!drawerListEntryArrayList.get(position).isHeader()) {
            rowView = inflater.inflate(R.layout.list_entry, parent, false);

            // 3. Get icon,title & counter views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

            // 4. Set the text for textView
            imgView.setImageResource(drawerListEntryArrayList.get(position).getIcon());
            titleView.setText(drawerListEntryArrayList.get(position).getTitle());

        } else {
            rowView = inflater.inflate(R.layout.list_header_entry, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.header);
            titleView.setText(drawerListEntryArrayList.get(position).getTitle());

        }

        // 5. return rowView
        return rowView;
    }
}
