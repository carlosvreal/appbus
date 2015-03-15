package com.archtouch.appbus.network.model;

import com.google.gson.annotations.Expose;
import java.util.List;
import com.archtouch.appbus.model.Timetable;

public class TimetableResponse {

    @Expose
    private List<Timetable> rows;

    public List<Timetable> getRows() {
        return rows;
    }
}
