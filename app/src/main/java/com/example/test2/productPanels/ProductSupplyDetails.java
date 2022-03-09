package com.example.test2.productPanels;

public class ProductSupplyDetails {

    public String Product,Quantity,Price,DateHarvested,ImageURL,RandomUID,producerId;

    public ProductSupplyDetails(String product, String quantity, String price, String dateHarvested, String imageURL, String randomUID, String producerId) {
        Product = product;
        Quantity = quantity;
        Price = price;
        DateHarvested = dateHarvested;
        ImageURL = imageURL;
        RandomUID = randomUID;
        this.producerId = producerId;
    }
}
