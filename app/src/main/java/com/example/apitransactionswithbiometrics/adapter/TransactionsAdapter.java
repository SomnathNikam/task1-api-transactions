package com.example.apitransactionswithbiometrics.adapter;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apitransactionswithbiometrics.R;
import com.example.apitransactionswithbiometrics.model.Transactions;

import java.util.ArrayList;
import java.util.List;

/*public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private List<Transactions> transactionList;

    public TransactionsAdapter(List<Transactions> transactions) {
        this.transactionList = transactions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transactions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transactions tx = transactionList.get(position);

        // Displaying currency and amount together like "₹100.00"
        String formattedAmount = tx.getCurrency() + " " + String.format("%.2f", tx.getAmount());
        holder.currency.setText(tx.getCurrency());
        holder.amount.setText(formattedAmount);

        holder.status.setText("Status: " + tx.getStatus());
        holder.description.setText(tx.getDescription() != null ? tx.getDescription() : "No description");
        holder.date.setText(tx.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount, currency, status, description, date;

        ViewHolder(View itemView) {
            super(itemView);
            currency = itemView.findViewById(R.id.currency);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
}*/




/*
public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private List<Transactions> transactionList;
    private List<Transactions> transactionListFull;
    public TransactionsAdapter(List<Transactions> transactions) {
        this.transactionList = transactions;
        this.transactionListFull = new ArrayList<>(transactions);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transactions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transactions tx = transactionList.get(position);
        holder.id.setText(tx.getId());

        holder.amount.setText(tx.getAmount());
        holder.description.setText(tx.getDescription());
        holder.date.setText(tx.getDate());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
    @Override
    public Filter getFilter() {
        return transactionFilter;
    }

    private Filter transactionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transactions> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(transactionListFull); // No filter, return full list
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                // Filter by amount or description (example)
                for (Transactions tx : transactionListFull) {
                    if (tx.getDescription().toLowerCase().contains(filterPattern) ||
                            String.valueOf(tx.getAmount()).contains(filterPattern)) {
                        filteredList.add(tx);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            transactionList.clear();
            if (results.values != null) {
                transactionList.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,amount,description,date;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idtransaction);
            amount = itemView.findViewById(R.id.amount);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
}*/
import android.widget.Filter;
import android.widget.Filterable;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> implements Filterable {
    private List<Transactions> transactionList;
    private List<Transactions> transactionListFull;
    public TransactionsAdapter(List<Transactions> transactions) {
        this.transactionList = transactions;
        this.transactionListFull = new ArrayList<>(transactions); // Store the original list for filtering
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transactions, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transactions tx = transactionList.get(position);
        holder.id.setText(tx.getId());
        holder.amount.setText(String.valueOf(tx.getAmount()));
        holder.description.setText(tx.getDescription());
        holder.date.setText(tx.getDate());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    @Override
    public Filter getFilter() {
        return transactionFilter;
    }

    private Filter transactionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transactions> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(transactionListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Transactions tx : transactionListFull) {
                    if (tx.getDescription().toLowerCase().contains(filterPattern) ||
                            String.valueOf(tx.getAmount()).contains(filterPattern)) {
                        filteredList.add(tx);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            transactionList.clear();
            if (results.values != null) {
                transactionList.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, amount, description, date;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idtransaction);
            amount = itemView.findViewById(R.id.amount);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
}
