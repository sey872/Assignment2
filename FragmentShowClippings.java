package com.example.scott.assignment2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Scott on 5/5/2016.
 * For CSCI342 Assignment 2
 */
public class FragmentShowClippings extends Fragment implements AdapterView.OnItemClickListener
{
    private ScrapbookModel db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_show_clippings, container, false);

        db = new ScrapbookModel(v.getContext());

        final int collection = Integer.parseInt(this.getArguments().getString("message"));
        List<Clipping> clipList;

        if(collection == 0)
        {
            clipList = db.getAllClippings();
        }
        else {
            clipList = db.getClipingsForCollection(collection);
        }

        TextView myTextView = (TextView) v.findViewById(R.id.collectionNum);

        myTextView.setText("Collection: " + collection);

        final ListView myListView = (ListView) v.findViewById(R.id.listView_clip);

        ListAdapter testListAdapter = new ListAdapter(v.getContext(), R.layout.item_row, clipList);

        myListView.setAdapter(testListAdapter);

        myListView.setOnItemClickListener(this);

        final EditText clipName = (EditText)v.findViewById(R.id.collectionSearch);

        clipName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newName = clipName.getText().toString();
                List<Clipping> test;
                if(collection == 0) {
                    test = db.getClippingWithNote(newName);
                }
                else
                {
                    test = db.getClippingWithNote(newName, collection);
                }

                ListAdapter change = new ListAdapter(getActivity().getApplicationContext(), R.layout.item_row, test);
                myListView.setAdapter(change);
            }
        });
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FragmentDisplayClipping F3 = new FragmentDisplayClipping();
        Bundle bundle = new Bundle();
        Clipping test2 = db.getClipping(position);
        Log.d("test2 was", String.valueOf(test2));
        String msg = String.valueOf(test2.getImg());
        bundle.putString("pic", msg);
        F3.setArguments(bundle);

        if(getFragmentManager().findFragmentById(R.id.fr_display_clip) == null)
        {
            FT.add(R.id.fr_display_clip, F3);
            FT.addToBackStack("f3");
        }
        FT.commit();
    }
}


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
