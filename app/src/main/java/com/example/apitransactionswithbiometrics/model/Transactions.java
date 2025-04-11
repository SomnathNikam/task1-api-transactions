package com.example.apitransactionswithbiometrics.model;

public class Transactions {
    private String id;
    private String amount;
    private String date;

    private String description;

    public String getId() {
        return id;
    }
    public String getAmount() {
        return amount;
    }
    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
