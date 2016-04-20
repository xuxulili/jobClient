package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/4/20.
 */
public class MessageItemCQUPT {
    public String messageTitle;
    public String messageTime;
    public String messagePlace;
    public String messageNeedNum;
    public String messageViewTime;
    public String messageNeedType;
    public String messageUrl;

    public String toString() {
        return messageTitle + messageNeedNum + messageTime + messageUrl;
    }
    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getMessagePlace() {
        return messagePlace;
    }

    public String getMessageNeedNum() {
        return messageNeedNum;
    }

    public String getMessageViewTime() {
        return messageViewTime;
    }

    public String getMessageNeedType() {
        return messageNeedType;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setMessagePlace(String messagePlace) {
        this.messagePlace = messagePlace;
    }

    public void setMessageNeedNum(String messageNeedNum) {
        this.messageNeedNum = messageNeedNum;
    }

    public void setMessageViewTime(String messageViewTime) {
        this.messageViewTime = messageViewTime;
    }

    public void setMessageNeedType(String messageNeedType) {
        this.messageNeedType = messageNeedType;
    }
}
