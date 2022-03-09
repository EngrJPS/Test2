package com.example.test2.productPanels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.test2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProducerOrderProduct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProductPendingOrders> productPendingOrdersList;
    private ProducerOrderProductAdapter producerOrderProductAdapter;
    private DatabaseReference databaseReference;
    private String RandomUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_order_product);
        recyclerView = (RecyclerView) findViewById(R.id.Recycle_orders_product);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productPendingOrdersList = new ArrayList<>();
        Producerorderproduct();
    }

    private void Producerorderproduct() {

        RandomUID = getIntent().getStringExtra("RandomUID");

        databaseReference = FirebaseDatabase.getInstance().getReference("ProducerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productPendingOrdersList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductPendingOrders productPendingOrders = dataSnapshot.getValue(ProductPendingOrders.class);
                    productPendingOrdersList.add(productPendingOrders);
                }

                producerOrderProductAdapter = new ProducerOrderProductAdapter(ProducerOrderProduct.this, productPendingOrdersList);
                recyclerView.setAdapter(producerOrderProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}