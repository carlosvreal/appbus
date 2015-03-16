package com.archtouch.appbus.network.model;

import com.google.gson.annotations.Expose;

public class JsonDataSearch {

    @Expose
    private Object params;

    public void setParamsObject(Object paramsObject) {
        this.params = paramsObject;
    }
}
