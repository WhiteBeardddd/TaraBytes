package com.example.midtermsexam_beauty.utilities;

import android.app.Activity;
import android.content.Intent;

import com.example.midtermsexam_beauty.MainActivity;
import com.example.midtermsexam_beauty.views.seller.SellerDashboard;
import com.example.midtermsexam_beauty.views.user.Homepage;
import com.example.midtermsexam_beauty.views.user.LoginActivity;

public final class AppNavigator {

    private AppNavigator() {
    }

    public static void openLanding(Activity activity, boolean clearTask) {
        Intent intent = new Intent(activity, MainActivity.class);
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        activity.startActivity(intent);
        if (clearTask) {
            activity.finish();
        }
    }

    public static void openLogin(Activity activity, boolean clearTask) {
        Intent intent = new Intent(activity, LoginActivity.class);
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        activity.startActivity(intent);
        if (clearTask) {
            activity.finish();
        }
    }

    public static void openAuthenticatedHome(Activity activity, boolean isSeller, boolean clearTask) {
        Class<?> destination = isSeller ? SellerDashboard.class : Homepage.class;
        Intent intent = new Intent(activity, destination);
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        activity.startActivity(intent);
        if (clearTask) {
            activity.finish();
        }
    }

    public static void logout(Activity activity, SessionManager sessionManager) {
        sessionManager.clearSession();
        openLanding(activity, true);
    }
}
