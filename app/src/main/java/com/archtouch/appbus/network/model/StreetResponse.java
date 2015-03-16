package com.archtouch.appbus.network.model;

import com.google.gson.annotations.Expose;
import java.util.List;
import com.archtouch.appbus.model.Street;

public class StreetResponse {

    @Expose
    private List<Street> rows;

    public List<Street> getRows() {
        return rows;
    }
}
