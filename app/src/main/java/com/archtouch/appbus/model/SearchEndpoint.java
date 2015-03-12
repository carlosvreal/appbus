package com.archtouch.appbus.model;

import com.archtouch.appbus.R;
import com.archtouch.appbus.application.AppBusApplication;
import com.google.gson.annotations.Expose;

import java.net.URLEncoder;

/**
 * Created by real on 6/3/15.
 */
public class SearchEndpoint {

    @Expose
    private String stopName;


    public SearchEndpoint(String stopName) {
        setStopName(stopName);
    }

    public String getStopName() {
        return stopName;
    }

    /**
     * Format server accept for query
     * @param stopName
     */
    public void setStopName(String stopName) {
        this.stopName = stopName.format("%%%s%%", stopName);
    }

}
