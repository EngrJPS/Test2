package com.example.test2.productPanels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.test2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ProductOrdertobePrepared extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProductWaitingOrders1> productWaitingOrders1List;
    private ProductOrdertobePreparedAdapter adapter;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ordertobe_prepared);
        recyclerView = findViewById(R.id.Recycle_orderstobeprepared);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductOrdertobePrepared.this));
        productWaitingOrders1List = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.Swipe1);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);
        adapter = new ProductOrdertobePreparedAdapter(ProductOrdertobePrepared.this, productWaitingOrders1List);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productWaitingOrders1List.clear();
                recyclerView = findViewById(R.id.Recycle_orderstobeprepared);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProductOrdertobePrepared.this));
                productWaitingOrders1List = new ArrayList<>();
                productsorderstoprepare();
            }
        });
        productsorderstoprepare();
    }

    private void productsorderstoprepare() {
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    productWaitingOrders1List.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final ProductWaitingOrders1 productWaitingOrders1 = dataSnapshot.getValue(ProductWaitingOrders1.class);
                                productWaitingOrders1List.add(productWaitingOrders1);
                                adapter = new ProductOrdertobePreparedAdapter(ProductOrdertobePrepared.this, productWaitingOrders1List);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}