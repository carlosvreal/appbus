package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

/**
 * Created by real on 6/3/15.
 */
public class SearchRoute {

    @Expose
    private int routeId;

    public SearchRoute(int routeId) {
        this.routeId = routeId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
