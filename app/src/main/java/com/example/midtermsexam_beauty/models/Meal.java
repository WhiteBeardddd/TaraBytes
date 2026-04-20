
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
    private final boolean availability;

    public Meal(int imageID, String name, String description, float price, String category, boolean availability) {
        this.imageID = imageID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.availability = availability;
    }

    public int getImageID() {return imageID;}

    public String getName() {return name;}

    public String getDescription() {return  description;}

    public float getPrice() {return price;}

    public String getCategory() {return category;}

    public boolean getAvalability() {return  availability;}

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

                int imageID = res.getIdentifier(obj.getString("imageID"), "drawable", context.getPackageName());
                String name = obj.getString("name");
                float price = (float) obj.getDouble("price");
                String description = obj.getString("description");
                String category = obj.getString("category");
                boolean availability = obj.getBoolean("availability");

                Meal addMeal = new Meal(imageID,name, description, price, category, availability);
                mealList.add(addMeal);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return mealList;
    }

    public static List<Meal> getMeals(Context context, String fromWhere) {
        return loadMealsFromJSON(context, "meals.json", fromWhere);
    }
}

