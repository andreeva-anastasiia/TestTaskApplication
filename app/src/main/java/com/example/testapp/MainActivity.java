package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp.adapter.CurrencyAdapter;
import com.example.testapp.model.Currency;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    CurrencyAdapter currencyAdapter;
    RecyclerView currencyRecycler;
    List<Currency> currenciesList;
    DailyUpdate dailyUpdate;
    String url = "https://www.cbr-xml-daily.ru/daily_json.js";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currenciesList = new ArrayList<>();
        currencyAdapter = new CurrencyAdapter(this, currenciesList);
        currencyRecycler = findViewById(R.id.currencyRecycler);
        currencyRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        currencyRecycler.setAdapter(currencyAdapter);
        setCurrencyRecycler(currenciesList);
        dailyUpdate = new DailyUpdate();
        dailyUpdate.execute();
    }

    public void onClick(View view) {
        dailyUpdate.cancel(true);
        currenciesList.clear();
        setCurrencyRecycler(currenciesList);
        dailyUpdate = new DailyUpdate();
        dailyUpdate.execute();
    }

    private void setCurrencyRecycler(List<Currency> currenciesList) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONObject currenciesObject = object.getJSONObject("Valute");

                final String[] keys = new String[] { "AUD", "AZN", "GBP",
                        "AMD", "BYN", "BGN", "BRL", "HUF", "HKD",
                        "DKK", "USD", "EUR", "INR", "KZT", "CAD", "KGS",
                        "CNY", "MDL", "NOK", "PLN", "RON", "XDR", "SGD",
                        "TJS", "TRY", "TMT", "UZS", "UAH", "CZK", "SEK",
                        "CHF", "ZAR", "KRW", "JPY"
                };
                int i = 0;
                for (String key : keys) {
                    JSONObject currencyData = currenciesObject.getJSONObject(key);
                    currenciesList.add(new Currency(i, currencyData.getString("CharCode"),
                            currencyData.getDouble("Value"), currencyData.getString("Name")));
                    i++;
                }
                currencyAdapter = new CurrencyAdapter(this, currenciesList);
                currencyRecycler.setAdapter(currencyAdapter);
            }
            catch (JSONException  e){
                e.printStackTrace();
            }
        },
                error -> Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void CurrenciesValuesUpdating(List<Currency> currenciesList) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                for (int i=0; i<currenciesList.size(); i++){
                    JSONObject currencyForUpdate = object.getJSONObject("Valute").getJSONObject(currenciesList.get(i).getCharCode());
                    currenciesList.get(i).setValue(currencyForUpdate.getDouble("Value"));
                }
                currencyAdapter = new CurrencyAdapter(this, currenciesList);
                currencyRecycler.setAdapter(currencyAdapter);
            }
            catch (JSONException  e){
                e.printStackTrace();
            }
        },
                error -> Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    class DailyUpdate extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            CurrenciesValuesUpdating(currenciesList);
            System.out.println("Updated" + values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                int counter = 1;
                while (counter > 0){
                    TimeUnit.SECONDS.sleep(86400);
                    publishProgress(counter);
                    counter++;
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}



