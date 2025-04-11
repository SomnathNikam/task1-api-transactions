package com.example.apitransactionswithbiometrics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.apitransactionswithbiometrics.R;
import com.example.apitransactionswithbiometrics.databinding.ActivityLoginBinding;
import com.example.apitransactionswithbiometrics.databinding.ActivitySplashScreenBinding;
import com.example.apitransactionswithbiometrics.utils.SecureSharedPrefs;

public class SplashScreen extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler().postDelayed(() -> checkLoginStatus(), 2000);

    }

    private void checkLoginStatus() {
        try {
            SharedPreferences prefs = SecureSharedPrefs.getEncryptedPrefs(this);
            String token = prefs.getString("token", null);

            if (token != null && !token.isEmpty()) {
                startActivity(new Intent(SplashScreen.this, BiometricActivity.class));
            } else {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        }

        finish();
    }
}