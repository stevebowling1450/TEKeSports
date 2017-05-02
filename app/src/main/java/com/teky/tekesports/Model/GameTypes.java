package com.teky.tekesports.Model;

/**
 * Created by lennyhicks on 12/12/16.
 * toornament-android.
 */

public class GameTypes {
    private String discipline;
    private String fullName;
    private String gameType;

    public GameTypes(String discipline, String fullName) {
        this.discipline = discipline;
        this.fullName = fullName;
    }

    public GameTypes() {
    }

    public GameTypes(String gameType) {
        this.gameType = gameType;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
