package com.teky.tekesports.Model;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lennyhicks on 12/7/16.
 * toornament-android.
 */

public class User {
    private ArrayList<Disciplines> favorites = new ArrayList<>();
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<Message> mailBox = new ArrayList<>();
    private ArrayList<Tournament> favoriteTournaments = new ArrayList<>();
    private String userID = "";
    private Boolean firstLogin = true;
    private Boolean isOnline = true;
    private String username = "";
    private String email = "";
    private Integer userRights = 1;
    private Boolean isMuted = false;
    private Boolean isBanned = false;
    private Date isMutedTil;
    private Date isBannedTil;


    public User(ArrayList<Disciplines> favorites,ArrayList<Tournament> favoriteTournaments, boolean firstLogin, boolean isOnline, String username) {
        this.favorites = favorites;
        this.favoriteTournaments = favoriteTournaments;
        this.firstLogin = firstLogin;
        this.isOnline = isOnline;
        this.username = username;
    }


    public User() {
    }

    public ArrayList<Disciplines> getFavorites() {
        if (favorites == null) {
            return new ArrayList<>();
        }
        return favorites;
    }

    public void setFavorites(ArrayList<Disciplines> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Tournament> getFavoriteTournaments() {
        if (favoriteTournaments == null) {
            return new ArrayList<>();
        }
        return favoriteTournaments;
    }

    public void setFavoriteTournaments(ArrayList<Tournament> favoriteTournaments) {
        this.favoriteTournaments = favoriteTournaments;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Message> getMailBox() {
        return mailBox;
    }

    public void setMailBox(ArrayList<Message> mailBox) {
        this.mailBox = mailBox;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserRights() {
        return userRights;
    }

    public void setUserRights(Integer userRights) {
        this.userRights = userRights;
    }

    public Boolean getMuted() {
        return isMuted;
    }

    public void setMuted(Boolean muted) {
        isMuted = muted;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public Date getIsMutedTil() {
        return isMutedTil;
    }

    public void setIsMutedTil(Date isMutedTil) {
        this.isMutedTil = isMutedTil;
    }

    public Date getIsBannedTil() {
        return isBannedTil;
    }

    public void setIsBannedTil(Date isBannedTil) {
        this.isBannedTil = isBannedTil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
