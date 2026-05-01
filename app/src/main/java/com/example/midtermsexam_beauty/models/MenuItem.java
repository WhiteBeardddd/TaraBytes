package com.example.midtermsexam_beauty.models;

import com.google.gson.annotations.SerializedName;

public class MenuItem {
    @SerializedName("id")
    private String id;

    @SerializedName("seller_id")
    private String sellerId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    @SerializedName("category")
    private String category;

    @SerializedName("is_available")
    private boolean isAvailable;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public MenuItem() {}

    // Getters
    public String getId() { return id; }
    public String getSellerId() { return sellerId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }
    public String getImageUrl() { return imageUrl; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
