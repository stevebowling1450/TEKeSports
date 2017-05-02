package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Participant {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private Logo logo;

    @SerializedName("score")
    private int score;

    @SerializedName("forfeit")
    private boolean forfeit;

    @SerializedName("country")
    private String country;

    public Participant() {
    }

    public int getScore() {
        return score;
    }

    public boolean isForfeit() {
        return forfeit;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Logo getLogo() {
        return logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
