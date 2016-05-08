package com.example.scott.assignment2;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Scott on 5/5/2016.
 * For CSCI342 Assignment 2
 */
public class FragmentShowClippings extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_show_clippings, container, false);

        final ScrapbookModel db = new ScrapbookModel(v.getContext());

        //int collection = Integer.parseInt(this.getArguments().getString("message"));
        List<Clipping> clipList;

        /*if(collection == 0)
        {*/
            clipList = db.getAllClippings();
        /*}
        else {
            clipList = db.getClipingsForCollection(collection);
        }*/

        ListView myListView = (ListView) v.findViewById(R.id.listView_clip);

        ListAdapter testListAdapater = new ListAdapter(v.getContext(), R.layout.item_row, clipList);

        myListView.setAdapter(testListAdapater);

       /* getActivity().imageView = (ImageView)v.findViewById(R.id.imageView);

        Button pickImage = (Button) v.findViewById(R.id.btn_pick);
        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }*/

        return v;
    }
}
