package com.example.uur.dbsqliteexample.model;

import java.io.Serializable;

/**
 * Created by Uur on 1.2.2015.
 */
public class Employee implements Serializable {
    public static final String TAG = "Employee";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mFirstName;
    private String mLastName;
    private String mAddress;
    private String mPhoneNumber;
    private String mEmail;
    private double mSalary;
    private Company mCompany;

    public Employee() {}
    public Employee(String mFirstName, String mLastName, String mAddress, String mPhoneNumber, String mEmail, double mSalary, Company mCompany) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mAddress = mAddress;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
        this.mSalary = mSalary;
        this.mCompany = mCompany;
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

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
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

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public double getmSalary() {
        return mSalary;
    }

    public void setmSalary(double mSalary) {
        this.mSalary = mSalary;
    }

    public Company getmCompany() {
        return mCompany;
    }

    public void setmCompany(Company mCompany) {
        this.mCompany = mCompany;
    }
}
