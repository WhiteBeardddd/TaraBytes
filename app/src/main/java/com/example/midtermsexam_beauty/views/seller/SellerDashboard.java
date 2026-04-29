package com.example.midtermsexam_beauty.views.seller;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.utilities.AppNavigator;
import com.example.midtermsexam_beauty.utilities.SessionManager;

public class SellerDashboard extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        sessionManager = new SessionManager(this);

        Button btnSellerLogout = findViewById(R.id.btnSellerLogout);
        btnSellerLogout.setOnClickListener(v -> AppNavigator.logout(this, sessionManager));
    }
}
