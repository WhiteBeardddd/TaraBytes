package com.example.midtermsexam_beauty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.models.MenuItem;
import java.util.List;

public class SellerMenuAdapter extends RecyclerView.Adapter<SellerMenuAdapter.ViewHolder> {

    private List<MenuItem> items;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(MenuItem item);
        void onDelete(MenuItem item);
    }

    public SellerMenuAdapter(List<MenuItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateList(List<MenuItem> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvCategory.setText(item.getCategory());
        holder.tvPrice.setText(String.format("₱%.2f", item.getPrice()));
        holder.tvStatus.setText(item.isAvailable() ? "Available" : "Unavailable");
        holder.tvStatus.setTextColor(item.isAvailable() ? 0xFF2ED573 : 0xFFFF4757);

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(item));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(item));
        
        // Placeholder image logic
        holder.imgProduct.setImageResource(R.drawable.product_1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvCategory, tvPrice, tvStatus;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
