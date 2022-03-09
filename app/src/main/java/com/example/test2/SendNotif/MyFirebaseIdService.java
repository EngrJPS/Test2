package com.example.test2.SendNotif;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.test2.productPanels.ProductPanel_BottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseIdService extends MyFirebaseMessagingService{

    @Override
    public void onNewToken(@NonNull String s){
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                }
                String token = task.getResult();

                if(firebaseUser != null){
                    updateToken(token);
                }
            }
        });
    }

    private void updateToken(String token) {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Token token1=new Token(token);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
    }
}
