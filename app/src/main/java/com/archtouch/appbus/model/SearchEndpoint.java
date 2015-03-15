package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

public class SearchEndpoint {

    @Expose
    private String stopName;

    public SearchEndpoint(String stopName) {
        setStopName(stopName);
    }

    /**
     * Format server accept for query
     * @param stopName
     */
    public void setStopName(String stopName) {
        this.stopName = stopName.format("%%%s%%", stopName);
    }
}