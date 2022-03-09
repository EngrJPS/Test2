package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    private ImageView appName, appBck;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);

        appBck = findViewById(R.id.appImg);
        appName = findViewById(R.id.appName);
        animationView = findViewById(R.id.splashAnime);

        appBck.animate().translationY(-2500).setDuration(1000).setStartDelay(5000);
        appName.animate().translationY(2000).setDuration(1000).setStartDelay(5000);
        animationView.animate().translationY(1500).setDuration(1000).setStartDelay(5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));

            }
        }, 6000);

    }
}