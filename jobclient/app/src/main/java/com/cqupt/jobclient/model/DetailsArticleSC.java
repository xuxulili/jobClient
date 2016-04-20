package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DetailsArticleSC {
    public String mainTitle;
    public String title;
    public String littleTitle;
    public String text;
    public String time;
    public Table table;
    public int type;
    public String recruit;//招聘
    public String toString() {
        return mainTitle + title + littleTitle + text + time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getRecruit() {
        return recruit;
    }

    public void setRecruit(String recruit) {
        this.recruit = recruit;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getLittleTitle() {
        return littleTitle;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public Table getTable() {
        return table;
    }
}
