package com.example.midtermsexam_beauty.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.views.seller.CurrentOrders;
import com.example.midtermsexam_beauty.views.seller.SellerDashboard;
import com.example.midtermsexam_beauty.views.seller.SellerMenu;
import com.example.midtermsexam_beauty.views.seller.SellerUserProfile;
import com.example.midtermsexam_beauty.views.seller.Transactions;

public class SellerNavCard {

    private static final int ACTIVE_COLOR = 0xFF2ED573;
    private static final int INACTIVE_COLOR = 0xFFAAB3BC;

    public static void setupNavbar(Activity activity) {
        View navigationBar = activity.findViewById(R.id.navigation_bar);
        if (navigationBar == null) return;

        View navDashTab = activity.findViewById(R.id.nav_dash_tab);
        View navMenuTab = activity.findViewById(R.id.nav_menu_tab);
        View navOrdersTab = activity.findViewById(R.id.nav_orders_tab);
        View navHistoryTab = activity.findViewById(R.id.nav_history_tab);
        View navAccountTab = activity.findViewById(R.id.nav_account_tab);

        ImageButton navDashIcon = activity.findViewById(R.id.nav_dash_icon);
        ImageButton navMenuIcon = activity.findViewById(R.id.nav_menu_icon);
        ImageButton navOrdersIcon = activity.findViewById(R.id.nav_orders_icon);
        ImageButton navHistoryIcon = activity.findViewById(R.id.nav_history_icon);
        ImageButton navAccountIcon = activity.findViewById(R.id.nav_account_icon);

        TextView navDashLabel = activity.findViewById(R.id.nav_dash_label);
        TextView navMenuLabel = activity.findViewById(R.id.nav_menu_label);
        TextView navOrdersLabel = activity.findViewById(R.id.nav_orders_label);
        TextView navHistoryLabel = activity.findViewById(R.id.nav_history_label);
        TextView navAccountLabel = activity.findViewById(R.id.nav_account_label);

        applySystemBarInsets(navigationBar);

        // Set Navigation
        setClickListener(activity, navDashTab, navDashIcon, SellerDashboard.class);
        setClickListener(activity, navMenuTab, navMenuIcon, SellerMenu.class);
        setClickListener(activity, navOrdersTab, navOrdersIcon, CurrentOrders.class);
        setClickListener(activity, navHistoryTab, navHistoryIcon, Transactions.class);
        setClickListener(activity, navAccountTab, navAccountIcon, SellerUserProfile.class);

        // Set Default States
        setInactive(navDashIcon, navDashLabel);
        setInactive(navMenuIcon, navMenuLabel);
        setInactive(navOrdersIcon, navOrdersLabel);
        setInactive(navHistoryIcon, navHistoryLabel);
        setInactive(navAccountIcon, navAccountLabel);

        // Highlight active tab
        if (activity instanceof SellerDashboard) {
            setActive(navDashIcon, navDashLabel);
        } else if (activity instanceof SellerMenu) {
            setActive(navMenuIcon, navMenuLabel);
        } else if (activity instanceof CurrentOrders) {
            setActive(navOrdersIcon, navOrdersLabel);
        } else if (activity instanceof Transactions) {
            setActive(navHistoryIcon, navHistoryLabel);
        } else if (activity instanceof SellerUserProfile) {
            setActive(navAccountIcon, navAccountLabel);
        }
    }

    private static void setClickListener(Activity activity, View tab, ImageButton icon, Class<?> target) {
        if (tab != null) tab.setOnClickListener(v -> navigateTo(activity, target));
        if (icon != null) icon.setOnClickListener(v -> navigateTo(activity, target));
    }

    private static void applySystemBarInsets(View navigationBar) {
        final int left = navigationBar.getPaddingLeft();
        final int top = navigationBar.getPaddingTop();
        final int right = navigationBar.getPaddingRight();
        final int bottom = navigationBar.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(navigationBar, (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(left, top, right, bottom + insets.bottom);
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(navigationBar);
    }

    private static void navigateTo(Activity activity, Class<?> targetClass) {
        if (!activity.getClass().equals(targetClass)) {
            Intent intent = new Intent(activity, targetClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.startActivity(intent);
            activity.overridePendingTransition(0, 0);
        }
    }

    private static void setActive(ImageButton button, TextView label) {
        if (button != null) button.setColorFilter(ACTIVE_COLOR);
        if (label != null) label.setTextColor(ACTIVE_COLOR);
    }

    private static void setInactive(ImageButton button, TextView label) {
        if (button != null) button.setColorFilter(INACTIVE_COLOR);
        if (label != null) label.setTextColor(INACTIVE_COLOR);
    }
}
