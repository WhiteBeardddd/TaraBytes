package com.example.midtermsexam_beauty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.models.OldProduct;

import java.util.List;
import java.util.Locale;

public class PopularAndFeaturedAdapter extends BaseAdapter {
    private final Context context;
    private final List<OldProduct> productList;

    public PopularAndFeaturedAdapter(Context context, List<OldProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.featured_and_popular_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OldProduct product = productList.get(position);
        holder.productImage.setImageResource(product.getImageId());
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format(Locale.US, "P%.2f", product.getPrice()));
        holder.productDescription.setText(product.getDescription());
        holder.productRating.setText(String.format(Locale.US, "%.1f", product.getRating()));
        holder.productCategory.setText("Category: " + product.getCategory());
        holder.productSkinType.setText("Best for: " + product.getSkin_type());

        return convertView;
    }

    private static class ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productDescription, productRating, productCategory, productSkinType;

        ViewHolder(View view) {
            productImage = view.findViewById(R.id.large_product_image);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            productDescription = view.findViewById(R.id.product_description);
            productRating = view.findViewById(R.id.product_rating);
            productCategory = view.findViewById(R.id.product_category);
            productSkinType = view.findViewById(R.id.product_skin_type);
        }
    }
}
