package com.example.midtermsexam_beauty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.models.Product;

import java.util.List;
import java.util.Locale;

public class RestaurantFeedAdapter extends RecyclerView.Adapter<RestaurantFeedAdapter.ViewHolder> {
    private final Context context;
    private final List<Product> productList;
    private final ProductCard.OnItemClickListener listener;

    public RestaurantFeedAdapter(Context context, List<Product> productList, ProductCard.OnItemClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_explore_restaurant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(productList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productDescription;
        private final TextView productCategory;
        private final TextView productAvailability;
        private final TextView productPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.explore_product_image);
            productName = itemView.findViewById(R.id.explore_product_name);
            productDescription = itemView.findViewById(R.id.explore_product_description);
            productCategory = itemView.findViewById(R.id.explore_product_category);
            productAvailability = itemView.findViewById(R.id.explore_product_availability);
            productPrice = itemView.findViewById(R.id.explore_product_price);
        }

        void bind(Product product, ProductCard.OnItemClickListener listener) {
            productImage.setImageResource(product.getImageID());
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productCategory.setText(product.getCategory());
            productAvailability.setText(product.getAvalability() ? "Available" : "Sold out");
            productPrice.setText(String.format(Locale.US, "P%.2f", product.getPrice()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            });
        }
    }
}
