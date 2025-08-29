package com.hanan.parlourapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserName, tvEmail, tvPhone;
    private ImageView ivProfilePic;
    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // or your filename

        tvUserName = findViewById(R.id.tvUserName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        // fill UI
        populateProfile();

        btnEditProfile.setOnClickListener(v -> {
            // open edit screen (optional)
            // startActivity(new Intent(this, EditProfileActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // repopulate in case user updated profile
        populateProfile();
    }

    private void populateProfile() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String name = prefs.getString("user_name", null);
        String email = prefs.getString("user_email", null);
        String phone = prefs.getString("user_phone", null); // optional if you save phone

        if (name == null && email == null) {
            // not logged in — redirect to login or show guest
            // Option A: redirect
            // startActivity(new Intent(this, LoginActivity.class));
            // finish();

            // Option B: show guest text
            tvUserName.setText("Guest");
            tvEmail.setText("Not signed in");
            return;
        }

        tvUserName.setText(name != null ? name : "User");
        tvEmail.setText(email != null ? email : "");
        if (phone != null) tvPhone.setText(phone);

        // If you have a profile picture URL in prefs, load it with Glide/Picasso
        String imageUrl = prefs.getString("user_image_url", null);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Example using Glide (add dependency if you use it)
            // Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_profile).into(ivProfilePic);
        } else {
            ivProfilePic.setImageResource(R.drawable.ic_profile); // placeholder
        }
    }
}
