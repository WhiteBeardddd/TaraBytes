package com.example.midtermsexam_beauty;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.midtermsexam_beauty.utilities.AppNavigator;
import com.example.midtermsexam_beauty.utilities.SessionManager;
import com.example.midtermsexam_beauty.views.user.LoginActivity;
import com.example.midtermsexam_beauty.views.user.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    Button logInBtn;
    Button signUpBtn;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            AppNavigator.openAuthenticatedHome(this, sessionManager.isSeller(), true);
            return;
        }

        logInBtn = findViewById(R.id.logInButton);
        signUpBtn = findViewById(R.id.signUpButton);

        logInBtn.setOnClickListener(v -> {
            Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(toLogin);
        });

        signUpBtn.setOnClickListener(v -> {
            Intent toRegister = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(toRegister);
        });
    }
}
