package com.example.midtermsexam_beauty.models;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    private String id;

    @SerializedName("seller_id")
    private String sellerId;

    @SerializedName("buyer_id")
    private String buyerId;

    @SerializedName("buyer_address_id")
    private String buyerAddressId;

    @SerializedName("address")
    private String address;

    @SerializedName("total_amount")
    private double totalAmount;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String createdAt;

    // Getters
    public String getId() { return id; }
    public String getSellerId() { return sellerId; }
    public String getBuyerId() { return buyerId; }
    public String getBuyerAddressId() { return buyerAddressId; }
    public String getAddress() { return address; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
}