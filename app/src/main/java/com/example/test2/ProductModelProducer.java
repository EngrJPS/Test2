package com.example.test2;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModelProducer implements  Parcelable{
    private String prodName;
    private String prodPrice;
    private String prodHarvestDate;
    private String prodDescription;
    private String prodID;

    public ProductModelProducer() {
    }

    public ProductModelProducer(String prodName, String prodPrice, String prodHarvestDate, String prodDescription, String prodID) {
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodHarvestDate = prodHarvestDate;
        this.prodDescription = prodDescription;
        this.prodID = prodID;
    }

    protected ProductModelProducer(Parcel in) {
        prodName = in.readString();
        prodPrice = in.readString();
        prodHarvestDate = in.readString();
        prodDescription = in.readString();
    }

    public static final Creator<ProductModelProducer> CREATOR = new Creator<ProductModelProducer>() {
        @Override
        public ProductModelProducer createFromParcel(Parcel in) {
            return new ProductModelProducer(in);
        }

        @Override
        public ProductModelProducer[] newArray(int size) {
            return new ProductModelProducer[size];
        }
    };

    //getter-setter prodname
    public String getProdName() {
        return prodName;
    }
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    //getter-setter prodprice
    public String getProdPrice() {
        return prodPrice;
    }
    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }
    //getter-setter prodHarvestDate
    public String getProdHarvestDate() {
        return prodHarvestDate;
    }
    public void setProdHarvestDate(String prodHarvestDate) {
        this.prodHarvestDate = prodHarvestDate;
    }
    //getter-setter prodDescription
    public String getProdDescription() {
        return prodDescription;
    }
    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }
    //getter-setter prodID
    public String getProdID() {
        return prodID;
    }
    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prodName);
        parcel.writeString(prodPrice);
        parcel.writeString(prodHarvestDate);
        parcel.writeString(prodDescription);
    }
}
