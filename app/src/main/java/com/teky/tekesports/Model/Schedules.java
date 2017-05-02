package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Schedules {
    @SerializedName("name")
    private String name;

    @SerializedName("timeZone")
    private String tineZone;

    @SerializedName("events")
    private Events[] events;

    public Schedules(){
    }

    public String getName() {
        return name;
    }

    public String getTineZone() {
        return tineZone;
    }

    public Events[] getEvents() {
        return events;
    }
}
