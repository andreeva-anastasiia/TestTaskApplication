package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Conversion extends AppCompatActivity {

    TextView charCode;
    TextView convertedAmount;
    EditText amount_in_RUB;
    Button convertButton;
    DecimalFormat Formatter = new DecimalFormat("#.####"); //4 знака после запятой по аналогии с Value из JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        charCode = findViewById(R.id.XXX);
        convertedAmount = findViewById(R.id.convertedAmount);
        amount_in_RUB = findViewById(R.id.amount_in_RUB);

        convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(v -> {
            double value = getIntent().getDoubleExtra("value", 0);
            int amount = Integer.parseInt(amount_in_RUB.getText().toString());
            double ConvertedValue = amount/value;
            convertedAmount.setText(Formatter.format(ConvertedValue));
        });

        charCode.setText(getIntent().getStringExtra("charCode"));
    }
}