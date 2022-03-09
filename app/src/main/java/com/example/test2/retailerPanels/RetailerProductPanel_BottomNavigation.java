package com.example.test2.retailerPanels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.SendNotif.Token;
import com.example.test2.StartActivity;
import com.example.test2.productPanels.ProductPanel_BottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class RetailerProductPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_product_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragementTransaction = fragmentManager.beginTransaction();
        if (name != null){
            if(name.equalsIgnoreCase("Homepage")){
                loadFragment(new RetailerHomeFragment());
            }else if(name.equalsIgnoreCase("Preparingpage")){
                loadFragment(new RetailerTrackFragment());
            }else if(name.equalsIgnoreCase("Preparedpage")){
                loadFragment(new RetailerTrackFragment());
            }else if(name.equalsIgnoreCase("Deliverpage")){
                loadFragment(new RetailerTrackFragment());
            }else if(name.equalsIgnoreCase("Thankyoupage")){
                loadFragment(new RetailerHomeFragment());
            }
        }else{
            loadFragment(new RetailerHomeFragment());
        }
    }

    private void UpdateToken(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(RetailerProductPanel_BottomNavigation.this, "Error! "+task.getException(), Toast.LENGTH_SHORT).show();
                }
                String token = task.getResult();
                Token token1 = new Token(token);
                FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
            }
        });
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return false;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        final Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.Home:
                fragment = new RetailerHomeFragment();
                break;

            case R.id.Cart:
                fragment = new RetailerCartFragment();
                break;

            case R.id.Order:
                fragment = new RetailerOrderFragment();
                break;

            case R.id.Track:
                fragment = new RetailerTrackFragment();
                break;

            case R.id.Profile:
                fragment = new RetailerProfileFragment();
                break;

        }
        return loadFragment (fragment);
    }
}