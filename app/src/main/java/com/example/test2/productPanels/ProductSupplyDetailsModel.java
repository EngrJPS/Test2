package com.example.test2.productPanels;

public class ProductSupplyDetailsModel {
    private String dHarvest, qtyinKg, pric, productname, ImageURL, RandomUID, rUserId;

    public ProductSupplyDetailsModel() {
    }

    public ProductSupplyDetailsModel(String dHarvest, String qtyinKg, String pric, String productname, String imageURL, String randomUID, String rUserId) {
        this.dHarvest = dHarvest;
        this.qtyinKg = qtyinKg;
        this.pric = pric;
        this.productname = productname;
        ImageURL = imageURL;
        RandomUID = randomUID;
        this.rUserId = rUserId;
    }

    public String getdHarvest() {
        return dHarvest;
    }

    public void setdHarvest(String dHarvest) {
        this.dHarvest = dHarvest;
    }

    public String getQtyinKg() {
        return qtyinKg;
    }

    public void setQtyinKg(String qtyinKg) {
        this.qtyinKg = qtyinKg;
    }

    public String getPric() {
        return pric;
    }

    public void setPric(String pric) {
        this.pric = pric;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getrUserId() {
        return rUserId;
    }

    public void setrUserId(String rUserId) {
        this.rUserId = rUserId;
    }
}
