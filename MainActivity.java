package com.example.scott.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);

        Log.d("DROP", "DROPING TABLES");
        db.dropAll();

        // Insert into database
        Log.d("Insert", "Inserting: ");
        db.addCollection(new Collection(0, "People"));
        db.addCollection(new Collection(1, "Places"));
        db.addCollection(new Collection(2, "Things"));

        // Ready from database
        Log.d("Reading", "Reading: ");
        Log.d("Collection size", "Size: " + String.valueOf(db.getCollectionCount()));
        List<Collection> colList = db.getAllCollections();
        for(Collection col : colList)
        {
            String log = "Id: " + col.getId() + " , Name: " + col.getName();
            // Show log
            Log.d("Collection: ", log);
        }
    }
}
