package com.example.test2;

import android.os.Parcel;
import android.os.Parcelable;

public class UsersDataLogistics implements Parcelable {
    private String logName;
    private String logAddress;
    private String logPhone;
    private String logEmail;
    private String logID;

    public UsersDataLogistics(String logName, String logAddress, String logPhone, String logEmail, String logID) {
        this.logName = logName;
        this.logAddress = logAddress;
        this.logPhone = logPhone;
        this.logEmail = logEmail;
        this.logID = logID;
    }

    protected UsersDataLogistics(Parcel in) {
        logName = in.readString();
        logAddress = in.readString();
        logPhone = in.readString();
        logEmail = in.readString();
        logID = in.readString();
    }

    public static final Creator<UsersDataLogistics> CREATOR = new Creator<UsersDataLogistics>() {
        @Override
        public UsersDataLogistics createFromParcel(Parcel in) {
            return new UsersDataLogistics(in);
        }

        @Override
        public UsersDataLogistics[] newArray(int size) {
            return new UsersDataLogistics[size];
        }
    };

    //getter-setter logName
    public String getLogName() {
        return logName;
    }
    public void setLogName(String logName) {
        this.logName = logName;
    }
    //getter-setter logAddress
    public String getLogAddress() {
        return logAddress;
    }
    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }
    //getter-setter logPhone
    public String getLogPhone() {
        return logPhone;
    }
    public void setLogPhone(String logPhone) {
        this.logPhone = logPhone;
    }
    //getter-setter logEmail
    public String getLogEmail() {
        return logEmail;
    }
    public void setLogEmail(String logEmail) {
        this.logEmail = logEmail;
    }
    //getter-setter logID
    public String getLogID() {
        return logID;
    }
    public void setLogID(String logID) {
        this.logID = logID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(logName);
        parcel.writeString(logAddress);
        parcel.writeString(logPhone);
        parcel.writeString(logEmail);
        parcel.writeString(logID);
    }
}
