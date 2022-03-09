package com.example.test2.productPanels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.SendNotif.Token;
import com.example.test2.productPanels.ProductHomeFragment;
import com.example.test2.productPanels.ProductOrderFragment;
import com.example.test2.productPanels.ProductPendingOrdersFragment;
import com.example.test2.productPanels.ProductProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class ProductPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.product_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        if(name != null){
            if (name.equalsIgnoreCase("Orderpage")){
                loadProducerFragment(new ProductPendingOrdersFragment());

            }else if (name.equalsIgnoreCase("Confirmpage")){
                loadProducerFragment(new ProductOrderFragment());

            }else if(name.equalsIgnoreCase("AcceptOrderpage")){
                loadProducerFragment(new ProductHomeFragment());
            }
        }else{
            loadProducerFragment(new ProductHomeFragment());
        }
    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(ProductPanel_BottomNavigation.this, "Error! "+task.getException(), Toast.LENGTH_SHORT).show();
                }
                String token = task.getResult();
                Token token1 = new Token(token);

                FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
            }
        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.producerHome:
                fragment=new ProductHomeFragment();
                break;
            case R.id.pendingOrders:
                fragment=new ProductPendingOrdersFragment();
                break;
            case R.id.orders:
                fragment=new ProductOrderFragment();
                break;
            case R.id.producerProfile:
                fragment=new ProductProfileFragment();
                break;
        }
        return loadProducerFragment(fragment);
    }
    private boolean loadProducerFragment(Fragment fragment) {

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }

}