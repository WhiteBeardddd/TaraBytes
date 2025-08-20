package com.example.midtermsexam_beauty;

import android.content.Context;

import com.example.midtermsexam_beauty.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProductParser {

    // Updated to load from specific categories (e.g., popularProducts or featuredProducts)
    public static List<Product> loadProductsFromAsset(Context context, String fileName, String key) {
        List<Product> productList = new ArrayList<>();

        try {
            String jsonString = loadJSONFromAsset(context, fileName);
            JSONObject jsonObject = new JSONObject(jsonString);

            // Use the dynamic key to parse popular or featured products
            JSONArray productsArray = jsonObject.getJSONArray(key);

            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObj = productsArray.getJSONObject(i);

                String name = productObj.getString("name");
                String description = productObj.getString("description");
                double price = productObj.getDouble("price");
                String imageName = productObj.getString("imageId"); // Corrected key
                int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                double rating = productObj.getDouble("rating");
                String skinType = productObj.getString("skin_type");
                String category = productObj.getString("category");

                // Create Product object
                Product product = new Product(imageResId, name, (float) price, description, 0, (float) rating, category, skinType);
                productList.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Method to load JSON from asset folder
    private static String loadJSONFromAsset(Context context, String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
