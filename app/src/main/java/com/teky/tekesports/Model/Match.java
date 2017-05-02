package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by stevebowling on 11/30/16.
 * toornament-android.
 */

public class Match implements Comparable<Match>{


    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("discipline")
    private String discipline;

    @SerializedName("status")
    private String status;

    @SerializedName("tournament_id")
    private String tournamentId;

    @SerializedName("number")
    private int number;

    @SerializedName("stage_number")
    private int stageNumber;

    @SerializedName("group_number")
    private int groupNumber;

    @SerializedName("round_number")
    private int roundNumber;

    @SerializedName("date")
    private String date;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("match_format")
    private String matchFormat;

    @SerializedName("opponents")
    private List<Opponent> opponents;

    @SerializedName("games")
    private List<Game> games;

    @SerializedName("streams")
    private List<Stream> streams;

    @SerializedName("vods")
    private List<Vod> vods;

    @SerializedName("note")
    private String note;

    public Match() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getMatchFormat() {
        return matchFormat;
    }

    public void setMatchFormat(String matchFormat) {
        this.matchFormat = matchFormat;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Opponent> opponents) {
        this.opponents = opponents;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public List<Vod> getVods() {
        return vods;
    }

    public String getNote() {
        return note;
    }



    @Override
    public int compareTo(Match match) {
        return match.getDate().compareTo(this.date);
    }
}
