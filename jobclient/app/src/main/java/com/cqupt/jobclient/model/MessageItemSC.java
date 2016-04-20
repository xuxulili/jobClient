package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/19.
 */
public class MessageItemSC  {
    public String messageTitle;
    public String messageUrl;

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String toString() {
        return messageTitle + messageUrl;
    }
}
