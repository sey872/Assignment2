package com.example.scott.assignment2;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 8/5/2016.
 * For CSCI342 Assignment 2
 */
public class ListAdapter extends ArrayAdapter<Clipping> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Clipping> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_row, null);
        }

        Clipping p = getItem(position);

        if (p != null) {
            ImageView tt1 = (ImageView) v.findViewById(R.id.row_Image);
            TextView tt2 = (TextView) v.findViewById(R.id.row_Note);

            if (tt1 != null) {
                tt1.setImageResource(p.getImg());
            }

            if (tt2 != null) {
                tt2.setText(p.getNotes());
            }
        }

        if (v == null)
        {
            Log.d("hi", "wtf now");
        }

        return v;
    }

}