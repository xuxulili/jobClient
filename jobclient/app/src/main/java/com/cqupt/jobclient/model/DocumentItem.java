package com.cqupt.jobclient.model;

/**
 * Created by Administrator on 2016/4/20.
 */
public class DocumentItem {
    public String document;
    public String documentUrl;

    public String toString() {
        return document + documentUrl;
    }
    public void setDocument(String document) {
        this.document = document;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocument() {
        return document;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }
}
