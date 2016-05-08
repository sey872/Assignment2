package com.example.scott.assignment2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ScrapbookModel db;
    private ListView l;

    private final int SELECT_PHOTO = 1;
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ScrapbookModel db = new ScrapbookModel(this);

        Log.d("DROP", "DROPPING TABLES");
        db.dropAll();


        db.addCollection(new Collection(db.getCollectionCount(), "People"));
        db.addCollection(new Collection(db.getCollectionCount(), "Places"));
        db.addCollection(new Collection(db.getCollectionCount(), "Things"));

        //setup data for array adapter
        List<Collection> tempList = db.getAllCollections();
        List<String> colList = new ArrayList<>();
        for(Collection col : tempList)
        {
            colList.add(col.getName());
        }

        db.addClipping(new Clipping(db.getClippingsCount(), R.drawable.lake, "My notes are great", 1));
        db.addClipping(new Clipping(db.getClippingsCount(), R.drawable.question, "Atleast you can test", 2));
        db.addClipping(new Clipping(db.getClippingsCount(), R.drawable.cathedral, "Maybe font size is too big", 3));
        //setup list
        l = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colList);
        l.setAdapter(adapter);
        l.setOnItemClickListener(this);

        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FragmentCollectionCreate F1 = new FragmentCollectionCreate();

        if(getFragmentManager().findFragmentById(R.id.fr_id) == null)
        {
            FT.add(R.id.fr_id, F1);
        }
        FT.commit();

        //runQueries();
        //runTests();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FragmentShowClippings F2 = new FragmentShowClippings();

        Bundle bundle = new Bundle();
        String msg = String.valueOf(position);
        bundle.putString("message", msg);
        F2.setArguments(bundle);

        if(getFragmentManager().findFragmentById(R.id.fr_show_clip) == null)
        {
            FT.add(R.id.fr_show_clip, F2);
            FT.addToBackStack("f2");
        }
        FT.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void runQueries()
    {
        /* * * * QUERY 1 * * * */
        // Insert into database
        Log.d("Insert", "Inserting: ");
        db.addCollection(new Collection(db.getCollectionCount(), "People"));
        db.addCollection(new Collection(db.getCollectionCount(), "Places"));
        db.addCollection(new Collection(db.getCollectionCount(), "Things"));

        /* * * * QUERY 2 * * * */
        List<Collection> colList = db.getAllCollections();
        printCollections(colList);


        /* * * * QUERY 3 * * * */
        //Insert Clippings into the database
        db.addClipping(new Clipping(0, R.drawable.testimg, "space pictureno", 0));
        db.addClipping(new Clipping(1, R.drawable.testimg, "dace pictureno", 0));
        db.addClipping(new Clipping(2, R.drawable.testimg, "mace pictureno", 0));
        db.addClipping(new Clipping(3, R.drawable.testimg, "pace pictureno", 0));
        db.addClipping(new Clipping(4, R.drawable.testimg, "lace pictureno", 2));
        db.addClipping(new Clipping(5, R.drawable.testimg, "space pictureno", 2));

        /* * * * QUERY 4 * * * */
        List<Clipping> clipList = db.getClipingsForCollection(0);
        printClippings(clipList);

        /* * * * QUERY 5 * * * */
        db.addClippingtoCollection(4, 0);
        clipList = db.getClipingsForCollection(0);
        printClippings(clipList);

        /* * * * QUERY 6 * * * */
        db.deleteCollection(new Collection(1, "Places"));
        colList = db.getAllCollections();
        printCollections(colList);

        /* * * * QUERY 7 * * * */
        db.deleteClipping(new Clipping(2, R.drawable.testimg, "mace pictureno", 0));
        clipList = db.getClipingsForCollection(0);
        printClippings(clipList);

        /* * * * QUERY 8 * * * */
        //pass note to find, not it is case insensitive
        clipList = db.getClippingWithNote("SpAcE");
        printClippings(clipList);

        /* * * * QUERY 9 * * * */
        //pass both note to find and collection id
        clipList = db.getClippingWithNote("space", 0);
        printClippings(clipList);
    }

    public void runTests()
    {
        /* * * * Testing Database * * * */
        List<Clipping> clipList;
        List<Collection> colList;
        //  TEST 1
        Log.d("TEST 1", "**************TEST 1********************");
        db = new ScrapbookModel(this);
        db.dropAll();

        //  TEST 2
        Log.d("TEST 2", "**************TEST 2********************");
        Collection X = new Collection(db.getCollectionCount(), "Deadzone");
        Collection A = new Collection(db.getCollectionCount(), "A");
        Collection B = new Collection(db.getCollectionCount(), "B");
        db.addCollection(A);
        db.addCollection(B);

        //  TEST 3
        Log.d("TEST 3", "**************TEST 3********************");
        Clipping one = new Clipping(0, R.drawable.testimg, "foo", X.getId());
        Clipping two = new Clipping(1, R.drawable.testimg, "foo", X.getId());
        Clipping three = new Clipping(2, R.drawable.testimg, "bar", X.getId());
        db.addClipping(one);
        db.addClipping(two);
        db.addClipping(three);

        // TEST 4
        Log.d("TEST 4", "**************TEST 4********************");
        colList = db.getAllCollections();
        printCollections(colList);

        //  TEST 5
        Log.d("TEST 5", "**************TEST 5********************");
        clipList = db.getAllClippings();
        printClippings(clipList);

        // TEST 6
        Log.d("TEST 6", "**************TEST 6********************");
        db.addClippingtoCollection(one.getId(), A.getId());
        db.addClippingtoCollection(two.getId(), A.getId());

        //  TEST 7
        Log.d("TEST 7", "**************TEST 7********************");
        clipList = db.getClipingsForCollection(A.getId());
        printClippings(clipList);

        //  TEST 8
        Log.d("TEST 8", "**************TEST 8********************");
        db.deleteClipping(one);

        //  TEST 9
        Log.d("TEST 9", "**************TEST 9********************");
        clipList = db.getClipingsForCollection(A.getId());
        printClippings(clipList);

        //  TEST 10
        Log.d("TEST 10", "**************TEST 10********************");
        clipList = db.getClippingWithNote("bar");
        printClippings(clipList);
    }

    public void printCollections(List<Collection> colList)
    {
        Log.d("CollectionsStart ", "*****************************");
        // Read from database
        Log.d("Collections ", "Collections: ");
        Log.d("Collections ", "\tNumber: " + String.valueOf(colList.size()));
        for(Collection col : colList)
        {
            String log = "\t- Id: " + col.getId() + " , Name: " + col.getName();
            // Show log
            Log.d("Collection ", log);
        }
        Log.d("CollectionsEnd   ", "*****************************");
    }

    public void printClippings(List<Clipping> clipList)
    {
        Log.d("ClipStart        ", "*****************************");
        Log.d("ClipList ", "Collection: ");
        Log.d("ClipList ", "\tSize: " + String.valueOf(clipList.size()));

        for(Clipping clip : clipList)
        {
            String log = "\t- Id: " + clip.getId() + ", Image: " + clip.getImg() + ", Notes: " + clip.getNotes() + ", Date Created: " + clip.getDate() + ",  Rid: " + clip.getRid();
            // Show log
            Log.d("Clip ", log);
        }
        Log.d("ClipEnd          ", "*****************************");
    }
}
