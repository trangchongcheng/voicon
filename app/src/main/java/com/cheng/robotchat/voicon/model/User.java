package com.cheng.robotchat.voicon.model;

/**
 * Created by chientruong on 6/7/16.
 */
public class User {
    private int id;
    private String mNameTo;
    private String mLanguage;
    private String mNameFrom;
    private byte[] mImageFrom;
    private byte[] mImageTo;

    public byte[] getmImageFrom() {
        return mImageFrom;
    }

    public void setmImageFrom(byte[] mImageFrom) {
        this.mImageFrom = mImageFrom;
    }

    public byte[] getmImageTo() {
        return mImageTo;
    }

    public void setmImageTo(byte[] mImageTo) {
        this.mImageTo = mImageTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmNameTo() {
        return mNameTo;
    }

    public void setmNameTo(String mNameTo) {
        this.mNameTo = mNameTo;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getmNameFrom() {
        return mNameFrom;
    }

    public void setmNameFrom(String mNameFrom) {
        this.mNameFrom = mNameFrom;
    }



    public User(String mNameFrom, String mNameTo, String mLanguage, byte[] mImageFrom, byte[] mImageTo) {
        this.mNameTo = mNameTo;
        this.mLanguage = mLanguage;
        this.mNameFrom = mNameFrom;
        this.mImageFrom = mImageFrom;
        this.mImageTo = mImageTo;
    }

    public User(int id,String mNameFrom, String mLanguage, byte[] mImageFrom) {
        this.mNameTo = mNameTo;
        this.mLanguage = mLanguage;
        this.mNameFrom = mNameFrom;
        this.mImageFrom = mImageFrom;
        this.mImageTo = mImageTo;
    }

    public User(int id,String mNameFrom, String mLanguage, byte[] mImageFrom,String mNameTo) {
        this.id=id;
        this.mNameTo = mNameTo;
        this.mLanguage = mLanguage;
        this.mNameFrom = mNameFrom;
        this.mImageFrom = mImageFrom;
    }


    public User() {
    }

    public User(int id, String mNameTo, String mLanguage, String mNameFrom, byte[] mImageFrom,byte[] mImageTo) {
        this.id = id;
        this.mNameTo = mNameTo;
        this.mLanguage = mLanguage;
        this.mNameFrom = mNameFrom;
        this.mImageFrom = mImageFrom;
        this.mImageTo = mImageTo;
    }
}
