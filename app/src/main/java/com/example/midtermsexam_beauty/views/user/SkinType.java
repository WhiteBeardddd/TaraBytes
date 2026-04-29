package com.example.midtermsexam_beauty.views.user;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.NavbarCard;
import com.example.midtermsexam_beauty.adapters.SkinTypeCard;
import com.example.midtermsexam_beauty.models.OldProduct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SkinType extends AppCompatActivity {

    private final ArrayList<OldProduct> skinTypeList = new ArrayList<>();
    private SkinTypeCard skinTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_type);

        NavbarCard.setupNavbar(this);

        ImageButton toPrevious = findViewById(R.id.back_btn);
        RecyclerView skinTypeListView = findViewById(R.id.skinTypeListView);
        Spinner skinTypeSpinner = findViewById(R.id.skinTypeSpinner);

        String[] options = {"All", "Oily", "Dry", "Combination", "Normal", "Ranked", "Hot", "Secret", "Loved"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinTypeSpinner.setAdapter(adapter);

        skinTypeList.addAll(OldProduct.getPopularProducts(this));
        skinTypeList.addAll(OldProduct.getDefaultProducts(this));

        skinTypeAdapter = new SkinTypeCard(this, new ArrayList<>(skinTypeList));
        skinTypeListView.setLayoutManager(new GridLayoutManager(this, 2));
        skinTypeListView.setAdapter(skinTypeAdapter);

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

        toPrevious.setOnClickListener(view -> finish());
    }

    private void filterProductsBySkinType(String selectedSkinType) {
        List<OldProduct> filteredList = new ArrayList<>();
        List<OldProduct> allProducts = new ArrayList<>();
        allProducts.addAll(OldProduct.getPopularProducts(this));
        allProducts.addAll(OldProduct.getDefaultProducts(this));

        String selectedType = selectedSkinType.toLowerCase();

        if (selectedType.equals("ranked")) {
            allProducts.sort(Comparator.comparingDouble(OldProduct::getRating).reversed());
            filteredList.addAll(allProducts);
        } else {
            for (OldProduct product : allProducts) {
                if (selectedType.equals("all") || product.getSkin_type().toLowerCase().contains(selectedType)) {
                    filteredList.add(product);
                } else if (product.getCategory().toLowerCase().contains(selectedType)) {
                    filteredList.add(product);
                }
            }
        }

        skinTypeAdapter.updateList(filteredList);
    }
}
