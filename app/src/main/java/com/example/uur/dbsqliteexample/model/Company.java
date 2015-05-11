package com.example.uur.dbsqliteexample.model;

import java.io.Serializable;

/**
 * Created by Uur on 1.2.2015.
 */
public class Company implements Serializable{
    public static final String TAG = "Employee";
    private static final long serialVersionUID = -7406082437623008161L;
    private long mId;
    private String mNAme;
    private String mAddress;
    private String mPhoneNumber;
    private String mWebsite;

    public Company() {}

    public Company(String name, String address, String phoneNumber, String webSite) {
        this.mNAme = name;
        this.mAddress = address;
        this.mPhoneNumber = phoneNumber;
        this.mWebsite = webSite;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmNAme() {
        return mNAme;
    }

    public void setmNAme(String mNAme) {
        this.mNAme = mNAme;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmWebsite() {
        return mWebsite;
    }

    public void setmWebsite(String mWebsite) {
        this.mWebsite = mWebsite;
    }
}
