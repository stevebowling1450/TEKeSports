package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Vod {


    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("language")
    private String language;

    @SerializedName("category")
    private String category;

    public Vod(){
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getLanguage() {
        return language;
    }

    public String getCategory() {
        return category;
    }

}
