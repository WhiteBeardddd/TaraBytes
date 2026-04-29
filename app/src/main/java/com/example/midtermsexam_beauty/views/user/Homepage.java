package com.example.midtermsexam_beauty.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.ProductCard;
import com.example.midtermsexam_beauty.models.Product;
import com.example.midtermsexam_beauty.adapters.NavbarCard;

import java.util.List;

public class Homepage extends AppCompatActivity {

//    private final ArrayList<OldProduct> popularProducts = new ArrayList<>();
//    private final ArrayList<OldProduct> featuredProducts = new ArrayList<>();
    ImageButton proceedToProfile;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        hideSystemUI();

        RecyclerView featuredListView = findViewById(R.id.featured_recycler);
        RecyclerView popularListView = findViewById(R.id.popular_recycler);

        proceedToProfile = findViewById(R.id.toProfile);

        NavbarCard.setupNavbar(this);

        List<Product> featuredProducts = Product.getMeals(this, "bestSellers");
        List<Product> popularProducts = Product.getMeals(this, "popular");

        ProductCard.OnItemClickListener listener = product -> {
            Toast.makeText(this, "Clicked: " + product.getName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ViewProductDetails.class);
            intent.putExtra("imageId", product.getImageID());
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("category", product.getCategory());
            intent.putExtra("availability", product.getAvalability());
            startActivity(intent);
        };

        // Adapters
        ProductCard featuredAdapter = new ProductCard(this, featuredProducts, listener);
        ProductCard popularAdapter = new ProductCard(this, popularProducts, listener);

        // Layouts
        featuredListView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        popularListView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        featuredListView.setAdapter(featuredAdapter);
        popularListView.setAdapter(popularAdapter);

        proceedToProfile.setOnClickListener(view -> {
            startActivity(new Intent(Homepage.this, UserProfile.class));
        });
    }
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }
}
