package com.example.midtermsexam_beauty.views.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.SkinTypeCard;
import com.example.midtermsexam_beauty.models.Product;
import com.example.midtermsexam_beauty.adapters.NavbarCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SkinType extends AppCompatActivity {

//    private final ArrayList<OldProduct> skinTypeList = new ArrayList<>();
    private SkinTypeCard SkinTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_type);
        hideSystemUI();

        // Initialize Navbar
        NavbarCard.setupNavbar(this);

        // Initialize Buttons
        ImageButton toPrevious = findViewById(R.id.back_btn);

        // Initialize RecyclerView & Spinner
        RecyclerView skinTypeListView = findViewById(R.id.skinTypeListView);
        Spinner skinTypeSpinner = findViewById(R.id.skinTypeSpinner);

        // Dynamic skin types list (new types can be added)
        String[] options = {"All", "Oily", "Dry", "Combination", "Normal", "Ranked", "Hot", "Secret", "Loved"};

        // Set up Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinTypeSpinner.setAdapter(adapter);

        // Load products
//        skinTypeList.addAll(OldProduct.getPopularProducts(this));
//        skinTypeList.addAll(OldProduct.getDefaultProducts(this));

        // Set up RecyclerView Adapter
//        SkinTypeAdapter = new SkinTypeCard(this, new ArrayList<>(skinTypeList));

        // Set Grid Layout (2 columns)
        skinTypeListView.setLayoutManager(new GridLayoutManager(this, 2));
        skinTypeListView.setAdapter(SkinTypeAdapter);

        // Spinner Selection Listener to filter products
        skinTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSkinType = (String) parent.getItemAtPosition(position);
                filterProductsBySkinType(selectedSkinType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterProductsBySkinType("All");
            }
        });

        // Navigation Buttons
        toPrevious.setOnClickListener(view -> finish());
    }

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(
                    WindowInsets.Type.systemBars()
            );
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    private void filterProductsBySkinType(String selectedSkinType) {
//        List<OldProduct> filteredList = new ArrayList<>();
//        List<OldProduct> allProducts = new ArrayList<>();
//        allProducts.addAll(OldProduct.getPopularProducts(this));
//        allProducts.addAll(OldProduct.getDefaultProducts(this));

        String selectedType = selectedSkinType.toLowerCase();

//        if (selectedType.equals("ranked")) {
//            // Sort products by rating from highest to lowest
//            allProducts.sort(Comparator.comparingDouble(OldProduct::getRating).reversed());
//            filteredList.addAll(allProducts);
//        } else {
//            // Filter by skin type && Category
//            for (OldProduct product : allProducts) {
//                if (selectedType.equals("all") || product.getSkin_type().toLowerCase().contains(selectedType)) {
//                    filteredList.add(product);
//                } else if (product.getCategory().toLowerCase().contains(selectedType)) {
//                    filteredList.add(product);
//                }
//            }
//        }
//
//        // Update RecyclerView with filtered data
//        SkinTypeAdapter.updateList(filteredList);
    }
}
