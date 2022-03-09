package com.example.test2.productPanels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test2.ImageRecyclerAdapter;
import com.example.test2.ImagesList;
import com.example.test2.R;
import com.example.test2.UsersData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;


import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductPost extends AppCompatActivity {

    private Button postProduct;
    private Spinner products;
    private MaterialEditText dateHarvested, qtyInKg, price;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,dataa;
    private FirebaseAuth Fauth;
    private StorageReference ref;
    private String ProducerId, RandomUID,State, City , Area;
    private String prod, dHarvest, qty, prc;
    private UsersData usersData;
    private CircleImageView imageButton;
    private ImageRecyclerAdapter imageRecyclerAdapter;
    private List<ImagesList> imagesList ;
    private FirebaseUser firebaseUser;
    private static final int IMAGE_REQUEST = 1;
    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_post);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        products = (Spinner)findViewById(R.id.productName);
        dateHarvested = (MaterialEditText) findViewById(R.id.productDateHarvested);
        qtyInKg = (MaterialEditText) findViewById(R.id.txtProductQuantity);
        price = (MaterialEditText) findViewById(R.id.txtProductQuantity);
        postProduct = (Button) findViewById(R.id.post);
        Fauth = FirebaseAuth.getInstance();
        firebaseUser = Fauth.getCurrentUser();

        databaseReference = firebaseDatabase.getInstance().getReference("ProductDetails");

        try{
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = firebaseDatabase.getInstance().getReference("Users").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usersData = snapshot.getValue(UsersData.class);
                    assert usersData != null;

                    State = usersData.getStatee();
                    City = usersData.getCityy();
                    Area = usersData.getAddress();

                    imageButton = (CircleImageView) findViewById(R.id.image_upload);

                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ProductPost.this);
                            builder.setCancelable(true);
                            View mView = LayoutInflater.from(ProductPost.this).inflate(R.layout.select_image_layout, null);
                            RecyclerView recyclerView = mView.findViewById(R.id.recyclerView);
                            collectOldData();
                            recyclerView.setLayoutManager(new GridLayoutManager(ProductPost.this, 3));
                            recyclerView.setHasFixedSize(true);
                            imageRecyclerAdapter = new ImageRecyclerAdapter(imagesList, ProductPost.this);
                            recyclerView.setAdapter(imageRecyclerAdapter);
                            imageRecyclerAdapter.notifyDataSetChanged();
                            Button openImage = mView.findViewById(R.id.btnOpenImage);
                            openImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    openImage();
                                }
                            });
                            builder.setView(mView);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }

                        private void collectOldData() {
                            DatabaseReference imageListReference = FirebaseDatabase.getInstance().getReference("ProductDetails").child(firebaseUser.getUid());
                            imageListReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    imagesList.clear();
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        imagesList.add(dataSnapshot.getValue(ImagesList.class));
                                    }

                                    imageRecyclerAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ProductPost.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });

                    postProduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prod = products.getSelectedItem().toString().trim();
                            dHarvest = dateHarvested.getText().toString().trim();
                            qty = qtyInKg.getText().toString().trim();
                            prc = price.getText().toString().trim();

                            uploadImage();

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }
    }


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void uploadImage() {
        if(imageUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(ProductPost.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ProducerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ProductPostModel info = new ProductPostModel(prod,dHarvest,prc,qty,String.valueOf(uri),RandomUID,ProducerId);
                            firebaseDatabase.getInstance().getReference("ProductDetails").child(State).child(City).child(Area).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ProductPost.this,"Product Posted Successfully!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ProductPost.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            if(storageTask != null && storageTask.isInProgress()){
                Toast.makeText(this, "Uploading is in progress", Toast.LENGTH_SHORT).show();
            }else{
                uploadImage();
            }
        }
    }
}