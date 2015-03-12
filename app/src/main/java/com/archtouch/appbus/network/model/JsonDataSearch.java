package com.archtouch.appbus.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by real on 9/3/15.
 */
public class JsonDataSearch {

    @SerializedName("params")
    private Object paramsObject;

    public void setParamsObject(Object paramsObject) {
        this.paramsObject = paramsObject;
    }
}
