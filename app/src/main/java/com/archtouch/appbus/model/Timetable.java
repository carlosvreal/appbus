package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

public class Timetable {

    @Expose private int id;
    @Expose private String calendar;
    @Expose private String time;

    public String getCalendar() {
        return calendar;
    }

    public String getTime() {
        return time;
    }
}
