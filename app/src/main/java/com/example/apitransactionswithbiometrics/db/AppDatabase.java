package com.example.apitransactionswithbiometrics.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.apitransactionswithbiometrics.dao.TransactionsDao;
import com.example.apitransactionswithbiometrics.entities.TransactionsEntity;

@Database(entities = {TransactionsEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract TransactionsDao transactionsDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "transactions_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
