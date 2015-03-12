package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

/**
 * Created by real on 6/3/15.
 */
public class Street {

    @Expose private int id;
    @Expose private String name;
    @Expose private String sequence;
    @Expose private int route_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }
}
