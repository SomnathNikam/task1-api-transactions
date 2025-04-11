package com.example.apitransactionswithbiometrics.listener;

import android.provider.SyncStateContract;

import com.example.apitransactionswithbiometrics.model.request.LoginRequest;
import com.example.apitransactionswithbiometrics.model.response.LoginResponse;
import com.example.apitransactionswithbiometrics.model.Transactions;
import com.example.apitransactionswithbiometrics.retrofit.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST(ApiEndpoints.login_endpoint)
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET(ApiEndpoints.transaction_endpoint)
    Call<List<Transactions>> getTransactions(@Header("Authorization") String token);

}
