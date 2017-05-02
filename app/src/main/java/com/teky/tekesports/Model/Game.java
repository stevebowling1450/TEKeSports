package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Game {


    @SerializedName("number")
    private int number;

    @SerializedName("status")
    private String status;

    @SerializedName("opponents")
    private List<Opponent> opponents;

    public Game(){
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Opponent> opponents) {
        this.opponents = opponents;
    }
}
