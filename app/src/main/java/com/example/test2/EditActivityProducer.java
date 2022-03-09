package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class EditActivityProducer extends AppCompatActivity {

    private MaterialEditText editProdName, editProdPrice, editProdDate, editProdDescription;
    private Button btnUpdate, btnDelete;
    private ProgressBar loadingPB;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String productID;
    private ProductModelProducer productModelProducer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_producer);

        editProdName = findViewById(R.id.editProductName);
        editProdPrice = findViewById(R.id.editProductPrice);
        editProdDate = findViewById(R.id.editProductHarvest);
        editProdDescription = findViewById(R.id.editProductDescription);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        loadingPB = findViewById(R.id.editPB);

        if(productModelProducer != null){
            editProdName.setText(productModelProducer.getProdName());
            editProdPrice.setText(productModelProducer.getProdPrice());
            editProdDate.setText(productModelProducer.getProdHarvestDate());
            editProdDescription.setText(productModelProducer.getProdDescription());
            productID = productModelProducer.getProdID();
        }

        databaseReference = firebaseDatabase.getReference("Products").child(productID);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String eProdName = editProdName.getText().toString();
                String eProdPrice = editProdPrice.getText().toString();
                String eProdDate = editProdDate.getText().toString();
                String eProdDescription = editProdDescription.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("prodName",eProdName);
                map.put("prodPrice",eProdPrice);
                map.put("prodHarvestDate",eProdDate);
                map.put("prodDescription",eProdDescription);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(EditActivityProducer.this, "Product Successfully updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditActivityProducer.this, HomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditActivityProducer.this, "Failed to update course", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });
    }

    private void deleteProduct(){
        databaseReference.removeValue();
        Toast.makeText(this, "Product Deleted...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditActivityProducer.this,HomeActivity.class));
    }

}