package com.example.midtermsexam_beauty.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.CheckOutCard;
import com.example.midtermsexam_beauty.models.Product;
import com.example.midtermsexam_beauty.adapters.NavbarCard;
import com.example.midtermsexam_beauty.utilities.ProductManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Checkout extends AppCompatActivity {
    private List<Product> productList;
    private TextView totalPriceText;

    ImageButton toPrevious;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        hideSystemUI();

        // Initialize Navbar
        NavbarCard.setupNavbar(this);

        toPrevious = findViewById(R.id.back_btn);

        ListView cartListView = findViewById(R.id.cart_list);
        totalPriceText = findViewById(R.id.total_price); // Get Total Price TextView

        Map<Product, Integer> cartMap = ProductManager.getInstance().getProduct();
        productList = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            Product product = entry.getKey();
            product.setCounter(entry.getValue());
            productList.add(product);
        }

        CheckOutCard checkOutAdapter = new CheckOutCard(this, productList);
        cartListView.setAdapter(checkOutAdapter);

        updateTotalPrice();

        // Navigation Buttons
        toPrevious.setOnClickListener(view -> finish());
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


    @SuppressLint("DefaultLocale")
    private void updateTotalPrice() {
        double total = 0;
        for (Product product : productList) {
            total += product.getPrice() * product.getCounter(); // Multiply price by quantity
        }
        totalPriceText.setText(String.format("₱%.2f", total)); // Format price to 2 decimal places
    }
}
