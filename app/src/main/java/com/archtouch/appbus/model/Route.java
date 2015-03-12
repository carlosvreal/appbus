package com.archtouch.appbus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by real on 6/3/15.
 */
public class Route implements Parcelable {

    @Expose private int id;
    @Expose private String shortName;
    @Expose private String longName;
    @Expose private String lastModifiedDate;
    @Expose private String agencyId;

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public Route() {
    }

    protected Route(Parcel in) {
        id = in.readInt();
        shortName = in.readString();
        longName = in.readString();
        lastModifiedDate = in.readString();
        agencyId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortName);
        dest.writeString(longName);
        dest.writeString(lastModifiedDate);
        dest.writeString(agencyId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}