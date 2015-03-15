package com.archtouch.appbus.network.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.archtouch.appbus.model.Route;

public class RouteResponse {

    @Expose
    private List<Route> rows;

    public List<Route> getRows() {
        return rows;
    }
}
