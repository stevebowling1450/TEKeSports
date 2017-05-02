package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Opponent {

    @SerializedName("number")
    private int number;

    @SerializedName("participant")
    private Participant participant;

    @SerializedName("result")
    private int result;

    @SerializedName("forfeit")
    private boolean forfeit;

    @SerializedName("score")
    private int score;

    public Opponent() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isForfeit() {
        return forfeit;
    }

    public void setForfeit(boolean forfeit) {
        this.forfeit = forfeit;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
