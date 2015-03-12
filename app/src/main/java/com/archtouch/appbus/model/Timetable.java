package com.archtouch.appbus.model;

import com.google.gson.annotations.Expose;

/**
 * Created by real on 6/3/15.
 */
public class Timetable {

    @Expose private int id;
    @Expose private String calendar;
    @Expose private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
