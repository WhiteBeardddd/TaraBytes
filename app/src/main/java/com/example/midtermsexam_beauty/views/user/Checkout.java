package com.example.midtermsexam_beauty.views.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.CheckOutCard;
import com.example.midtermsexam_beauty.adapters.NavbarCard;
import com.example.midtermsexam_beauty.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {
    private final List<Product> productList = new ArrayList<>();
    private TextView totalPriceText;
    private ImageButton toPrevious;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        NavbarCard.setupNavbar(this);

        toPrevious = findViewById(R.id.back_btn);
        ListView cartListView = findViewById(R.id.cart_list);
        totalPriceText = findViewById(R.id.total_price);

        CheckOutCard checkOutAdapter = new CheckOutCard(this, productList);
        cartListView.setAdapter(checkOutAdapter);

        updateTotalPrice();
        toPrevious.setOnClickListener(view -> finish());
    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPrice() {
        double total = 0;
        for (Product product : productList) {
            total += product.getPrice() * product.getCounter();
        }
        totalPriceText.setText(String.format("?%.2f", total));
    }
}
