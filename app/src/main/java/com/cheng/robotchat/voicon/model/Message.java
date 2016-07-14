package com.cheng.robotchat.voicon.model;

import java.util.List;

public class Message {
    private String fromName, value, isDownload, type;
    private List<Links> links;
    private boolean isSelf;
    private String time;
    private byte[] imageFrom;
    private byte[] imageTo;

    public byte[] getImageTo() {
        return imageTo;
    }

    public void setImageTo(byte[] imageTo) {
        this.imageTo = imageTo;
    }

    public byte[] getImageFrom() {
        return imageFrom;
    }

    public void setImageFrom(byte[] imageFrom) {
        this.imageFrom = imageFrom;
    }
//
//    public byte[] getImage() {
//        return imageFrom;
//    }
//
//    public void setImage(byte[] image) {
//        this.imageFrom = image;
//    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Message() {
    }

    public Message(byte[] imageFrom,String fromName, String time, String value, boolean isSelf) {
        this.imageFrom=imageFrom;
        this.time = time;
        this.value = value;
        this.fromName = fromName;
        this.isSelf = isSelf;
    }

    public Message(byte[] imageTo, String fromName, String time, String value, String isDownload, String type, List<Links> links, boolean isSelf) {
        this.imageTo=imageTo;
        this.fromName = fromName;
        this.time = time;
        this.value = value;
        this.isDownload = isDownload;
        this.type = type;
        this.links = links;
        this.isSelf = isSelf;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(String isDownl) {
        this.isDownload = isDownl;
    }

    public String getType() {
        return type;
    }

    public void setType(String txt) {
        this.type = txt;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }
}
