package com.example.apitransactionswithbiometrics.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.apitransactionswithbiometrics.R;
import com.example.apitransactionswithbiometrics.databinding.ActivityBiometricBinding;

public class BiometricActivity extends AppCompatActivity {

    private ActivityBiometricBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBiometricBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BiometricPrompt biometricPrompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        startActivity(new Intent(BiometricActivity.this, TransactionsActivity.class));
                        finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(BiometricActivity.this, "Biometric Auth Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Authenticate using fingerprint or face")
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(true)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}