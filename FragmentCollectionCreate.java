package com.example.scott.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 5/5/2016.
 * For CSCI342 Assignment 2
 */
public class FragmentCollectionCreate extends Fragment
{
    private int numCollections;
    private ListView l;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_collection_create, container, false);

        final ScrapbookModel db = new ScrapbookModel(getActivity());

        final Button create = (Button)v.findViewById(R.id.createCollection);

        final Button delete = (Button)v.findViewById(R.id.deleteCollection);

        final EditText mytemp = (EditText)v.findViewById(R.id.collectionName);

        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newName = mytemp.getText().toString();

                if(db.getCollection(newName).getId() != 999)
                {
                    Toast.makeText(getActivity(), "Collection with name: " + newName + " already exists.", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addCollection(new Collection(db.getCollectionCount(), newName));

                    List<Collection> tempList = db.getAllCollections();
                    List<String> colList = new ArrayList<>();
                    for (Collection col : tempList) {
                        colList.add(col.getName());
                    }

                    l = (ListView) getActivity().findViewById(R.id.listView);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, colList);
                    l.setAdapter(adapter);
                }
                //getActivity().getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fr_id)).commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newName = mytemp.getText().toString();

                Collection temp = db.getCollection(newName);

                if(temp.getId() == 999)
                {
                    Toast.makeText(getActivity(), "No collection with name: " + newName + " exists.", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.deleteCollection(new Collection((temp.getId()), newName));

                    List<Collection> tempList = db.getAllCollections();
                    List<String> colList = new ArrayList<>();
                    for (Collection col : tempList) {
                        colList.add(col.getName());
                    }

                    l = (ListView) getActivity().findViewById(R.id.listView);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, colList);
                    l.setAdapter(adapter);
                }
            }
        });
        return v;
    }

}
