package com.example.midtermsexam_beauty.views.seller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.SellerNavCard;

public class Transactions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        SellerNavCard.setupNavbar(this);
    }
}
