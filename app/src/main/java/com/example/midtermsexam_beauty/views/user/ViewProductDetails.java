package com.example.midtermsexam_beauty.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.NavbarHelper;

import java.util.Locale;

public class ViewProductDetails extends AppCompatActivity {
    ImageView productImage;
    TextView productName, productPrice, productDescription,
            productRating, productCategory, productSkinType;
    ImageButton toPrevious;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_product_details);
        hideSystemUI();

        productImage = findViewById(R.id.display_image);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        productRating = findViewById(R.id.productRating);
        productCategory = findViewById(R.id.productCategory);
        productSkinType = findViewById(R.id.productSkinType);
        toPrevious = findViewById(R.id.back_btn);

        NavbarHelper.setupNavbar(this);

        Intent intent = getIntent();
        int imageId = intent.getIntExtra("imageId", 0);
        String name = intent.getStringExtra("name");
        float price = intent.getFloatExtra("price", 0.0f);
        String description = intent.getStringExtra("description");
        float rating = intent.getFloatExtra("rating", 0.0f);
        String category = intent.getStringExtra("category");
        String skinType = intent.getStringExtra("skin_type");

        productImage.setImageResource(imageId);
        productName.setText(name);
        productPrice.setText(String.format(Locale.US, "P%.2f", price));
        productDescription.setText(description);
        productRating.setText(String.format(Locale.US, "Rating: %.1f", rating));
        productCategory.setText(category);
        productSkinType.setText(skinType);

        toPrevious.setOnClickListener(v -> finish());
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
