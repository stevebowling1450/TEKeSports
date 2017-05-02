package com.teky.tekesports.Utils;

import com.teky.tekesports.Model.User;

import java.util.ArrayList;
import java.util.Date;

import static com.teky.tekesports.Components.Constants.PLAYER_ADMIN;
import static com.teky.tekesports.Components.Constants.PLAYER_MODERATOR;
import static com.teky.tekesports.Components.Constants.PLAYER_OWNER;
import static com.teky.tekesports.Components.Constants.PLAYER_USER;

/**
 * Created by lennyhicks on 12/7/16.
 * toornament-android.
 */

public class PlayerControls {
    private User currentUser;
    private User otherUser;

    public PlayerControls(User currentUser, User otherUser) {
        this.currentUser = currentUser;
        this.otherUser = otherUser;
    }

    public void muteUser() {
        if (currentUser.getUserRights() > PLAYER_USER && currentUser.getUserRights() > otherUser.getUserRights()
                || currentUser.getUserRights().equals(PLAYER_OWNER)) {
            //Mute User
            otherUser.setMuted(true);
        }
    }

    public void tempMuteUser(Date muteLength) {
        if (currentUser.getUserRights() > PLAYER_USER && currentUser.getUserRights() > otherUser.getUserRights()
                || currentUser.getUserRights().equals(PLAYER_OWNER)) {
            //Mute User
            otherUser.setMuted(true);
            otherUser.setIsMutedTil(muteLength);
        }
    }

    public void banUser() {
        if (currentUser.getUserRights() >= PLAYER_ADMIN && currentUser.getUserRights() > otherUser.getUserRights()
                || currentUser.getUserRights().equals(PLAYER_OWNER)) {
            //Ban User
            otherUser.setBanned(true);
        }
    }

    public void tempBanUser(Date banLength) {
        if (currentUser.getUserRights() >= PLAYER_ADMIN && currentUser.getUserRights() > otherUser.getUserRights()
                || currentUser.getUserRights().equals(PLAYER_OWNER)) {
            //Temp Ban User
            otherUser.setBanned(true);
            otherUser.setIsBannedTil(banLength);
        }
    }

    public void setUserRank(Integer rank) {
        if (currentUser.getUserRights().equals(PLAYER_OWNER)) {
            //Set User Rank
            otherUser.setUserRights(rank);
        }
    }

    public void unmuteUser() {
        //Unmute User
        if (currentUser.getUserRights() > PLAYER_MODERATOR && currentUser.getUserRights() > otherUser.getUserRights()
                || currentUser.getUserRights().equals(PLAYER_OWNER)) {
            otherUser.setIsMutedTil(null);
            otherUser.setMuted(false);
        }
    }

    public void unbanUser() {
        //Unban User
        if (currentUser.getUserRights().equals(PLAYER_OWNER)) {
            otherUser.setIsBannedTil(null);
            otherUser.setBanned(false);
        }
    }

    public void addFriend(User currentUser, User otherUser) {
        ArrayList<User> friends = new ArrayList<>();
        friends.add(otherUser);
        CurrentUser.getUser().setFriends(friends);
        FireBase.getDatabase().child("users").child(currentUser.getUserID()).setValue(currentUser);
    }
}
