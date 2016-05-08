package com.example.scott.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 4/5/2016.
 * For CSCI342 Assignment 2
 */
public class ScrapbookModel extends SQLiteOpenHelper
{
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "collectionInfo";

    //Collection table name
    private static final String TABLE_COLLECTION = "collection";
    //Collection column definitions
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    //Clippings table name
    private static final String TABLE_CLIPPINGS = "clippings";
    //Reference variable
    private static final String KEY_RID = "rid";
    private static final String KEY_IMG = "image";
    private static final String KEY_NOTES = "notes";

    public ScrapbookModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COLLECTION_TABLE = "CREATE TABLE " + TABLE_COLLECTION +
                "("
                + KEY_ID + " INTEGER, "
                + KEY_NAME + " TEXT, " +
                "PRIMARY KEY (" + KEY_ID + ", " + KEY_NAME + ")," +
                "UNIQUE (" + KEY_NAME + ")" +
                ")";
        db.execSQL(CREATE_COLLECTION_TABLE);

        String CREATE_CLIPPINGS_TABLE = "CREATE TABLE " + TABLE_CLIPPINGS +
                "("
                + KEY_ID + " INTEGER, "
                + KEY_IMG + " INTEGER, "
                + KEY_NOTES + " TEXT, "
                + KEY_RID + " INTEGER REFERENCES " + TABLE_COLLECTION + "(" + KEY_ID + "), " +
                "PRIMARY KEY (" + KEY_ID + ")" +
                ")";
        db.execSQL(CREATE_CLIPPINGS_TABLE);
        addCollection(new Collection(getCollectionCount(), "All Clippings"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIPPINGS);
        //Create new tables
        onCreate(db);
    }

    //Add Collection
    public void addCollection(Collection col)
    {
        //setup DB connection
        SQLiteDatabase db = this.getWritableDatabase();
        //Set up values to be inserted
        ContentValues vals = new ContentValues();
        //Collection Name
        vals.put(KEY_ID, col.getId());
        vals.put(KEY_NAME, col.getName());
        //Insert row
        Log.d("Insert", "Inserted: " + col.getId() + ", " + col.getName());
        db.insert(TABLE_COLLECTION, null, vals);
        //close DB connection
        db.close();
    }

    //Add Clipping
    public void addClipping(Clipping clip)
    {
        //setup DB connection
        SQLiteDatabase db = this.getWritableDatabase();
        //Set up values to be inserted
        ContentValues vals = new ContentValues();
        //Collection Name
        vals.put(KEY_ID, clip.getId());
        vals.put(KEY_IMG, clip.getImg());
        vals.put(KEY_NOTES, clip.getNotes());
        vals.put(KEY_RID, clip.getRid());
        //Insert row
        Log.d("Insert", "Inserted: " + clip.getId() + ", " + clip.getNotes() + ", REFERENCES (" + clip.getRid() + ")");
        db.insert(TABLE_CLIPPINGS, null, vals);
        //close DB connection
        db.close();
    }

    public Collection getCollection(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COLLECTION, new String[]{KEY_ID, KEY_NAME}, KEY_NAME + "=?", new String[]{name}, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
        }
        else
        {
            return new Collection(999, "Invalid");
        }
        return new Collection(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
    }

    public Clipping getClipping(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLIPPINGS, new String[]{KEY_ID, KEY_NAME, KEY_IMG, KEY_RID}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        else
        {
            return new Clipping(999, 0, "Invalid", 0);
        }
        return new Clipping(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
    }

    public List<Collection> getAllCollections() {
        //Select All Query
        List<Collection> colList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COLLECTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //put all rows into list
        if (cursor.moveToFirst()) {
            do {
                Collection col = new Collection();
                col.setId(Integer.parseInt(cursor.getString(0)));
                col.setName(cursor.getString(1));
                //Add to list
                colList.add(col);
            }
            while (cursor.moveToNext());
        }
        return colList;
    }

    public List<Clipping> getAllClippings() {
        //Select All Query
        List<Clipping> colList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLIPPINGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //put all rows into list
        if (cursor.moveToFirst()) {
            do {
                Clipping clip = new Clipping();
                clip.setId(Integer.parseInt(cursor.getString(0)));
                clip.setImg(Integer.parseInt(cursor.getString(1)));
                clip.setNotes(cursor.getString(2));
                clip.setRid(Integer.parseInt(cursor.getString(3)));
                //Add to list
                colList.add(clip);
            }
            while (cursor.moveToNext());
        }
        return colList;
    }

    public List<Clipping> getClipingsForCollection(int id) {
        //Select All Query
        List<Clipping> colList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLIPPINGS + " WHERE " + KEY_RID + " = " + String.valueOf(id);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //put all rows into list
        if (cursor.moveToFirst()) {
            do {
                Clipping clip = new Clipping();
                clip.setId(Integer.parseInt(cursor.getString(0)));
                clip.setImg(Integer.parseInt(cursor.getString(1)));
                clip.setNotes(cursor.getString(2));
                clip.setRid(Integer.parseInt(cursor.getString(3)));
                //Add to list
                colList.add(clip);
            }
            while (cursor.moveToNext());
        }
        return colList;
    }


    public int getCollectionCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_COLLECTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public int getClippingsCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_CLIPPINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public int getClippingsForCollectionCount(int id)
    {
        String countQuery = "SELECT * FROM " + TABLE_CLIPPINGS + " WHERE " + KEY_RID + " = " + String.valueOf(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public int updateCollection(Collection col)
    {
        // Connect to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Set up values to update
        ContentValues vals = new ContentValues();
        vals.put(KEY_NAME, col.getName());

        // Update table
        return db.update(TABLE_COLLECTION, vals, KEY_ID + " =?", new String[]{String.valueOf(col.getId())});
    }

    public int updateClipping(Clipping clip)
    {
        // Connect to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Set up values to update
        ContentValues vals = new ContentValues();
        vals.put(KEY_IMG, clip.getImg());
        vals.put(KEY_NOTES, clip.getNotes());
        vals.put(KEY_RID, clip.getRid());

        // Update table
        return db.update(TABLE_CLIPPINGS, vals, KEY_ID + " =?", new String[]{String.valueOf(clip.getId())});
    }

    public int addClippingtoCollection(int clipID, int colID)
    {
        // Connect to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Set up values to update
        ContentValues vals = new ContentValues();
        vals.put(KEY_RID, colID);

        // Update table
        return db.update(TABLE_CLIPPINGS, vals, KEY_ID + " =?", new String[]{String.valueOf(clipID)});
    }

    public List<Clipping> getClippingWithNote(String token)
    {
        //Select All Query
        List<Clipping> colList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLIPPINGS + " WHERE " + KEY_NOTES + " LIKE '%" + token +"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //put all rows into list
        if (cursor.moveToFirst()) {
            do {
                Clipping clip = new Clipping();
                clip.setId(Integer.parseInt(cursor.getString(0)));
                clip.setImg(Integer.parseInt(cursor.getString(1)));
                clip.setNotes(cursor.getString(2));
                clip.setRid(Integer.parseInt(cursor.getString(3)));
                //Add to list
                colList.add(clip);
            }
            while (cursor.moveToNext());
        }
        return colList;
    }

    public List<Clipping> getClippingWithNote(String token, int rid)
    {
        //Select All Query
        List<Clipping> colList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLIPPINGS + " WHERE " + KEY_NOTES + " LIKE '%" + token +"%' AND " + KEY_RID + " = " +String.valueOf(rid);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //put all rows into list
        if (cursor.moveToFirst()) {
            do {
                Clipping clip = new Clipping();
                clip.setId(Integer.parseInt(cursor.getString(0)));
                clip.setImg(Integer.parseInt(cursor.getString(1)));
                clip.setNotes(cursor.getString(2));
                clip.setRid(Integer.parseInt(cursor.getString(3)));
                //Add to list
                colList.add(clip);
            }
            while (cursor.moveToNext());
        }
        return colList;
    }

    public void deleteCollection(Collection col)
    {
        // Connect to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete from database
        db.delete(TABLE_COLLECTION, KEY_ID + "=?", new String[]{String.valueOf(col.getId())});

        // Close connection
        db.close();
    }

    public void deleteClipping(Clipping clip)
    {
        // Connect to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete from database
        db.delete(TABLE_CLIPPINGS, KEY_ID + "=?", new String[]{String.valueOf(clip.getId())});

        // Close connection
        db.close();
    }

    public void dropAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIPPINGS);
        onCreate(db);
    }
}
