package com.example.scott.assignment2;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Scott on 5/5/2016.
 * For CSCI342 Assignment 2
 */
public class FragmentDisplayClipping extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_display_clipping, container, false);

        final ScrapbookModel db = new ScrapbookModel(v.getContext());

        int pic = Integer.parseInt(this.getArguments().getString("pic"));

        ImageView img = (ImageView) v.findViewById(R.id.image);

        img.setImageResource(pic);

        return v;
    }
}
