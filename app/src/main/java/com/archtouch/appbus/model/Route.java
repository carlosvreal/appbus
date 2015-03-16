package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

public class Route  {

    @Expose private int id;
    @Expose private String shortName;
    @Expose private String longName;
    @Expose private String lastModifiedDate;
    @Expose private String agencyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}