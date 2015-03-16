package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

public class SearchRoute {

    @Expose
    private int routeId;

    public SearchRoute(int routeId) {
        this.routeId = routeId;
    }
}
