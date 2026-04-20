package com.example.midtermsexam_beauty.utilities;

import com.example.midtermsexam_beauty.models.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {
    private static ProductManager productInstance;

    private final Map<Product, Integer> productList;

    private ProductManager() {
        productList = new HashMap<>();
    }

    public static synchronized ProductManager getInstance() {
        if (productInstance == null) {
            productInstance = new ProductManager();
        }
        return productInstance;
    }

    public void addProduct(Product product, int quantity) {
        int currentQty = productList.getOrDefault(product, 0);
        productList.put(product, currentQty + quantity);
    }


    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public Map<Product, Integer> getProduct(){
        return productList;
    }

    public void clearProductList() {
        productList.clear();
    }
}
