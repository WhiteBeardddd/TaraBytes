package com.example.midtermsexam_beauty.models;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    private String id;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("reviewer_id")
    private String reviewerId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("comment")
    private String comment; // nullable

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    // Getters
    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public String getReviewerId() { return reviewerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}