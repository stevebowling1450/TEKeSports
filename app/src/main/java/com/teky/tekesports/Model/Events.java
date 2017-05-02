package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 12/1/16.
 * toornament-android.
 */

public class Events {

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("hour")
    private String hour;

    public Events(){
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }
}
