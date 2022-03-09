package com.example.test2;

import android.os.Parcel;
import android.os.Parcelable;

public class UsersDataProducer implements Parcelable {
    private String bName;
    private String bAddress;
    private String bPhone;
    private String bEmail;
    private String bID;

    public UsersDataProducer(String bName, String bAddress, String bPhone, String bEmail, String bID) {
        this.bName = bName;
        this.bAddress = bAddress;
        this.bPhone = bPhone;
        this.bEmail = bEmail;
        this.bID = bID;
    }

    protected UsersDataProducer(Parcel in) {
        bName = in.readString();
        bAddress = in.readString();
        bPhone = in.readString();
        bEmail = in.readString();
        bID = in.readString();
    }

    public static final Creator<UsersDataProducer> CREATOR = new Creator<UsersDataProducer>() {
        @Override
        public UsersDataProducer createFromParcel(Parcel in) {
            return new UsersDataProducer(in);
        }

        @Override
        public UsersDataProducer[] newArray(int size) {
            return new UsersDataProducer[size];
        }
    };

    //getter-setter bName
    public String getbName() {
        return bName;
    }
    public void setbName(String bName) {
        this.bName = bName;
    }
    //getter-setter bAddress
    public String getbAddress() {
        return bAddress;
    }
    public void setbAddress(String bAddress) {
        this.bAddress = bAddress;
    }
    //getter-setter bPhone
    public String getbPhone() {
        return bPhone;
    }
    public void setbPhone(String bPhone) {
        this.bPhone = bPhone;
    }
    //getter-setter bEmail
    public String getbEmail() {
        return bEmail;
    }
    public void setbEmail(String bEmail) {
        this.bEmail = bEmail;
    }
    //getter-setter bID
    public String getbID() {
        return bID;
    }
    public void setbID(String bID) {
        this.bID = bID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bName);
        parcel.writeString(bAddress);
        parcel.writeString(bPhone);
        parcel.writeString(bEmail);
        parcel.writeString(bID);
    }
}
