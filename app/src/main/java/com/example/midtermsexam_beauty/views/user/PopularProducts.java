package com.example.midtermsexam_beauty.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.NavbarCard;
import com.example.midtermsexam_beauty.adapters.PopularAndFeaturedAdapter;
import com.example.midtermsexam_beauty.models.Product;

import java.util.ArrayList;

public class PopularProducts extends AppCompatActivity {

    private final ArrayList<Product> popularProducts = new ArrayList<>();
    private ImageButton toPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_products);

        NavbarCard.setupNavbar(this);

        ListView popularListView = findViewById(R.id.popular_recycler);
        popularProducts.addAll(Product.getMeals(this, "popular"));

        PopularAndFeaturedAdapter popularAdapter = new PopularAndFeaturedAdapter(this, popularProducts);
        popularListView.setAdapter(popularAdapter);
        popularListView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = popularProducts.get(position);
            openProductDetails(product);
        });

        toPrevious = findViewById(R.id.back_btn);
        toPrevious.setOnClickListener(view -> finish());
    }

    private void openProductDetails(Product product) {
        Intent intent = new Intent(this, ViewProductDetails.class);
        intent.putExtra("imageId", product.getImageID());
        intent.putExtra("name", product.getName());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("rating", product.getRating());
        intent.putExtra("category", product.getCategory());
        intent.putExtra("skin_type", product.getSkin_type());
        intent.putExtra("availability", product.getAvalability());
        startActivity(intent);
    }
}
