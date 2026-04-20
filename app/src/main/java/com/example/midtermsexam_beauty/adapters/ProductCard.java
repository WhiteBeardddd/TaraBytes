package com.example.midtermsexam_beauty.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.models.Product;
import com.example.midtermsexam_beauty.utilities.ProductManager;

import java.util.List;

public class ProductCard extends RecyclerView.Adapter<ProductCard.ViewHolder> {
    private final Context context;
    private final List<Product> productList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    public ProductCard(Context context, List<Product> productList, OnItemClickListener lis) {
        this.context = context;
        this.productList = productList;
        listener = lis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView displayImage;
        TextView productName, productPrice, productDescription, productCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productCategory = itemView.findViewById(R.id.product_category);
            productDescription = itemView.findViewById(R.id.product_description);
        }
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind (Product product, OnItemClickListener listener) {

            if(product == null) return;

            displayImage.setImageResource(product.getImageID());
            productName.setText(product.getName());
            productCategory.setText(product.getCategory());
            productPrice.setText(String.format("₱%.2f", product.getPrice()));
            productDescription.setText(product.getDescription());

            itemView.setOnClickListener(v -> {
                if(listener != null) {
                    listener.onItemClick(product);
                }
            });

        }
    }
}
