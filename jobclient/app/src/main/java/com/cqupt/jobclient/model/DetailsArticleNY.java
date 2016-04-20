package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DetailsArticleNY {
    public String title;
    public String viewNum;
    public String littleTitle;
    public String text;



    public int type;
    public String toString() {
        return title + viewNum + littleTitle + type;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getViewNum() {
        return viewNum;
    }

    public String getLittleTitle() {
        return littleTitle;
    }

    public int getType() {
        return type;
    }
}
