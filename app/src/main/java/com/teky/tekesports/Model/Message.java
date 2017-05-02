package com.teky.tekesports.Model;

import android.support.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lennyhicks on 12/7/16.
 * toornament-android.
 */
@Keep
@IgnoreExtraProperties
public class Message {
    @SerializedName("sender")
    private User sender;
    @SerializedName("recipient")
    private User recipient;
    @SerializedName("messageId")
    private String messageId;
    @SerializedName("isPrivate")
    private Boolean isPrivate;
    @SerializedName("isStaffOnly")
    private Boolean isStaffOnly;
    @SerializedName("message")
    private String message;
    @SerializedName("sendDate")
    private String sendDate;
    @SerializedName("receiveDate")
    private String receiveDate;
    @SerializedName("isRead")
    private Boolean isRead;

    //Public Chat Message
    public Message(User sender, String messageId, Boolean isStaffOnly, String message, String sendDate) {
        this.sender = sender;
        this.messageId = messageId;
        this.isStaffOnly = isStaffOnly;
        this.message = message;
        this.sendDate = sendDate;
    }

    //Private Chat Message
    public Message(User sender, User recipient, Boolean isPrivate, String messageId, String message, String sendDate) {
        this.sender = sender;
        this.recipient = recipient;
        this.isPrivate = isPrivate;
        this.messageId = messageId;
        this.message = message;
        this.sendDate = sendDate;
    }

    public Message() {
    }

    public Message(String message, String messageId, String sendDate, User sender, Boolean isStaffOnly) {
        this.message = message;
        this.messageId = messageId;
        this.sendDate = sendDate;
        this.sender = sender;
        this.isStaffOnly = isStaffOnly;
    }

    public Message(String value) {
        // Paste your code here
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Boolean getStaffOnly() {
        return isStaffOnly;
    }

    public void setStaffOnly(Boolean staffOnly) {
        isStaffOnly = staffOnly;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }


}
