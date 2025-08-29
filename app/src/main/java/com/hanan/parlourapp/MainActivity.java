package com.hanan.parlourapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.Display.Mode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 promoSlider;
    private LinearLayout dotLayout;

    private final Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;
    private boolean isAutoSliding = false;

    private int totalSlides = 0;
    private List<ImageView> dotIndicators;

    // Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnDrawer;

    // Service buttons
    private ImageView serviceHair, serviceMakeup, serviceMehndi, serviceFacial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forceHighRefreshRate();

        // --- Views ---
        promoSlider = findViewById(R.id.promoSlider);
        bottomNavigationView = findViewById(R.id.bottomNav);
        dotLayout = findViewById(R.id.dotLayout);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnDrawer = findViewById(R.id.btnDrawer);

        serviceHair = findViewById(R.id.service_hair);
        serviceMakeup = findViewById(R.id.service_makeup);
        serviceMehndi = findViewById(R.id.service_mehndi);
        serviceFacial = findViewById(R.id.service_facial);

        drawerLayout.setScrimColor(0x99000000);
        drawerLayout.setDrawerElevation(24f);

        btnDrawer.setOnClickListener(v ->
                drawerLayout.post(() -> drawerLayout.openDrawer(GravityCompat.START, true))
        );

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override public void onDrawerOpened(@NonNull View drawerView) { stopAutoSlide(); }
            @Override public void onDrawerClosed(@NonNull View drawerView)  { startAutoSlide(); }
        });

        // --- Drawer item clicks ---
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);

            drawerLayout.postDelayed(() -> {
                if (id == R.id.drawer_home || id == R.id.drawer_services) {
                    // Already here, do nothing
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, ProfileActivity.class));
                } else if (id == R.id.drawer_contact) {
                    startActivity(new Intent(this, ContactActivity.class));
                } else if (id == R.id.drawer_logout) {
                    showLogoutDialog();
                }
            }, 150);

            return true;
        });

        // --- Slider data ---
        List<PromoItem> promoItems = new ArrayList<>();
        promoItems.add(new PromoItem("Relax & get 20% Off!", R.drawable.slider1));
        promoItems.add(new PromoItem("Glow Up Today – Book Now!", R.drawable.slider2));
        promoItems.add(new PromoItem("Pamper Yourself – Limited Offer", R.drawable.slider3));
        totalSlides = promoItems.size();

        PromoSliderAdapter sliderAdapter = new PromoSliderAdapter(promoItems);
        promoSlider.setAdapter(sliderAdapter);

        promoSlider.setOffscreenPageLimit(Math.max(1, totalSlides - 1));
        promoSlider.setUserInputEnabled(true);
        promoSlider.setOverScrollMode(View.OVER_SCROLL_NEVER);

        promoSlider.setPageTransformer((page, position) -> {
            float scale = 0.95f + (1f - Math.abs(position)) * 0.05f;
            page.setScaleY(scale);
            page.setAlpha(0.8f + (1f - Math.abs(position)) * 0.2f);
        });

        setupDots(totalSlides);
        updateDots(0);

        promoSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                updateDots(position);
                if (isAutoSliding) {
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable, 4000);
                }
            }
        });

        // Bottom Nav
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_services) {
                Toast.makeText(this, "Bookings clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // --- Service buttons ---
        serviceHair.setOnClickListener(v -> startActivity(new Intent(this, HairStylingActivity.class)));
        serviceMakeup.setOnClickListener(v -> startActivity(new Intent(this, MakeupActivity.class)));
        serviceMehndi.setOnClickListener(v -> startActivity(new Intent(this, MehndiActivity.class)));
        serviceFacial.setOnClickListener(v -> startActivity(new Intent(this, FacialActivity.class)));
    }

    // --- Logout feature ---
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> performLogout())
                .setNegativeButton("No", null)
                .show();
    }

    private void performLogout() {
        // Clear user session
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        prefs.edit().clear().apply();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to login screen
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // --- Dots ---
    private void setupDots(int count) {
        dotIndicators = new ArrayList<>();
        dotLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotLayout.addView(dot);
            dotIndicators.add(dot);
        }
    }

    private void updateDots(int currentIndex) {
        if (dotIndicators == null) return;
        for (int i = 0; i < dotIndicators.size(); i++) {
            ImageView dot = dotIndicators.get(i);
            if (i == currentIndex) {
                dot.setImageResource(R.drawable.dot_active);
                dot.animate().scaleX(1.15f).scaleY(1.15f).setDuration(150).start();
            } else {
                dot.setImageResource(R.drawable.dot_inactive);
                dot.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
            }
        }
    }

    // --- Auto slide ---
    private void startAutoSlide() {
        if (isAutoSliding || totalSlides <= 1) return;
        if (sliderRunnable == null) {
            sliderRunnable = new Runnable() {
                @Override public void run() {
                    if (totalSlides <= 1) return;
                    int next = (promoSlider.getCurrentItem() + 1) % totalSlides;
                    promoSlider.setCurrentItem(next, true);
                    sliderHandler.postDelayed(this, 4000);
                }
            };
        }
        isAutoSliding = true;
        sliderHandler.postDelayed(sliderRunnable, 4000);
    }

    private void stopAutoSlide() {
        isAutoSliding = false;
        if (sliderRunnable != null) {
            sliderHandler.removeCallbacks(sliderRunnable);
        }
    }

    @Override protected void onStart() {
        super.onStart();
        startAutoSlide();
    }

    @Override protected void onStop() {
        super.onStop();
        stopAutoSlide();
    }

    private void forceHighRefreshRate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Display display = getDisplay();
            if (display != null) {
                Mode[] modes = display.getSupportedModes();
                Mode bestMode = display.getMode();
                for (Mode mode : modes) {
                    if (mode.getRefreshRate() > bestMode.getRefreshRate()) {
                        bestMode = mode;
                    }
                }
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.preferredDisplayModeId = bestMode.getModeId();
                getWindow().setAttributes(lp);
            }
        }
    }
}
