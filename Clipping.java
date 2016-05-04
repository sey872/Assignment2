package com.example.scott.assignment2;

/**
 * Created by Scott on 5/5/2016.
 */
public class Clipping {
    private int id;
    private String name;
    private int rid;

    public Clipping(int id, String name, int rid)
    {
        this.id = id;
        this.name = name;
        this.rid = rid;
    }

    public Clipping() {
        id = 999;
        name = "default";
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
}