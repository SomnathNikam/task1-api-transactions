package com.example.apitransactionswithbiometrics.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions_table")
public class TransactionsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String transactionId;
    private String date;
    private String amount;
    private String description;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

