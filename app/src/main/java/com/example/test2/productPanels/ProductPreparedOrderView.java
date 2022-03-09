package com.example.test2.productPanels;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.SendNotif.APIService;
import com.example.test2.SendNotif.Client;
import com.example.test2.SendNotif.Data;
import com.example.test2.SendNotif.MyResponse;
import com.example.test2.SendNotif.NotificationSender;
import com.example.test2.UsersData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPreparedOrderView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProductFinalOrders> productFinalOrdersList;
    private DatabaseReference databaseReference;
    private String RandomUID, rUserId;
    private TextView totalPrice, address, name, phone;
    private LinearLayout linearLayout;
    private Button prepared;
    private ProgressDialog progressDialog;
    private APIService apiService;
    private Spinner logistics;
    private String deliveryId = "oCpc4SwLVFbKO0fPdtp4R6bmDmI3";
    private ProductPreparedOrderViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_prepared_order_view);

        recyclerView = (RecyclerView) findViewById(R.id.Recycle_viewOrder);
        totalPrice = (TextView) findViewById(R.id.pri);
        address = (TextView) findViewById(R.id.Cadress);
        name = (TextView) findViewById(R.id.Cname);
        phone = (TextView) findViewById(R.id.Cnumber);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        logistics = (Spinner) findViewById(R.id.shipper);
        prepared = (Button) findViewById(R.id.prepared);
        
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(ProductPreparedOrderView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductPreparedOrderView.this));
        productFinalOrdersList = new ArrayList<>();
        ProducerorderproductView();

    }

    private void ProducerorderproductView() {

        RandomUID = getIntent().getStringExtra("RandomUID");

        databaseReference = FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productFinalOrdersList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductFinalOrders productFinalOrders = dataSnapshot.getValue(ProductFinalOrders.class);
                    productFinalOrdersList.add(productFinalOrders);
                }
                if(productFinalOrdersList.size() == 0){
                    linearLayout.setVisibility(View.INVISIBLE);
                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                    prepared.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    final UsersData usersData = snapshot.getValue(UsersData.class);
                                    final String producerName = usersData.getFullname();
                                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
                                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                final ProductFinalOrders productFinalOrders = dataSnapshot.getValue(ProductFinalOrders.class);
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                String productId = productFinalOrders.getProductId();
                                                rUserId = productFinalOrders.getUserId();
                                                hashMap.put("ProducerId", productFinalOrders.getProducerId());
                                                hashMap.put("ProductId", productFinalOrders.getProductId());
                                                hashMap.put("ProductName", productFinalOrders.getProductName());
                                                hashMap.put("ProductPrice", productFinalOrders.getProductPrice());
                                                hashMap.put("ProductQuantity", productFinalOrders.getProductQuantity());
                                                hashMap.put("RandomUID", RandomUID);
                                                hashMap.put("TotalPrice", productFinalOrders.getTotalPrice());
                                                hashMap.put("UserId", productFinalOrders.getUserId());
                                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(RandomUID).child("Products").child(productId).setValue(hashMap);
                                            }
                                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    final ProductFinalOrders1 productFinalOrders1 = snapshot.getValue(ProductFinalOrders1.class);
                                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                                    String producerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    hashMap1.put("Address", productFinalOrders1.getAddress());
                                                    hashMap1.put("ChefId", producerId);
                                                    hashMap1.put("ChefName", producerName);
                                                    hashMap1.put("GrandTotalPrice", productFinalOrders1.getGrandTotalPrice());
                                                    hashMap1.put("Phone", productFinalOrders1.getPhone());
                                                    hashMap1.put("Name", productFinalOrders1.getName());
                                                    hashMap1.put("RandomUID", RandomUID);
                                                    hashMap1.put("Status", "Order is Prepared");
                                                    hashMap1.put("UserId", rUserId);
                                                    FirebaseDatabase.getInstance().getReference("LogisticsShipOrders").child(deliveryId).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("RetailerFinalOrders").child(rUserId).child(RandomUID).child("OtherInformation").child("Status").setValue("Order is Prepared...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(rUserId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                            String usertoken = snapshot.getValue(String.class);
                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is Prepared", "Prepared");
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                        }
                                                                    });

                                                                    }
                                                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    String usertoken = snapshot.getValue(String.class);
                                                                    sendNotifications(usertoken, "New Order", "You have a New Order", "DeliveryOrder");
                                                                    progressDialog.dismiss();
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductPreparedOrderView.this);
                                                                    builder.setMessage("Order has been sent to shipper");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            dialogInterface.dismiss();
                                                                            Intent intent = new Intent(ProductPreparedOrderView.this, ProductPreparedOrder.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }
                                                                    });
                                                                    AlertDialog alertDialog = builder.create();
                                                                    alertDialog.show();
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

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
                adapter = new ProductPreparedOrderViewAdapter(ProductPreparedOrderView.this, productFinalOrdersList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProductFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductFinalOrders1 productFinalOrders1 = snapshot.getValue(ProductFinalOrders1.class);
                totalPrice.setText("Php "+ productFinalOrders1.getGrandTotalPrice());
                address.setText(productFinalOrders1.getAddress());
                name.setText(productFinalOrders1.getName());
                phone.setText(productFinalOrders1.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendNotifications(String usertoken, String title, String message, String prepared) {
        Data data = new Data (title, message, prepared);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if(response.code() == 200){
                    if (response.body().success != 1) {
                        Toast.makeText(ProductPreparedOrderView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}