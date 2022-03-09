package com.example.test2.productPanels;

public class ProductPendingOrders1 {

    private String address, totalPrice, phone, name, note, RandomUID;

    public ProductPendingOrders1() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public ProductPendingOrders1(String address, String totalPrice, String phone, String name, String note, String randomUID) {
        this.address = address;
        this.totalPrice = totalPrice;
        this.phone = phone;
        this.name = name;
        this.note = note;
        RandomUID = randomUID;


    }
}
