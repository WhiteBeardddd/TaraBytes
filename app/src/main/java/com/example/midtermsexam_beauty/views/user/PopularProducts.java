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
import com.example.midtermsexam_beauty.adapters.NavbarHelper;

import java.util.ArrayList;

public class PopularProducts extends AppCompatActivity {

    private final ArrayList<Product> popularProducts = new ArrayList<>();

    ImageButton toPrevious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_popular_products);
        hideSystemUI();

        // Initialize Navbar
        NavbarHelper.setupNavbar(this);

        ListView popularListView = findViewById(R.id.popular_recycler);

        popularProducts.addAll(Product.getPopularProducts(this));
        PopularAndFeaturedAdapter popularAdapter = new PopularAndFeaturedAdapter(this, popularProducts);
        popularListView.setAdapter(popularAdapter);


        toPrevious = findViewById(R.id.back_btn);

        // Navigation Buttons
        toPrevious.setOnClickListener(view -> startActivity(new Intent(PopularProducts.this, Homepage.class)));
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