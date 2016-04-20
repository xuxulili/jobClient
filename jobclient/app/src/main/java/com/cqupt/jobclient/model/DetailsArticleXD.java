package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/28.
 */
public class DetailsArticleXD {
    public String title;
    public String text;
    public String littleTitle;
    public int type;
    public String time;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getLittleTitle() {
        return littleTitle;
    }
}
