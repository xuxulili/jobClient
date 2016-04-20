package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/4/20.
 */
public class DetailsArticleCQUPT {
    public String title;
    public String text;
    public String littleTitle;
    public int type;
    public String time;
    public DocumentItem document;

    public DocumentItem getDocument() {
        return document;
    }

    public void setDocument(DocumentItem document) {
        this.document = document;
    }

    public String toString() {
        return title + text + type + document.toString();
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

    public void setType(int type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getType() {
        return type;
    }

    public String getTime() {
        return time;
    }


}
