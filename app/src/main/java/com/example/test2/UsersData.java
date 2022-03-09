package com.example.test2;

public class UsersData {
    private String userId;
    private String fullname;
    private String email;
    private String occupation;
    private String phone;
    private String imageURL;
    private String house;
    private String address;
    private String pincode;
    private String cityy;
    private String statee;


    public UsersData() {
    }

    public UsersData(String userId, String fullname, String email, String occupation, String phone, String imageURL, String house, String address, String pincode, String cityy, String statee) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.occupation = occupation;
        this.phone = phone;
        this.imageURL = imageURL;
        this.house = house;
        this.address = address;
        this.pincode = pincode;
        this.cityy = cityy;
        this.statee = statee;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCityy() {
        return cityy;
    }

    public void setCityy(String cityy) {
        this.cityy = cityy;
    }

    public String getStatee() {
        return statee;
    }

    public void setStatee(String statee) {
        this.statee = statee;
    }
}
