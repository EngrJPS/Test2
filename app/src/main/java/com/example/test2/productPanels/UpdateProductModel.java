package com.example.test2.productPanels;

public class UpdateProductModel {

    private String productName;
    private String randomUID;
    private String dateHarvested;
    private String price;
    private String quantity;
    private String imageURL;
    private String userId;

    public UpdateProductModel() {
    }

    public UpdateProductModel(String productName, String randomUID, String dateHarvested, String price, String quantity, String imageURL, String userId) {
        this.productName = productName;
        this.randomUID = randomUID;
        this.dateHarvested = dateHarvested;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

    public String getDateHarvested() {
        return dateHarvested;
    }

    public void setDateHarvested(String dateHarvested) {
        this.dateHarvested = dateHarvested;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
