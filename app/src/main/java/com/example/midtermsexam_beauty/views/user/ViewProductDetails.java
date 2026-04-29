package com.example.midtermsexam_beauty.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.NavbarCard;

import java.util.Locale;

public class ViewProductDetails extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private TextView productCategory;
    private TextView productAvailability;
    private ImageButton toPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_details);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productCategory = findViewById(R.id.product_category);
        productAvailability = findViewById(R.id.product_availability);
        toPrevious = findViewById(R.id.back_btn);

        NavbarCard.setupNavbar(this);

        Intent intent = getIntent();
        int imageId = intent.getIntExtra("imageId", 0);
        String name = intent.getStringExtra("name");
        float price = intent.getFloatExtra("price", 0.0f);
        String description = intent.getStringExtra("description");
        String category = intent.getStringExtra("category");
        boolean availability = intent.getBooleanExtra("availability", true);

        productImage.setImageResource(imageId);
        productName.setText(name);
        productPrice.setText(String.format(Locale.US, "P%.2f", price));
        productDescription.setText(description);
        productCategory.setText(category);
        productAvailability.setText(availability ? "Available now" : "Currently unavailable");

        toPrevious.setOnClickListener(v -> finish());
    }
}
