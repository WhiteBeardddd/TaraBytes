package com.example.midtermsexam_beauty.adapters;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageButton;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.views.user.Checkout;
import com.example.midtermsexam_beauty.views.user.FeaturedProducts;
import com.example.midtermsexam_beauty.views.user.Homepage;
import com.example.midtermsexam_beauty.views.user.PopularProducts;
import com.example.midtermsexam_beauty.views.user.SkinType;

public class NavbarCard {

    public static void setupNavbar(Activity activity) {
        ImageButton navHome = activity.findViewById(R.id.nav_home);
        ImageButton navRatings = activity.findViewById(R.id.nav_ratings);
        ImageButton navSkinTypes = activity.findViewById(R.id.nav_skin_types);
        ImageButton navFeatured = activity.findViewById(R.id.nav_featured);
        ImageButton navPayment = activity.findViewById(R.id.nav_payment);

        if (navHome != null) {
            navHome.setOnClickListener(v -> navigateTo(activity, Homepage.class));
        }

        if (navRatings != null) {
            navRatings.setOnClickListener(v -> navigateTo(activity, PopularProducts.class));
        }

        if (navSkinTypes != null) {
            navSkinTypes.setOnClickListener(v -> navigateTo(activity, SkinType.class));
        }

        if (navFeatured != null) {
            navFeatured.setOnClickListener(v -> navigateTo(activity, FeaturedProducts.class));
        }

        if (navPayment != null) {
            navPayment.setOnClickListener(v -> navigateTo(activity, Checkout.class));
        }
        
        // Highlight active icon
        highlightActiveIcon(activity, navHome, navRatings, navSkinTypes, navFeatured, navPayment);
    }

    private static void navigateTo(Activity activity, Class<?> targetClass) {
        if (!activity.getClass().equals(targetClass)) {
            Intent intent = new Intent(activity, targetClass);
            // Flags to prevent re-rendering the whole stack and keep it smooth
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.startActivity(intent);
            // Optional: disable transition animation
            activity.overridePendingTransition(0, 0);
        }
    }

    private static void highlightActiveIcon(Activity activity, ImageButton... buttons) {
        int orange = 0xFFFF5722; 
        
        if (activity instanceof Homepage && buttons[0] != null) buttons[0].setColorFilter(orange);
        else if (activity instanceof PopularProducts && buttons[1] != null) buttons[1].setColorFilter(orange);
        else if (activity instanceof SkinType && buttons[2] != null) buttons[2].setColorFilter(orange);
        else if (activity instanceof FeaturedProducts && buttons[3] != null) buttons[3].setColorFilter(orange);
        else if (activity instanceof Checkout && buttons[4] != null) buttons[4].setColorFilter(orange);
    }
}
