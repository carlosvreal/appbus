package com.archtouch.appbus.network.model;

import com.google.gson.annotations.Expose;
import java.util.List;
import com.archtouch.appbus.model.Timetable;

/**
 * Created by real on 9/3/15.
 */
public class TimetableResponse {

    @Expose
    private List<Timetable> rows;

    public List<Timetable> getRows() {
        return rows;
    }
}
