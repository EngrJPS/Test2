package com.example.test2.productPanels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.test2.ReusableCodes.ReusableCodes;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductpendingorderAdapter extends RecyclerView.Adapter<ProductpendingorderAdapter.ViewHolder>{

    private Context context;
    private List<ProductPendingOrders1> productPendingOrders1;
    private APIService apiService;
    private String producerId, productId, userId;

    public ProductpendingorderAdapter(Context context, List<ProductPendingOrders1> productPendingOrders1) {
        this.context = context;
        this.productPendingOrders1 = productPendingOrders1;
    }

    @NonNull
    @Override
    public ProductpendingorderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producer_orders, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new ProductpendingorderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductpendingorderAdapter.ViewHolder holder, int position) {
        final ProductPendingOrders1 pendingOrders1 = productPendingOrders1.get(position);
        holder.address.setText(pendingOrders1.getAddress());
        holder.totalPrice.setText("Total price: Php " + pendingOrders1.getTotalPrice());
        final String random = pendingOrders1.getRandomUID();
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProducerOrderProduct.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
            }
        });

        holder.acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Products");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            final ProductPendingOrders productPendingOrders = dataSnapshot.getValue(ProductPendingOrders.class);
                            HashMap<String, String> hashMap = new HashMap<>();
                            String producerId = productPendingOrders.getProducerId();
                            String productId = productPendingOrders.getProductId();
                            hashMap.put("ProducerId", productPendingOrders.getProducerId());
                            hashMap.put("ProductId", productPendingOrders.getProductId());
                            hashMap.put("ProductName", productPendingOrders.getProductName());
                            hashMap.put("ProductPrice", productPendingOrders.getProductPrice());
                            hashMap.put("ProductQuantity", productPendingOrders.getProductQty());
                            hashMap.put("RandomUID", random);
                            hashMap.put("TotalPrice", productPendingOrders.getTotalPrice());
                            hashMap.put("UserId", productPendingOrders.getUserId());
                            FirebaseDatabase.getInstance().getReference("ProductPaymentOrders").child(producerId).child(random).child("Products").child(productId).setValue(hashMap);
                        }
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ProductPendingOrders1 pendingOrders1 = snapshot.getValue(ProductPendingOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("Address", pendingOrders1.getAddress());
                                hashMap1.put("GrandTotalPrice", pendingOrders1.getTotalPrice());
                                hashMap1.put("MobileNumber", pendingOrders1.getPhone());
                                hashMap1.put("Name", pendingOrders1.getName());
                                hashMap1.put("Note",pendingOrders1.getNote());
                                hashMap1.put("RandomUID", random);
                                FirebaseDatabase.getInstance().getReference("ProductPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes");
                                        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                    final ProductPendingOrders productPendingOrders = snapshot.getValue(ProductPendingOrders.class);
                                                    HashMap<String, String> hashMap2 = new HashMap<>();
                                                    String producerId = productPendingOrders.getUserId();
                                                    String productId = productPendingOrders.getProductId();
                                                    hashMap2.put("producerId", productPendingOrders.getProducerId());
                                                    hashMap2.put("productId", productPendingOrders.getProductId());
                                                    hashMap2.put("ProductName", productPendingOrders.getProductName());
                                                    hashMap2.put("ProductPrice", productPendingOrders.getProductPrice());
                                                    hashMap2.put("ProductQuantity", productPendingOrders.getProductQty());
                                                    hashMap2.put("RandomUID", random);
                                                    hashMap2.put("TotalPrice", productPendingOrders.getTotalPrice());
                                                    hashMap2.put("UserId", productPendingOrders.getUserId());
                                                    FirebaseDatabase.getInstance().getReference("RetailerPaymentOrders").child(producerId).child(random).child("Product").child(productId).setValue(hashMap2);
                                                }
                                                DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        ProductPendingOrders1 pendingOrders1 = snapshot.getValue(ProductPendingOrders1.class);
                                                        HashMap<String, String> hashMap3 = new HashMap<>();
                                                        hashMap3.put("Address", pendingOrders1.getAddress());
                                                        hashMap3.put("GrandTotalPrice", pendingOrders1.getTotalPrice());
                                                        hashMap3.put("MobileNumber", pendingOrders1.getPhone());
                                                        hashMap3.put("Name", pendingOrders1.getName());
                                                        hashMap3.put("Note",pendingOrders1.getNote());
                                                        hashMap3.put("RandomUID", random);
                                                        FirebaseDatabase.getInstance().getReference("RetailerPaymentOrders").child(producerId).child(random).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                FirebaseDatabase.getInstance().getReference("RetailerPendingOrders").child(producerId).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("RetailerPendingOrders").child(producerId).child(random).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void unused) {
                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(producerId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        String usertoken = snapshot.getValue(String.class);
                                                                                                        sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Chef, Now make Payment for Order", "Payment");
                                                                                                        ReusableCodes.ShowAlert(context,"","Wait for the Customer to make Payment");
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
        holder.rejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Products");
                Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            final ProductPendingOrders productPendingOrders = snapshot.getValue(ProductPendingOrders.class);
                            userId = productPendingOrders.getUserId();
                            productId = productPendingOrders.getProductId();
                        }
                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(producerId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String usertoken = snapshot.getValue(String.class);
                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Chef due to some Circumstances", "Home");
                                FirebaseDatabase.getInstance().getReference("RetailerPendingOrders").child(userId).child(random).child("Products").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("RetailerPendingOrders").child(userId).child(random).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                       FirebaseDatabase.getInstance().getReference("ProductsPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void unused) {
                                                               FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userId).child("isOrdered").setValue("false");
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

    private void sendNotifications(String usertoken, String title, String message, String order) {
        Data data = new Data(title, message, order);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return productPendingOrders1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView address, totalPrice;
        private Button viewOrder, acceptOrder, rejectOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.AD);
            totalPrice = itemView.findViewById(R.id.TP);
            viewOrder = itemView.findViewById(R.id.vieww);
            acceptOrder = itemView.findViewById(R.id.accept);
            rejectOrder = itemView.findViewById(R.id.reject);
        }
    }
}
