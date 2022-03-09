package com.example.test2.productPanels;

public class ProductPaymentOrders1 {

    private String Address,GrandTotalPrice,Phone,Name,Note,RandomUID;

    public ProductPaymentOrders1() {
    }

    public ProductPaymentOrders1(String address, String grandTotalPrice, String phone, String name, String note, String randomUID) {
        Address = address;
        GrandTotalPrice = grandTotalPrice;
        Phone = phone;
        Name = name;
        Note = note;
        RandomUID = randomUID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGrandTotalPrice() {
        return GrandTotalPrice;
    }

    public void setGrandTotalPrice(String grandTotalPrice) {
        GrandTotalPrice = grandTotalPrice;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }
}
