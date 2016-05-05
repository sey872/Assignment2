package com.example.scott.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image = (ImageView) findViewById(R.id.test_image);

        ScrapbookModel db = new ScrapbookModel(this);

        Log.d("DROP", "DROPING TABLES");
        db.dropAll();

        /* * * * QUERY 1 * * * */
        // Insert into database
        Log.d("Insert", "Inserting: ");
        db.addCollection(new Collection(0, "People"));
        db.addCollection(new Collection(1, "Places"));
        db.addCollection(new Collection(2, "Things"));

        /* * * * QUERY 2 * * * */
        // Read from database
        Log.d("Reading", "Collections: ");
        Log.d("\tCollection size", "Size: " + String.valueOf(db.getCollectionCount()));
        List<Collection> colList = db.getAllCollections();
        for(Collection col : colList)
        {
            String log = "\t-\tId: " + col.getId() + " , Name: " + col.getName();
            // Show log
            Log.d("Collection: ", log);
        }


        /* * * * QUERY 3 * * * */
        //Insert Clippings into the database
        db.addClipping(new Clipping(0, "Bob", R.id.test_image, "space pictureno", 0));
        db.addClipping(new Clipping(1, "Bill", R.id.test_image, "space pictureno", 0));
        db.addClipping(new Clipping(2, "Joe", R.id.test_image, "space pictureno", 0));
        db.addClipping(new Clipping(3, "Bud", R.id.test_image, "space pictureno", 0));
        db.addClipping(new Clipping(4, "House", R.id.test_image, "space pictureno", 2));
        db.addClipping(new Clipping(5, "Door", R.id.test_image, "space pictureno", 2));

        /* * * * QUERY 4 * * * */
        int colID = 0;
        Log.d("Collection "+colID, "Collection " + colID + ": ");
        List<Clipping> col1 = db.getClipingsForCollection(colID);
        for(Clipping clip : col1)
        {
            String log = "\t-\tId: " + clip.getId() + ",  Name: " + clip.getName() + ", Image: " + clip.getImg() + ", Notes: " + clip.getNotes() + ",  Rid: " + clip.getRid();
            // Show log
            Log.d("Clipping: ", "  -  " + log);
        }

    }
}
