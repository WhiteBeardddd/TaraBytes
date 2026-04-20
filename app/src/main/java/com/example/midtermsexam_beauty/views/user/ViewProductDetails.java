package com.example.midtermsexam_beauty.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;

public class ViewProductDetails extends AppCompatActivity {
    ImageView productImage;
    TextView productName, productPrice, productDescription, productCategory, productQuantity;
    Button addBtn, minusBtn, confirmBtn;

    int currentQuantity = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product_view_details);
        hideSystemUI();

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productCategory = findViewById(R.id.product_category);

        addBtn = findViewById(R.id.add_btn);
        productQuantity = findViewById(R.id.product_quantity);
        minusBtn = findViewById(R.id.minus_btn);
        confirmBtn = findViewById(R.id.confirm_buy_btn);


        // Get product details from intent
        Intent intent = getIntent();
        int imageId = intent.getIntExtra("imageId", 0);
        String name = intent.getStringExtra("name");
        float price = intent.getFloatExtra("price", 0.0f);
        String description = intent.getStringExtra("description");
        String category = intent.getStringExtra("category");

        // SET VALUES
        productImage.setImageResource(imageId);
        productName.setText(name != null ? name : "No Name");
        productPrice.setText("₱" + price);
        productDescription.setText(description != null ? description : "No Description");
        productCategory.setText(category != null ? category : "No Category");
        productQuantity.setText(String.valueOf(currentQuantity));

        // Add Button
        addBtn.setOnClickListener(v -> {
            currentQuantity++;
            productQuantity.setText(String.valueOf(currentQuantity));
        });

        // Minus Button
        minusBtn.setOnClickListener(v -> {
            if (currentQuantity > 0) {
                currentQuantity--;
                productQuantity.setText(String.valueOf(currentQuantity));
            }
        });

        // Confirm Button
        confirmBtn.setOnClickListener(v -> {
            if (currentQuantity <= 0) {
                Toast.makeText(this, "Please select quantity first!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Added " + currentQuantity + " item(s) to cart!",
                        Toast.LENGTH_SHORT).show();
            }
        });
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