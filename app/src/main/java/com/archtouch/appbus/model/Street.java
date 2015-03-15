package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

public class Street {

    @Expose private int id;
    @Expose private String name;
    @Expose private String sequence;
    @Expose private int route_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
