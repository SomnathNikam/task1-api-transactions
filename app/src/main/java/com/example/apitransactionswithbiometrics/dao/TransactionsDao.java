package com.example.apitransactionswithbiometrics.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apitransactionswithbiometrics.entities.TransactionsEntity;

import java.util.List;

@Dao
public interface TransactionsDao {

    @Insert
    void insertTransactions(List<TransactionsEntity> transactions);

    @Query("SELECT * FROM transactions_table")
    List<TransactionsEntity> getAllTransactions();

    @Query("DELETE FROM transactions_table")
    void deleteAll();
}
