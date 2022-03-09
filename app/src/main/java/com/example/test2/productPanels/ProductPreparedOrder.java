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

public class ProductPreparedOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProductFinalOrders1> productFinalOrders1List;
    private ProductPreparedOrderAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_prepared_order);

        recyclerView = (RecyclerView) findViewById(R.id.Recycle_preparedOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductPreparedOrder.this));
        productFinalOrders1List = new ArrayList<>();
        ProductPrepareOrders();
    }

    private void ProductPrepareOrders() {

        databaseReference = FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productFinalOrders1List.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ProductFinalOrders1 productFinalOrders1 = snapshot.getValue(ProductFinalOrders1.class);
                            productFinalOrders1List.add(productFinalOrders1);
                            adapter = new ProductPreparedOrderAdapter(ProductPreparedOrder.this, productFinalOrders1List);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}