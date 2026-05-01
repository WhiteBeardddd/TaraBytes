package com.example.midtermsexam_beauty.views.seller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.SellerNavCard;
import com.example.midtermsexam_beauty.utilities.AppNavigator;
import com.example.midtermsexam_beauty.utilities.SessionManager;
import com.example.midtermsexam_beauty.utilities.SupabaseAuthService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SellerDashboard extends AppCompatActivity {

    private SessionManager sessionManager;
    private SupabaseAuthService authService;
    private ExecutorService executor;
    private TextView tvTotalSales, tvCompletedOrders, tvPendingOrders, tvTotalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        sessionManager = new SessionManager(this);
        authService = new SupabaseAuthService();
        executor = Executors.newSingleThreadExecutor();

        SellerNavCard.setupNavbar(this);

        tvTotalSales = findViewById(R.id.tvTotalSales);
        tvCompletedOrders = findViewById(R.id.tvCompletedOrders);
        tvPendingOrders = findViewById(R.id.tvPendingOrders);
        tvTotalItems = findViewById(R.id.tvTotalItems);

        Button btnSellerLogout = findViewById(R.id.btnSellerLogout);
        btnSellerLogout.setOnClickListener(v -> AppNavigator.logout(this, sessionManager));

        loadStatistics();
    }

    private void loadStatistics() {
        executor.execute(() -> {
            String token = sessionManager.getToken();
            String authId = sessionManager.getUserId();
            
            // 1. Resolve Seller ID from Profile
            String sellerId = authService.getSellerIdByAuthId(token, authId);
            
            if (sellerId != null) {
                // 2. Fetch Stats
                SupabaseAuthService.SellerStats stats = authService.getStats(token, sellerId);
                
                runOnUiThread(() -> {
                    tvTotalSales.setText(String.format("₱%.2f", stats.totalSales));
                    tvCompletedOrders.setText(String.valueOf(stats.completedOrders));
                    tvPendingOrders.setText(String.valueOf(stats.pendingOrders));
                    tvTotalItems.setText(String.valueOf(stats.totalItems));
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (executor != null) executor.shutdown();
        super.onDestroy();
    }
}
