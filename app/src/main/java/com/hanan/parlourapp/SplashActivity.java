package com.hanan.parlourapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Make sure activity_splash.xml exists

        // Delay to show splash for 2 seconds
        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            String userId = prefs.getString("user_id", null);

            if (userId != null) {
                // User already logged in → go to main
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                // User not logged in → go to login
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            finish(); // close splash so user can’t return
        }, 2000); // 2000ms = 2 sec
    }
}
