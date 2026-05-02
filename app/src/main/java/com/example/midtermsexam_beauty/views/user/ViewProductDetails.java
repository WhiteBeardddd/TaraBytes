package com.example.midtermsexam_beauty.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.midtermsexam_beauty.R;

import java.util.Locale;

public class ViewProductDetails extends AppCompatActivity {
    private static final String DEFAULT_BRANCH = "Placeholder Branch";
    private static final String DEFAULT_SHOP_NAME = "Restaurant Placeholder";
    private static final float DEFAULT_RATING = 5.0f;

    private ImageView shopCoverImage;
    private ImageView shopLogoImage;
    private TextView shopLogoInitials;
    private TextView shopTitle;
    private TextView shopRating;
    private TextView shopSearchLabel;
    private TextView shopSectionNote;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_details);

        bindViews();
        styleViews();
        bindShopHeader(readShopPayload());
        backButton.setOnClickListener(v -> finish());
    }

    private void bindViews() {
        shopCoverImage = findViewById(R.id.shop_cover_image);
        shopLogoImage = findViewById(R.id.shop_logo_image);
        shopLogoInitials = findViewById(R.id.shop_logo_initials);
        shopTitle = findViewById(R.id.shop_title);
        shopRating = findViewById(R.id.rating_text);
        shopSearchLabel = findViewById(R.id.shop_search_label);
        shopSectionNote = findViewById(R.id.shop_section_note);
        backButton = findViewById(R.id.back_btn);
    }

    private void styleViews() {
        shopRating.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
    }

    private ShopPayload readShopPayload() {
        Intent intent = getIntent();
        return new ShopPayload(
                intent.getIntExtra("imageId", R.drawable.product_1),
                intent.getIntExtra("logoImageId", 0),
                sanitize(intent.getStringExtra("name"), DEFAULT_SHOP_NAME),
                intent.getFloatExtra("rating", DEFAULT_RATING)
        );
    }

    private void bindShopHeader(ShopPayload payload) {
        shopCoverImage.setImageResource(payload.coverImageId);
        bindLogo(payload);
        shopTitle.setText(buildBranchTitle(payload.shopName));
        shopRating.setText(buildRatingLabel(payload.rating));
        shopSearchLabel.setText(buildSearchLabel());
        shopSectionNote.setText(buildSectionNote(payload.shopName));
    }

    private void bindLogo(ShopPayload payload) {
        if (payload.logoImageId != 0) {
            shopLogoImage.setVisibility(View.VISIBLE);
            shopLogoInitials.setVisibility(View.GONE);
            shopLogoImage.setImageResource(payload.logoImageId);
            return;
        }

        shopLogoImage.setVisibility(View.GONE);
        shopLogoInitials.setVisibility(View.VISIBLE);
        shopLogoInitials.setText(buildInitials(payload.shopName));
    }

    private String buildBranchTitle(String shopName) {
        return String.format(Locale.US, "%s - %s", shopName, DEFAULT_BRANCH);
    }

    private String buildRatingLabel(float rating) {
        float resolvedRating = rating > 0 ? rating : DEFAULT_RATING;
        return String.format(Locale.US, "%.1f (100+ ratings)", resolvedRating);
    }

    private String buildSearchLabel() {
        return "Search menu";
    }

    private String buildSectionNote(String shopName) {
        return String.format(Locale.US, "Most ordered items for %s will be shown here once the menu data is wired.", shopName);
    }

    private String buildInitials(String shopName) {
        String[] parts = shopName.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty() && Character.isLetterOrDigit(part.charAt(0))) {
                initials.append(Character.toUpperCase(part.charAt(0)));
            }
            if (initials.length() == 2) {
                break;
            }
        }

        return initials.length() > 0 ? initials.toString() : "TB";
    }

    private String sanitize(String value, String fallback) {
        if (value == null) {
            return fallback;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? fallback : trimmed;
    }

    private static final class ShopPayload {
        private final int coverImageId;
        private final int logoImageId;
        private final String shopName;
        private final float rating;

        private ShopPayload(int coverImageId, int logoImageId, String shopName, float rating) {
            this.coverImageId = coverImageId;
            this.logoImageId = logoImageId;
            this.shopName = shopName;
            this.rating = rating;
        }
    }
}
