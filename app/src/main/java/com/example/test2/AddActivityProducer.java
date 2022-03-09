package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddActivityProducer extends AppCompatActivity {

    private ImageView productimage;
    private MaterialEditText productname, productprice, productharvest, productdescription;
    private Button addproduct, addimage;
    private ProgressBar addprogress;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producer);

        //ImageView findView
        productimage = findViewById(R.id.imageProduct);
        //EditText findView
        productname = findViewById(R.id.addProductName);
        productprice = findViewById(R.id.addProductPrice);;
        productharvest = findViewById(R.id.addProductHarvest);;
        productdescription = findViewById(R.id.addProductDescription);;
        //Button findView
        addproduct = findViewById(R.id.buttonAddProduct);;
        addimage = findViewById(R.id.addImage);
        //ProgressBar findView
        addprogress = findViewById(R.id.addProductProgressBar);;
        //Database Instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String prodName = productname.getText().toString();
                final String prodPrice = productprice.getText().toString();
                final String prodHarvestDate = productharvest.getText().toString();
                final String prodDescription = productdescription.getText().toString();
                productID = prodName;

                ProductModelProducer productModel = new ProductModelProducer(prodName,prodPrice,prodHarvestDate,prodDescription,productID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(productID).setValue(productModel);
                        Toast.makeText(AddActivityProducer.this, "Product Added...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivityProducer.this, HomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddActivityProducer.this, "Error is "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}