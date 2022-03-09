package com.example.test2;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModelRetailer implements Parcelable{
    private String quantity;
    private String shippingname;
    private String shippingaddress;
    private String shippingnumber;
    private String orderID;

    public ProductModelRetailer(String quantity,String shippingname,String shippingaddress,String shippingnumber,String orderID)
    {
        this.quantity = quantity;
        this.shippingname = shippingname;
        this.shippingaddress = shippingaddress;
        this.shippingnumber = shippingnumber;
        this.orderID = orderID;
    }

    protected ProductModelRetailer(Parcel in) {
        quantity = in.readString();
        shippingname = in.readString();
        shippingaddress = in.readString();
        shippingnumber = in.readString();
        orderID = in.readString();
    }

    public static final Parcelable.Creator<ProductModelRetailer> CREATOR = new Parcelable.Creator<ProductModelRetailer>() {
        @Override
        public ProductModelRetailer createFromParcel(Parcel in) {
            return new ProductModelRetailer(in);
        }

        @Override
        public ProductModelRetailer[] newArray(int size) {
            return new ProductModelRetailer[size];
        }
    };

    //getter-setter Quantity
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    //getter-setter shippingname
    public String getShippingname() { return shippingname; }
    public void setShippingname(String shippingname) {
        this.shippingname = shippingname;
    }
    //getter-setter shippingaddress
    public String getShippingaddress() { return shippingaddress; }
    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }
    //getter-setter shippingnumber
    public String getShippingnumber() { return shippingnumber; }
    public void setShippingnumber(String shippingnumber) {
        this.shippingnumber = shippingnumber;
    }
   //getter-setter orderID
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quantity);
        parcel.writeString(shippingname);
        parcel.writeString(shippingaddress);
        parcel.writeString(shippingnumber);
        parcel.writeString(orderID);
    }
}
