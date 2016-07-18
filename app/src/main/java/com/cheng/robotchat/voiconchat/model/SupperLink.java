package com.cheng.robotchat.voiconchat.model;

/**
 * Created by chientruong on 6/1/16.
 */
public class SupperLink {
    private String messages;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    private String link;
    private boolean isPlay;
    private boolean isDownload;
    private String value;
    private String type;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SupperLink(String fileName,String type,String value,String messages, String link, boolean isPlay, boolean isDownload) {
        this.type=type;
        this.fileName=fileName;
        this.messages = messages;
        this.link = link;
        this.isPlay = isPlay;
        this.isDownload = isDownload;
        this.value = value;
    }

    public SupperLink(String messages, String link, boolean isPlay, boolean isDownload) {
        this.messages=messages;
        this.link = link;
        this.isPlay = isPlay;
        this.isDownload = isDownload;
    }
    public SupperLink(String messages) {
        this.messages=messages;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
