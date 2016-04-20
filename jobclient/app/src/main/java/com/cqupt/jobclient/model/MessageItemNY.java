package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/20.
 */

/**
 * Created by Administrator on 2016/3/19.
 */
public class MessageItemNY  {
    public String messageTitle;
    public String messageUrl;
    public  String messagePlace;
    public  String messageTime;

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public void setMessagePlace(String messagePlace) {
        this.messagePlace = messagePlace;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public String getMessagePlace() {
        return messagePlace;
    }

    public String getMessageTime() {
        return messageTime;
    }


    public String toString() {
        return messageTitle + messageUrl+messagePlace+messageTime;
    }
}
