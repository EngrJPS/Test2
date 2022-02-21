package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ChangePasswordActivity extends AppCompatActivity {

    private MaterialEditText oldPass, newPass, confirmPass;
    private Button btnChangePass;
    private FirebaseAuth firebaseAuth;
    private ProgressBar loadingBar;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this, StartActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        oldPass = findViewById(R.id.txtOldPass);
        newPass = findViewById(R.id.txtNewPass);
        confirmPass = findViewById(R.id.txtConfirmPass);

        btnChangePass = findViewById(R.id.btnChangePass);
        loadingBar = findViewById(R.id.progressBarr);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oPass = oldPass.getText().toString();
                String nPass = newPass.getText().toString();
                String cPass = confirmPass.getText().toString();

                if(TextUtils.isEmpty(oPass) || TextUtils.isEmpty(nPass) || TextUtils.isEmpty(cPass)){
                    Toast.makeText(ChangePasswordActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                }else if(nPass.length() < 6){
                    Toast.makeText(ChangePasswordActivity.this, "The new password length should be more than 6 characters", Toast.LENGTH_SHORT).show();
                }else if(!cPass.equals(nPass)){
                    Toast.makeText(ChangePasswordActivity.this, "Confirm password does not match new password", Toast.LENGTH_SHORT).show();
                }else{
                    changePassword(oPass, nPass);
                }
            }

            private void changePassword(String oPass, String nPass) {
                loadingBar.setVisibility(View.VISIBLE);
                AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),oPass);
                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firebaseUser.updatePassword(nPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        firebaseAuth.signOut();
                                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            loadingBar.setVisibility(View.GONE);
                            Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}