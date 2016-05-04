package com.example.scott.assignment2;

/**
 * Created by Scott on 4/5/2016.
 */
public class Collection {
    private int id;
    private String name;

    public Collection(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Collection() {
        id = 999;
        name = "default";
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
