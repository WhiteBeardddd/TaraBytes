package com.example.midtermsexam_beauty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.models.MenuItem;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    public interface OnItemAction {
        void onAction(MenuItem item);
    }

    private final OnItemAction onEdit;
    private final OnItemAction onDelete;
    private final OnItemAction onToggle; // add this

    public MenuItemAdapter(List<MenuItem> items, OnItemAction onEdit, OnItemAction onDelete, OnItemAction onToggle) {
        this.items = items;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
        this.onToggle = onToggle; // add this
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = items.get(position);

        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvPrice.setText(String.format("₱%.2f", item.getPrice()));
        holder.tvCategory.setText(item.getCategory());

        // Set toggle without triggering listener
        holder.switchAvailable.setOnCheckedChangeListener(null);
        holder.switchAvailable.setChecked(item.isAvailable());
        updateStatusLabel(holder.tvStatus, item.isAvailable());

        // Toggle listener
        holder.switchAvailable.setOnCheckedChangeListener((btn, isChecked) -> {
            item.setAvailable(isChecked);
            updateStatusLabel(holder.tvStatus, isChecked);
            onToggle.onAction(item); // save to DB
        });

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            holder.ivImage.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(holder.ivImage);
        } else {
            holder.ivImage.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(v -> onEdit.onAction(item));
        holder.btnDelete.setOnClickListener(v -> onDelete.onAction(item));
    }

    private void updateStatusLabel(TextView tv, boolean isAvailable) {
        tv.setText(isAvailable ? "Available" : "Unavailable");
        tv.setTextColor(isAvailable ? 0xFF4CAF50 : 0xFFFF4444); // green / red
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvDescription, tvPrice, tvCategory, tvStatus;
        SwitchCompat switchAvailable;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage        = itemView.findViewById(R.id.ivImage);
            tvName         = itemView.findViewById(R.id.tvName);
            tvDescription  = itemView.findViewById(R.id.tvDescription);
            tvPrice        = itemView.findViewById(R.id.tvPrice);
            tvCategory     = itemView.findViewById(R.id.tvCategory);
            tvStatus       = itemView.findViewById(R.id.tvStatus);
            switchAvailable = itemView.findViewById(R.id.switchAvailable);
            btnEdit        = itemView.findViewById(R.id.btnEdit);
            btnDelete      = itemView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvDescription, tvPrice, tvCategory, tvStatus;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage        = itemView.findViewById(R.id.ivImage);
            tvName         = itemView.findViewById(R.id.tvName);
            tvDescription  = itemView.findViewById(R.id.tvDescription);
            tvPrice        = itemView.findViewById(R.id.tvPrice);
            tvCategory     = itemView.findViewById(R.id.tvCategory);
            tvStatus       = itemView.findViewById(R.id.tvStatus);
            btnEdit        = itemView.findViewById(R.id.btnEdit);
            btnDelete      = itemView.findViewById(R.id.btnDelete);
        }
    }
}