package com.example.test2.productPanels;

public class ProductPendingOrders {

    private String userId, productId, productName, productQty, productPrice, RandomUID, totalPrice, producerId;


    public ProductPendingOrders() {
    }

    public ProductPendingOrders(String userId, String productId, String productName, String productQty, String productPrice, String randomUID, String totalPrice, String producerId) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
        RandomUID = randomUID;
        this.totalPrice = totalPrice;
        this.producerId = producerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }
}
