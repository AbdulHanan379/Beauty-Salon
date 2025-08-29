package com.hanan.parlourapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Get data from intent
        String name = getIntent().getStringExtra("style_name");
        int price = getIntent().getIntExtra("style_price", 0);
        String category = getIntent().getStringExtra("service_category"); // NEW
        String id = getIntent().getStringExtra("service_id"); // optional, if needed later

        // Find Views
        TextView tvCategory = findViewById(R.id.tvBookingPrice);
        TextView tvTitle = findViewById(R.id.tvBookingTitle);
        TextView tvPrice = findViewById(R.id.tvBookingPrice);
        Button btnConfirm = findViewById(R.id.btnConfirmBooking);

        // Set Category
        if (category != null) {
            tvCategory.setText("Category: " + category);
        } else {
            tvCategory.setText("Category: Not specified");
        }

        // Set Service Name
        tvTitle.setText(name != null ? name : "Unknown Service");

        // Format Price
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "PK"));
        tvPrice.setText("PKR " + nf.format(price));

        // Confirm Button Action
        btnConfirm.setOnClickListener(v ->
                Toast.makeText(this, "Booked: " + category + " - " + name, Toast.LENGTH_SHORT).show()
        );
    }
}
