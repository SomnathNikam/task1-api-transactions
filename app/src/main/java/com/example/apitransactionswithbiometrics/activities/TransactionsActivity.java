package com.example.apitransactionswithbiometrics.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.apitransactionswithbiometrics.R;
import com.example.apitransactionswithbiometrics.adapter.TransactionsAdapter;
import com.example.apitransactionswithbiometrics.dao.TransactionsDao;
import com.example.apitransactionswithbiometrics.databinding.ActivityTransactionsBinding;
import com.example.apitransactionswithbiometrics.db.AppDatabase;
import com.example.apitransactionswithbiometrics.entities.TransactionsEntity;
import com.example.apitransactionswithbiometrics.listener.ApiService;
import com.example.apitransactionswithbiometrics.model.Transactions;
import com.example.apitransactionswithbiometrics.retrofit.ApiClient;
import com.example.apitransactionswithbiometrics.utils.NetworkUtils;
import com.example.apitransactionswithbiometrics.utils.SecureSharedPrefs;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionsActivity extends AppCompatActivity {
    private TransactionsAdapter adapter;
    private ApiService apiService;
    private String token;

    private ActivityTransactionsBinding binding;
    private List<Transactions> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.searchView.setQueryHint("Search Transactions");
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Options: NONE, BASIC, HEADERS, BODY

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        try {
            SharedPreferences prefs = SecureSharedPrefs.getEncryptedPrefs(this);
            token = prefs.getString("token", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.prepstripe.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);

        fetchTransactions();

        binding.logoutBtn.setOnClickListener(v -> {
            try {
                SharedPreferences prefs = SecureSharedPrefs.getEncryptedPrefs(this);
                prefs.edit().clear().apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        if (NetworkUtils.isConnected(this)) {
            fetchTransactions();
        } else {
            loadTransactionsFromRoom();
        }
    }
   private void fetchTransactions() {
       apiService.getTransactions(token).enqueue(new Callback<List<Transactions>>() {
           @Override
           public void onResponse(Call<List<Transactions>> call, Response<List<Transactions>> response) {
               if (response.isSuccessful() && response.body() != null) {
                   List<Transactions> apiTransactions = response.body();
                   adapter = new TransactionsAdapter(apiTransactions);
                   binding.recyclerview.setAdapter(adapter);

                   new Thread(() -> {
                       AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                       TransactionsDao dao = db.transactionsDao();
                       dao.deleteAll(); // Optional: clear previous entries

                       List<TransactionsEntity> entities = new ArrayList<>();
                       for (Transactions t : apiTransactions) {
                           TransactionsEntity e = new TransactionsEntity();
                           e.setTransactionId(t.getId());
                           e.setAmount(t.getAmount());
                           e.setDate(t.getDate());
                           e.setDescription(t.getDescription());
                           entities.add(e);
                       }
                       dao.insertTransactions(entities);
                   }).start();

               } else {
                   Toast.makeText(TransactionsActivity.this, "Server error. Loading offline data.", Toast.LENGTH_SHORT).show();
                   loadTransactionsFromRoom();
               }
           }
           @Override
           public void onFailure(Call<List<Transactions>> call, Throwable t) {
               Toast.makeText(TransactionsActivity.this, "API failed: " + t.getMessage() + "\nLoading offline data.", Toast.LENGTH_SHORT).show();
               loadTransactionsFromRoom();
           }
       });
   }

    private void loadTransactionsFromRoom() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<TransactionsEntity> localData = db.transactionsDao().getAllTransactions();

            List<Transactions> transactions = new ArrayList<>();
            for (TransactionsEntity entity : localData) {
                Transactions t = new Transactions();
                t.setId(entity.getTransactionId());
                t.setAmount(entity.getAmount());
                t.setDate(entity.getDate());
                t.setDescription(entity.getDescription());
                transactions.add(t);
            }
            runOnUiThread(() -> {
                if (!transactions.isEmpty()) {
                    adapter = new TransactionsAdapter(transactions);
                    binding.recyclerview.setAdapter(adapter);
                } else {
                    Toast.makeText(this, "No offline transactions found.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
