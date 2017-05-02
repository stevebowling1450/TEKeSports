package com.teky.tekesports.Utils;

import com.teky.tekesports.Model.Message;
import com.teky.tekesports.Model.User;

import static com.teky.tekesports.Components.Constants.PLAYER_ADMIN;
import static com.teky.tekesports.Components.Constants.PLAYER_OWNER;

/**
 * Created by lennyhicks on 12/7/16.
 */

public class ChatControls {
    private User currentUser;
    private Message message;

    public ChatControls(User currentUser, Message message){
        this.currentUser = currentUser;
        this.message = message;
    }

    public void deleteMessage(){
        if(message.getPrivate()){
            //Delete Private Message
        } else if (currentUser.getUserRights() >= PLAYER_ADMIN && message.getSender().getUserRights() != PLAYER_OWNER){
            //Delete Global Chat Message
        }
    }

    public void sendMessage(){
        if (currentUser.getMuted()){
//            //Inform User they are muted Perm / Until
            return;
        } else {
            message.setMessage(message.getMessage());
            //Check for Obscene words
        }

        if (!message.getMessage().isEmpty()) {
            //Send final Message
            String key = FireBase.getDatabase().child("messages").push().getKey();
            FireBase.getDatabase().child("messages").child(key).setValue(message);
        }


    }


}
