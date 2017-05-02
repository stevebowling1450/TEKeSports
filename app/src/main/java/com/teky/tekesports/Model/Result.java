package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Result {

    @SerializedName("status")
    private String status;

    @SerializedName("opponents")
    private Opponent[] opponents;

    public Result(){
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Opponent[] getOpponents() {
        return opponents;
    }

    public void setOpponents(Opponent[] opponents) {
        this.opponents = opponents;
    }
}
