package com.example.test2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    String[] Maharashtra = {"Mumbai", "Pune", "Nashik"};
    String[] Madhyapradesh = {"Bhopal","Indore","Ujjain"};

    private MaterialEditText name, email, pass, phone, houseNo, address, pincode;
    private Spinner stateSpin, citySpin;
    private CountryCodePicker Cpp;

    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private Button btn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;

    private String full_name, cluster, barangay, e_mail, pass_word, num, hNo, hAddress, pinCode, occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeactivity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            startActivity (new Intent(RegisterActivity.this, LoginActivity.class));
        });

        firebaseAuth = FirebaseAuth.getInstance();

        pass = (MaterialEditText) findViewById(R.id.txtPass);
        name = (MaterialEditText) findViewById(R.id.txtName);
        phone = (MaterialEditText) findViewById(R.id.txtPhone);
        email = (MaterialEditText) findViewById(R.id.txtEmail);
        houseNo = (MaterialEditText) findViewById(R.id.txtHandSNum);
        address = (MaterialEditText) findViewById(R.id.txtAddress);
        pincode = (MaterialEditText) findViewById(R.id.txtPincode);

        stateSpin = (Spinner) findViewById(R.id.Statee);
        citySpin = (Spinner)findViewById(R.id.Citys);

        Cpp = (CountryCodePicker) findViewById(R.id.CountryCode);

        progressBar = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioButtons);

        btn = findViewById(R.id.btnRegister);

        stateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object value = parent.getItemAtPosition(position);
                cluster = value.toString().trim();
                if(cluster.equals("Maharashtra")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : Maharashtra){
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,list);
                    citySpin.setAdapter(arrayAdapter);
                }
                if(cluster.equals("Madhyapradesh")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : Madhyapradesh){
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,list);
                    citySpin.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                barangay = value.toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn.setOnClickListener(v->{
            full_name = name.getText().toString().trim();
            e_mail = email.getText().toString().trim();
            pass_word = pass.getText().toString().trim();
            num = phone.getText().toString().trim();
            hNo = houseNo.getText().toString().trim();
            hAddress = address.getText().toString().trim();
            pinCode = pincode.getText().toString().trim();

            String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            boolean isValidEmail = false;

            int checkId = radioGroup.getCheckedRadioButtonId();

            RadioButton position = radioGroup.findViewById(checkId);

            if(position == null){
                Toast.makeText(RegisterActivity.this, "Please Select Desired User As!", Toast.LENGTH_SHORT).show();
            }else {
                occupation = position.getText().toString();

                if(TextUtils.isEmpty(full_name) || TextUtils.isEmpty(e_mail) || TextUtils.isEmpty(pass_word) ||
                        TextUtils.isEmpty(num)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                }else{
                    register(full_name, e_mail, pass_word, num, occupation, hNo, hAddress, pinCode);
                }

                if(TextUtils.isEmpty(e_mail)){
                    email.setError("Email is required");
                }else{
                    if(!e_mail.matches(emailpattern)){
                        Toast.makeText(RegisterActivity.this, "Please pick a valid email address!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void register(String full_name, String e_mail, String pass_word, String num, String occupation, String hNo, String hAddress, String pinCode) {
        progressBar.setVisibility(View.VISIBLE);

        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Registration is in progress please wait......");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(e_mail,pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser rUser = firebaseAuth.getCurrentUser();
                    assert rUser != null;
                    String userId = rUser.getUid();

                    db = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("userId", userId);
                    hashMap.put("fullname", full_name);
                    hashMap.put("email", e_mail);
                    hashMap.put("phone", num);
                    hashMap.put("occupation", occupation);
                    hashMap.put("house", hNo);
                    hashMap.put("address", hAddress);
                    hashMap.put("pincode", pinCode);
                    hashMap.put("city", barangay);
                    hashMap.put("state", cluster);
                    hashMap.put("imageURL", "default");

                    db.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);

                            progressDialog.dismiss();

                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}