package com.archtouch.appbus.network.model;

import com.google.gson.annotations.Expose;

import java.util.List;

import com.archtouch.appbus.model.Route;
import com.archtouch.appbus.model.Timetable;

/**
 * Created by real on 9/3/15.
 */
public class RouteResponse {

    @Expose
    private List<Route> rows;

    public List<Route> getRows() {
        return rows;
    }
}
