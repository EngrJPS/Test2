package com.example.test2.productPanels;

import com.example.test2.R;
import com.example.test2.SendNotif.APIService;
import com.example.test2.SendNotif.Client;
import com.example.test2.SendNotif.Data;
import com.example.test2.SendNotif.MyResponse;
import com.example.test2.SendNotif.NotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOrdertobePreparedView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProductWaitingOrders> productWaitingOrdersList;
    private ProductOrdertobePreparedViewAdapter adapter;
    private DatabaseReference reference;
    private String RandomUID, rUserId;
    private TextView totalPrice, note, address, name, phone;
    private LinearLayout linearLayout;
    private Button btnPreparing;
    private ProgressDialog progressDialog;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ordertobe_prepared_view);
        recyclerView = (RecyclerView) findViewById(R.id.Recycle_viewOrder);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        note = (TextView) findViewById(R.id.nt);
        address = (TextView) findViewById(R.id.ad);
        name = (TextView) findViewById(R.id.nm);
        phone = (TextView) findViewById(R.id.num);
        linearLayout = (LinearLayout) findViewById(R.id.button1);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(ProductOrdertobePreparedView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductOrdertobePreparedView.this));
        productWaitingOrdersList = new ArrayList<>();
        ProdutorderdishesView();

    }

    private void ProdutorderdishesView() {
        RandomUID = getIntent().getStringExtra("RandomUID");

        reference = FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productWaitingOrdersList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductWaitingOrders productWaitingOrders = snapshot.getValue(ProductWaitingOrders.class);
                    productWaitingOrdersList.add(productWaitingOrders);
                }
                if(productWaitingOrdersList.size() == 0){
                    linearLayout.setVisibility(View.INVISIBLE);
                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                    btnPreparing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        final ProductWaitingOrders productWaitingOrders= snapshot.getValue(ProductWaitingOrders.class);
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        String productId = productWaitingOrders.getProductId();
                                        rUserId = productWaitingOrders.getUserId();
                                        hashMap.put("ProducerId", productWaitingOrders.getProducerId());
                                        hashMap.put("ProductId", productWaitingOrders.getProductId());
                                        hashMap.put("ProductName", productWaitingOrders.getProductName());
                                        hashMap.put("ProductPrice", productWaitingOrders.getProductPrice());
                                        hashMap.put("ProductQuantity", productWaitingOrders.getProductQuantity());
                                        hashMap.put("RandomUID", RandomUID);
                                        hashMap.put("TotalPrice", productWaitingOrders.getTotalPrice());
                                        hashMap.put("UserId", productWaitingOrders.getUserId());
                                        FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes").child(productId).setValue(hashMap);
                                    }
                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            final ProductWaitingOrders1 productWaitingOrders1 = snapshot.getValue(ProductWaitingOrders1.class);
                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                            hashMap1.put("Address", productWaitingOrders1.getAddress());
                                            hashMap1.put("GrandTotalPrice", productWaitingOrders1.getGrandTotalPrice());
                                            hashMap1.put("MobileNumber", productWaitingOrders1.getPhone());
                                            hashMap1.put("Name", productWaitingOrders1.getName());
                                            hashMap1.put("RandomUID", RandomUID);
                                            hashMap1.put("Status", "Chef is preparing your Order...");
                                            FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(rUserId).child(RandomUID).child("OtherInformation").child("Status").setValue("Chef is preparing your order...").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("RetailerFinalOrders").child(rUserId).child(RandomUID).child("OtherInformation").child("Status").setValue("Chef is preparing your order...").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(rUserId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                    String userToken = snapshot.getValue(String.class);
                                                                                    sendNotifications(userToken, "Estimated Time","Producer is Preparing your Order","Preparing");
                                                                                    progressDialog.dismiss();
                                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductOrdertobePreparedView.this);
                                                                                    builder.setMessage("See Orders which are Prepared");
                                                                                    builder.setCancelable(false);
                                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                                            dialogInterface.dismiss();
                                                                                            Intent b = new Intent(ProductOrdertobePreparedView.this, ProductOrdertobePrepared.class);
                                                                                            startActivity(b);
                                                                                            finish();
                                                                                        }
                                                                                    });

                                                                                    AlertDialog alert = builder.create();
                                                                                    alert.show();

                                                                                }

                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
                adapter = new ProductOrdertobePreparedViewAdapter(ProductOrdertobePreparedView.this, productWaitingOrdersList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProductWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ProductWaitingOrders1 productWaitingOrders1 = snapshot.getValue(ProductWaitingOrders1.class);
                totalPrice.setText("Php "+ productWaitingOrders1.getGrandTotalPrice());
                phone.setText(productWaitingOrders1.getPhone());
                address.setText(productWaitingOrders1.getAddress());
                note.setText(productWaitingOrders1.getNote());
                name.setText(productWaitingOrders1.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String preparing) {
        Data data = new Data(title, message, preparing);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if(response.code() == 200){
                    if(response.body().success != 1){
                        Toast.makeText(ProductOrdertobePreparedView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}