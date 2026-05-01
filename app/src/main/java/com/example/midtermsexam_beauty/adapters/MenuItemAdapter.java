package com.example.midtermsexam_beauty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.models.MenuItem;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    public interface OnItemAction {
        void onAction(MenuItem item);
    }

    private List<MenuItem> items;
    private final OnItemAction onEdit;
    private final OnItemAction onDelete;
    private final OnItemAction onToggle;

    public MenuItemAdapter(List<MenuItem> items, OnItemAction onEdit, OnItemAction onDelete, OnItemAction onToggle) {
        this.items = items;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
        this.onToggle = onToggle;
    }

    public void updateList(List<MenuItem> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seller_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = items.get(position);

        holder.tvName.setText(item.getName());
        holder.tvCategory.setText(item.getCategory());
        holder.tvPrice.setText(String.format("₱%.2f", item.getPrice()));

        // Set toggle without triggering listener
        holder.switchAvailable.setOnCheckedChangeListener(null);
        holder.switchAvailable.setChecked(item.isAvailable());
        updateStatusLabel(holder.tvStatus, item.isAvailable());

        // Toggle listener
        holder.switchAvailable.setOnCheckedChangeListener((btn, isChecked) -> {
            item.setAvailable(isChecked);
            updateStatusLabel(holder.tvStatus, isChecked);
            onToggle.onAction(item);
        });

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.product_1)
                    .centerCrop()
                    .into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(R.drawable.product_1);
        }

        holder.btnEdit.setOnClickListener(v -> onEdit.onAction(item));
        holder.btnDelete.setOnClickListener(v -> onDelete.onAction(item));
    }

    private void updateStatusLabel(TextView tv, boolean isAvailable) {
        tv.setText(isAvailable ? "Available" : "Unavailable");
        tv.setTextColor(isAvailable ? 0xFF2ED573 : 0xFFFF4757);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvCategory, tvPrice, tvStatus;
        SwitchMaterial switchAvailable;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            switchAvailable = itemView.findViewById(R.id.switchAvailable);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
