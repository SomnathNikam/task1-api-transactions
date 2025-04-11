package com.example.apitransactionswithbiometrics.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.apitransactionswithbiometrics.R;
import com.example.apitransactionswithbiometrics.databinding.ActivityLoginBinding;
import com.example.apitransactionswithbiometrics.listener.ApiService;
import com.example.apitransactionswithbiometrics.model.request.LoginRequest;
import com.example.apitransactionswithbiometrics.model.response.LoginResponse;
import com.example.apitransactionswithbiometrics.retrofit.ApiClient;
import com.example.apitransactionswithbiometrics.utils.SecureSharedPrefs;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ApiService apiService;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Options: NONE, BASIC, HEADERS, BODY

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.prepstripe.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(ApiService.class);
//        apiService = ApiClient.getClient(token).create(ApiService.class);
        binding.btnLogin.setOnClickListener(v -> loginUser());

        binding.etpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() == 14) {
                    hideInputKeyboard();
                }
            }
        });


    }

    private void hideInputKeyboard() {
        InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            inputMethod.hideSoftInputFromWindow((view).getWindowToken(), 0);
        }
    }

    private void loginUser() {
        LoginRequest request = new LoginRequest(
                binding.etemail.getText().toString(),
                binding.etpassword.getText().toString()
        );

        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        SharedPreferences prefs = SecureSharedPrefs.getEncryptedPrefs(LoginActivity.this);
                        prefs.edit().putString("token", response.body().getToken()).apply();

                        startActivity(new Intent(LoginActivity.this, BiometricActivity.class));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}