package com.example.test2.productPanels;

public class ProductFinalOrders1 {

    private String Address,GrandTotalPrice,Phone,Name,RandomUID,Status;

    public ProductFinalOrders1() {
    }

    public ProductFinalOrders1(String address, String grandTotalPrice, String phone, String name, String randomUID, String status) {
        Address = address;
        GrandTotalPrice = grandTotalPrice;
        Phone = phone;
        Name = name;
        RandomUID = randomUID;
        Status = status;
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

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
