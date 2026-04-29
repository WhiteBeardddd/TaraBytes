package com.example.midtermsexam_beauty.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.PopularAndFeaturedAdapter;
import com.example.midtermsexam_beauty.models.Product;
import com.example.midtermsexam_beauty.adapters.NavbarCard;

import java.util.ArrayList;

public class FeaturedProducts extends AppCompatActivity {

    private final ArrayList<OldProduct> featuredProducts = new ArrayList<>();

    ImageButton toPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_featured_products);
        hideSystemUI();

        // Initialize Navbar
        NavbarCard.setupNavbar(this);

        toPrevious = findViewById(R.id.back_btn);

        ListView featuredListView = findViewById(R.id.featured_recycler);

        featuredProducts.addAll(OldProduct.getDefaultProducts(this));


        PopularAndFeaturedAdapter adapter = new PopularAndFeaturedAdapter(this, featuredProducts);
        featuredListView.setAdapter(adapter);
        featuredListView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = featuredProducts.get(position);
            openProductDetails(product);
        });

        toPrevious.setOnClickListener(view -> finish());
    }

    private void openProductDetails(Product product) {
        Intent intent = new Intent(this, ViewProductDetails.class);
        intent.putExtra("imageId", product.getImageId());
        intent.putExtra("name", product.getName());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("rating", product.getRating());
        intent.putExtra("category", product.getCategory());
        intent.putExtra("skin_type", product.getSkin_type());
        startActivity(intent);
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
