package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/20.
 */
public class MessageItemXD {
    public String messageTitle;
    public String messageUrl;
    public String messagePostTime;

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public void setMessagePostTime(String messagePostTime) {
        this.messagePostTime = messagePostTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public String getMessagePostTime() {
        return messagePostTime;
    }
    public String toString() {
        return messagePostTime + messageTitle + messageUrl;
    }
}
