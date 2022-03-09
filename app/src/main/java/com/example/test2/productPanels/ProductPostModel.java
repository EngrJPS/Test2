package com.example.test2.productPanels;

public class ProductPostModel {
    private String prod;
    private String dHarvest;
    private String qty;
    private String prc;
    private String ImageURL;
    private String RandomUID;
    private String ProducerId;

    public ProductPostModel() {
    }

    public ProductPostModel(String prod, String dHarvest, String qty, String prc, String imageURL, String randomUID, String producerId) {
        this.prod = prod;
        this.dHarvest = dHarvest;
        this.qty = qty;
        this.prc = prc;
        ImageURL = imageURL;
        RandomUID = randomUID;
        ProducerId = producerId;
    }
}
