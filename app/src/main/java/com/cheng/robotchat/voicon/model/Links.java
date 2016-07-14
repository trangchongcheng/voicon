package com.cheng.robotchat.voicon.model;

/**
 * Created by chientruong on 5/27/16.
 */
public class Links {
    private String title;
    private String url;
    private boolean isDownload;
    private boolean isPLay;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isPLay() {
        return isPLay;
    }

    public void setPLay(boolean PLay) {
        isPLay = PLay;
    }

    public Links() {
        this.title = title;
    }

    public Links(String fileName,String title, String url, boolean isDownload,boolean isPLay) {
        this.fileName=fileName;
        this.title = title;
        this.url = url;
        this.isDownload = isDownload;
        this.isPLay=isPLay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

            public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
