
package com.example.midtermsexam_beauty.models;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private final int imageID;

    private final String name;
    private final String description;
    private final float price;
    private final String category;
    private final boolean isAvailable;

    public Meal(int imageID, String name, String description, float price, String category, boolean isAvailable) {
        this.imageID = imageID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public int getImageID() {return imageID;}

    public String getName() {return name;}

    public String getDescription() {return  description;}

    public float getPrice() {return price;}

    public String getCategory() {return category;}

    public boolean getAvalability() {return  isAvailable;}

    private static List<Meal> loadMealsFromJSON(Context context, String fileName, String key) {
        List<Meal> mealList = new ArrayList<>();

        try {
            InputStream writer = context.getAssets().open(fileName);
            int size = writer.available();
            byte[] buffer = new byte[size];

            writer.read(buffer);
            writer.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray mealsArray = jsonObject.getJSONArray(key);

            Resources res = context.getResources();

            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject obj = mealsArray.getJSONObject(i);

                int imageID = res.getIdentifier()
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return mealList;
    }
}
