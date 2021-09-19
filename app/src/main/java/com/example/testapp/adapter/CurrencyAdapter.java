package com.example.testapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.Conversion;
import com.example.testapp.R;
import com.example.testapp.model.Currency;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    Context context;
    List<Currency> currencies;

    public CurrencyAdapter(Context context, List<Currency> currencies) {
        this.context = context;
        this.currencies = currencies;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View currencyItems = LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false);
        return new CurrencyViewHolder(currencyItems);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.currencyCharCode.setText(currencies.get(position).getCharCode());
        holder.currencyValue.setText((currencies.get(position).getValue()).toString());
        holder.currencyName.setText(currencies.get(position).getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Conversion.class);
            intent.putExtra("charCode", currencies.get(position).getCharCode());
            intent.putExtra("value", currencies.get(position).getValue());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public static final class CurrencyViewHolder extends RecyclerView.ViewHolder{

        TextView currencyCharCode;
        TextView currencyValue;
        TextView currencyName;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyCharCode = itemView.findViewById(R.id.textViewCharCode);
            currencyValue = itemView.findViewById(R.id.textViewValue);
            currencyName = itemView.findViewById(R.id.textViewName);
        }
    }
}
