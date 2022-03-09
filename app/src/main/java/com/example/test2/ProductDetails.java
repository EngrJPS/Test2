package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetails extends AppCompatActivity {
    private ImageView prodImage;
    private TextView tProdName,tProdPrice,tProdDate,tProdDescription,tShopName,tShopAddress,tShopNumber,tShopEmail;
    private Button orderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        prodImage = findViewById(R.id.imageProductInfo);

        tProdName = findViewById(R.id.textProductName);
        tProdPrice = findViewById(R.id.textProductPrice);
        tProdDate = findViewById(R.id.textProductDate);
        tProdDescription = findViewById(R.id.textProductDescription);
        tShopName = findViewById(R.id.textShopName);
        tShopAddress = findViewById(R.id.textShopAddress);
        tShopNumber = findViewById(R.id.textShopContactNumber);
        tShopEmail = findViewById(R.id.textEmailAddress);

        orderItem = findViewById(R.id.buttonOrder);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference prodRef = database.getReference();
    }
}