package com.example.scott.assignment2;

import android.media.Image;

import java.util.Date;

/**
 * Created by Scott on 5/5/2016.
 */
public class Clipping {

    //Reference id
    private int rid;

    //Unique id
    private int id;

    //Name of picture
    private String name;

    //Image of picture
    private int img;

    //Notes about picture
    private String notes;

    //Date of creation
    private Date date;

    public Clipping(int id, String name, int img, String notes, int rid)
    {
        this.id = id;
        this.name = name;
        this.img = img;
        this.notes = notes;
        this.rid = rid;
        date = new Date();
    }

    public Clipping() {
        id = 999;
        name = "default";
        img = 999;
        notes = "this is a blank image";
        date = new Date();
        rid = 999;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRid(int rid)
    {
        this.rid = rid;
    }

    public void setImg(int img)
    {
        this.img = img;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getRid()
    {
        return rid;
    }

    public int getImg()
    {
        return img;
    }

    public String getNotes()
    {
        return notes;
    }

    public Date getDate()
    {
        return date;
    }
}