package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MessageItemXDFirst {
    public String messageTitle;
    public String messageUrl;
    public String messageTime;
    public String messagePlace;

    public String toString() {
        return messageTitle + messageUrl + messageTime + messagePlace;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setMessagePlace(String messagePlace) {
        this.messagePlace = messagePlace;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getMessagePlace() {
        return messagePlace;
    }
}
