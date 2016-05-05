package com.example.scott.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ScrapbookModel db;
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
        List<Collection> colList = db.getAllCollections();
        printCollections(colList);


        /* * * * QUERY 3 * * * */
        //Insert Clippings into the database
        db.addClipping(new Clipping(0, R.id.test_image, "space pictureno", 0));
        db.addClipping(new Clipping(1, R.id.test_image, "dace pictureno", 0));
        db.addClipping(new Clipping(2, R.id.test_image, "mace pictureno", 0));
        db.addClipping(new Clipping(3, R.id.test_image, "pace pictureno", 0));
        db.addClipping(new Clipping(4, R.id.test_image, "lace pictureno", 2));
        db.addClipping(new Clipping(5, R.id.test_image, "space pictureno", 2));

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
        db.deleteClipping(new Clipping(2, R.id.test_image, "mace pictureno", 0));
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


        /* * * * Testing Database * * * */

        //  TEST 1
        Log.d("TEST 1", "**************TEST 1********************");
        db = new ScrapbookModel(this);
        db.dropAll();

        //  TEST 2
        Log.d("TEST 2", "**************TEST 2********************");
        Collection X = new Collection(0, "Deadzone");
        Collection A = new Collection(1, "A");
        Collection B = new Collection(2, "B");
        db.addCollection(A);
        db.addCollection(B);

        //  TEST 3
        Log.d("TEST 3", "**************TEST 3********************");
        Clipping one = new Clipping(0, R.id.test_image, "foo", X.getId());
        Clipping two = new Clipping(1, R.id.test_image, "foo", X.getId());
        Clipping three = new Clipping(2, R.id.test_image, "bar", X.getId());
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
