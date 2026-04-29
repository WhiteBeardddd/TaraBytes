package com.example.midtermsexam_beauty.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.NavbarCard;
import com.example.midtermsexam_beauty.adapters.ProductCard;
import com.example.midtermsexam_beauty.adapters.RestaurantFeedAdapter;
import com.example.midtermsexam_beauty.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {

    private RecyclerView popularListView;
    private LinearLayout explorePaginationIndicator;
    private PagerSnapHelper exploreSnapHelper;
    private LinearLayoutManager exploreLayoutManager;
    private final List<ImageView> exploreDots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        RecyclerView featuredListView = findViewById(R.id.featured_recycler);
        popularListView = findViewById(R.id.popular_recycler);
        explorePaginationIndicator = findViewById(R.id.explore_pagination_indicator);
        EditText searchEditText = findViewById(R.id.searchEditText);

        NavbarCard.setupNavbar(this);

        List<Product> featuredProducts = getStaticFeaturedShops();
        List<Product> popularProducts = Product.getMeals(this, "popular");

        ProductCard.OnItemClickListener listener = product -> {
            Toast.makeText(this, "Clicked: " + product.getName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ViewProductDetails.class);
            intent.putExtra("imageId", product.getImageID());
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("rating", product.getRating());
            intent.putExtra("category", product.getCategory());
            intent.putExtra("skin_type", product.getSkin_type());
            intent.putExtra("availability", product.getAvalability());
            startActivity(intent);
        };

        ProductCard featuredAdapter = new ProductCard(this, featuredProducts, listener);
        RestaurantFeedAdapter popularAdapter = new RestaurantFeedAdapter(this, popularProducts, listener);

        featuredListView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        exploreLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularListView.setLayoutManager(exploreLayoutManager);

        featuredListView.setAdapter(featuredAdapter);
        popularListView.setAdapter(popularAdapter);

        setupExplorePagination(popularProducts.size());

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startActivity(new Intent(this, PopularProducts.class));
                v.clearFocus();
            }
        });
    }

    private void setupExplorePagination(int itemCount) {
        if (exploreSnapHelper == null) {
            exploreSnapHelper = new PagerSnapHelper();
            exploreSnapHelper.attachToRecyclerView(popularListView);
        }

        buildExploreDots(itemCount);
        updateExploreDots(0);

        popularListView.clearOnScrollListeners();
        popularListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateExploreIndicatorFromSnap();
            }
        });

        popularListView.post(this::updateExploreIndicatorFromSnap);
    }

    private void buildExploreDots(int itemCount) {
        exploreDots.clear();
        explorePaginationIndicator.removeAllViews();

        for (int i = 0; i < itemCount; i++) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(8), dpToPx(8));
            if (i > 0) {
                params.setMarginStart(dpToPx(8));
            }
            dot.setLayoutParams(params);
            dot.setImageResource(R.drawable.indicator_dot_inactive);
            explorePaginationIndicator.addView(dot);
            exploreDots.add(dot);
        }
    }

    private void updateExploreIndicatorFromSnap() {
        if (exploreSnapHelper == null || exploreLayoutManager == null) {
            return;
        }

        android.view.View snappedView = exploreSnapHelper.findSnapView(exploreLayoutManager);
        if (snappedView == null) {
            return;
        }

        int position = exploreLayoutManager.getPosition(snappedView);
        if (position != RecyclerView.NO_POSITION) {
            updateExploreDots(position);
        }
    }

    private void updateExploreDots(int activePosition) {
        for (int i = 0; i < exploreDots.size(); i++) {
            exploreDots.get(i).setImageResource(
                    i == activePosition
                            ? R.drawable.indicator_dot_active
                            : R.drawable.indicator_dot_inactive
            );
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }

    private List<Product> getStaticFeaturedShops() {
        List<Product> shops = new ArrayList<>();
        shops.add(new Product(
                R.drawable.product_1,
                "Minute Burger",
                "Quick burgers and budget-friendly bites that are easy to grab anytime.",
                99.00f,
                "Fast Food",
                true,
                4.8f,
                "Always featured"
        ));
        shops.add(new Product(
                R.drawable.product_2,
                "Jollibee",
                "Well-known comfort food with crowd favorites and familiar combo meals.",
                149.00f,
                "Chicken & Rice",
                true,
                4.9f,
                "Always featured"
        ));
        shops.add(new Product(
                R.drawable.product_3,
                "McDonalds",
                "Reliable fast-food staples with burgers, fries, and drinks users already know.",
                139.00f,
                "Burgers",
                true,
                4.7f,
                "Always featured"
        ));
        shops.add(new Product(
                R.drawable.product_4,
                "KFC",
                "Crispy chicken meals and box deals that fit the featured restaurant lane well.",
                179.00f,
                "Fried Chicken",
                true,
                4.8f,
                "Always featured"
        ));
        return shops;
    }
}
