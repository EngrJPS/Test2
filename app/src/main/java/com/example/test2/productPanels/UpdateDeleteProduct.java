package com.example.test2.productPanels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class UpdateDeleteProduct extends AppCompatActivity {

    private MaterialEditText dateHarvested, qtyInKg, price;
    private TextView prodName;
    private CircleImageView circleImageView;
    private Uri imageUri;
    private Button updateBtn, deleteBtn;
    private String  RandomUID, State, City, rUserId, Area, dburi;
    private StorageReference ref;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private FirebaseAuth Fauth;
    private FirebaseUser firebaseUser;
    private ProducthomeAdapter producthomeAdapter;
    private StorageTask storageTask;
    private static final int IMAGE_REQUEST = 1;
    private ProgressDialog progressDialog;
    private DatabaseReference dataaa;
    private String ID;
    private UsersData usersData;
    private List<ImagesList> imagesList ;
    private ImageRecyclerAdapter imageRecyclerAdapter;
    private String dHarvest, qtyinKg, pric, productname, ProducerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();;
        dateHarvested = (MaterialEditText) findViewById(R.id.productDateHarvested);
        qtyInKg = (MaterialEditText) findViewById(R.id.txtProductQuantity);
        price = (MaterialEditText) findViewById(R.id.txtProductQuantity);
        updateBtn = (Button) findViewById(R.id.update);
        deleteBtn = (Button) findViewById(R.id.delete);
        Fauth = FirebaseAuth.getInstance();
        firebaseUser = Fauth.getCurrentUser();
        prodName = (TextView) findViewById(R.id.textView);
        circleImageView = (CircleImageView) findViewById(R.id.image_upload);

        ID = getIntent().getStringExtra("updatedeletedproduct");

        rUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = firebaseDatabase.getInstance().getReference("Users").child(rUserId);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersData usersData = snapshot.getValue(UsersData.class);
                State = usersData.getStatee();
                City = usersData.getCityy();
                Area = usersData.getAddress();

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                    }

                    @Override
                    public void onClick(View view) {
                        final String dHarvested = dateHarvested.getText().toString().trim();
                        final String quantity = qtyInKg.getText().toString().trim();
                        final String prc = price.getText().toString().trim();

                        if(imageUri != null){
                            uploadImage();
                        }else{
                            updatedEsc(dburi);
                        }
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteProduct.this);
                        builder.setMessage("Are you sure you want to Delete Dish");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Area).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();

                                AlertDialog.Builder product = new AlertDialog.Builder(UpdateDeleteProduct.this);
                                product.setMessage("Your Dish has been Deleted");
                                product.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(UpdateDeleteProduct.this, ProductPanel_BottomNavigation.class));
                                    }
                                });
                                AlertDialog alert = product.create();
                                alert.show();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                progressDialog = new ProgressDialog(UpdateDeleteProduct.this);
                dataaa = FirebaseDatabase.getInstance().getReference("ProductSupplyDetailsModel").child(State).child(City).child(Area).child(rUserId).child(ID);
                dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UpdateProductModel updateProductModel = snapshot.getValue(UpdateProductModel.class);
                        dateHarvested.setText(updateProductModel.getDateHarvested());
                        qtyInKg.setText(updateProductModel.getQuantity());
                        price.setText(updateProductModel.getPrice());
                        prodName.setText("Product Name: "+ updateProductModel.getProductName());
                        productname = updateProductModel.getProductName();
                        Glide.with(UpdateDeleteProduct.this).load(updateProductModel.getImageURL()).into(circleImageView);
                        dburi = updateProductModel.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Fauth = FirebaseAuth.getInstance();
                dataaa = firebaseDatabase.getInstance().getReference("ProductSupplyDetailsModel");
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();

                circleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteProduct.this);
                        builder.setCancelable(true);
                        View mView = LayoutInflater.from(UpdateDeleteProduct.this).inflate(R.layout.select_image_layout, null);
                        RecyclerView recyclerView = mView.findViewById(R.id.recyclerView);
                        collectOldData();
                        recyclerView.setLayoutManager(new GridLayoutManager(UpdateDeleteProduct.this, 3));
                        recyclerView.setHasFixedSize(true);
                        imageRecyclerAdapter = new ImageRecyclerAdapter(imagesList, UpdateDeleteProduct.this);
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
                        DatabaseReference imageListReference = FirebaseDatabase.getInstance().getReference("ProductSupplyDetailsModel").child(firebaseUser.getUid());
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
                                Toast.makeText(UpdateDeleteProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void uploadImage() {
        if(imageUri != null){
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updatedEsc(String.valueOf(uri));
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateDeleteProduct.this, "Failed : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    private void updatedEsc(String uri){
        rUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ProductSupplyDetailsModel info = new ProductSupplyDetailsModel(dHarvest, qtyinKg, pric, productname, uri, ID, rUserId);
        firebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Area)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(UpdateDeleteProduct.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
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