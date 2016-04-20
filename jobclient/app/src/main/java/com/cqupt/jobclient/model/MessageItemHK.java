package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/21.
 */
public class MessageItemHK  {
    public String messageTitle;
    public String messagePostTime;
    public String messageViewNum;
    public String messageUrl;

    public String toString() {
        return messageTitle + messagePostTime + messageUrl + messageViewNum;
    }
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessagePostTime(String messagePostTime) {
        this.messagePostTime = messagePostTime;
    }

    public void setMessageViewNum(String messageViewNum) {
        this.messageViewNum = messageViewNum;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessagePostTime() {
        return messagePostTime;
    }

    public String getMessageViewNum() {
        return messageViewNum;
    }

    public String getMessageUrl() {
        return messageUrl;
    }
}
