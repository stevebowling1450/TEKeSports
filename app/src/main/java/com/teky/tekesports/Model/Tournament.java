package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by stevebowling on 11/29/16.
 * toornament-android.
 */
public class Tournament {
    @SerializedName("id")
    public String id;

    @SerializedName("discipline")
    private String discipline;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("status")
    private String status;

    @SerializedName("date_start")
    private Date date_start;

    @SerializedName("date_end")
    private Date date_end;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("online")
    private boolean online;

    @SerializedName("isPublic")
    private boolean isPublic;

    @SerializedName("location")
    private String location;

    @SerializedName("country")
    private String country;

    @SerializedName("size")
    private int size;

    @SerializedName("participant_type")
    private String participantType;

    @SerializedName("match_type")
    private String matchType;

    @SerializedName("organization")
    private String organization;

    @SerializedName("website")
    private String website;

    @SerializedName("description")
    private String description;

    @SerializedName("rules")
    private String rules;

    @SerializedName("prize")
    private String prize;

    @SerializedName("streams")
    private Stream[] streams;

    @SerializedName("checkIn")
    private boolean checkIn;

    @SerializedName("participant_nationality")
    private boolean participantNationality;

    @SerializedName("match_format")
    private String matchFormat;


    public Tournament(){
    }

    public String getId() {
        return id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getName() {
        return name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getStatus() {
        return status;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public String getTimezone() {
        return timezone;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    public int getSize() {
        return size;
    }

    public String getParticipantType() {
        return participantType;
    }

    public String getMatchType() {
        return matchType;
    }

    public String getOrganization() {
        return organization;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getRules() {
        return rules;
    }

    public String getPrize() {
        return prize;
    }

    public Stream[] getStreams() {
        return streams;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public boolean isParticipantNationality() {
        return participantNationality;
    }

    public String getMatchFormat() {
        return matchFormat;
    }
}