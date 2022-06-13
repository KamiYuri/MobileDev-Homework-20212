package com.example.mobiledevhomework20212;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private TextView topInput, bottomInput, currentInput;
    private Button number0Button, number1Button, number2Button,
            number3Button, number4Button, number5Button,
            number6Button, number7Button, number8Button,
            number9Button, ceButton, bsButton, dotButton;

    Spinner topSpinner, bottomSpinner;

    private static final List<String> currency;
    public static Map<String, Double> rateList;

    static {
        currency = Arrays.asList("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD");
        rateList = new HashMap<>();
        rateList.put("USD", 1D);
        rateList.put("EUR", 1.17D);
        rateList.put("JPY", 0.0095);
        rateList.put("GBP", 1.29);
        rateList.put("AUD", 0.71);
        rateList.put("CAD", 0.76);
        rateList.put("CHF", 1.09);
        rateList.put("CNY", 0.15);
        rateList.put("SEK", 0.11);
        rateList.put("NZD", 0.66);
    }

    String topCurrency, bottomCurrency;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currency);

        topCurrency = "USD";
        bottomCurrency = "USD";

        topSpinner = findViewById(R.id.topSpinner);
        bottomSpinner = findViewById(R.id.bottomSpinner);

        topInput = findViewById(R.id.topInput);
        bottomInput = findViewById(R.id.bottomInput);
        currentInput = topInput;

        topSpinner.setAdapter(adapter);
        topSpinner.setSelection(0);
        topSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                topCurrency = topSpinner.getSelectedItem().toString();
                bottomInput.setText(String.valueOf(convert(topCurrency, bottomCurrency, Double.parseDouble(topInput.getText().toString()))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bottomSpinner.setAdapter(adapter);
        bottomSpinner.setSelection(0);
        bottomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bottomCurrency = bottomSpinner.getSelectedItem().toString();
                topInput.setText(String.valueOf(convert(bottomCurrency, topCurrency, Double.parseDouble(bottomInput.getText().toString()))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        topInput.setOnClickListener(view -> {
            currentInput = topInput;
        });
        bottomInput.setOnClickListener(view -> {
            currentInput = bottomInput;
        });

        initButton();
    }

    private void initButton(){
        number0Button = findViewById(R.id.button0);
        number0Button.setOnClickListener(view -> handleSelectButton(number0Button));

        number1Button = findViewById(R.id.button1);
        number1Button.setOnClickListener(view -> handleSelectButton(number1Button));

        number2Button = findViewById(R.id.button2);
        number2Button.setOnClickListener(view -> handleSelectButton(number2Button));


        number3Button = findViewById(R.id.button3);
        number3Button.setOnClickListener(view -> handleSelectButton(number3Button));


        number4Button = findViewById(R.id.button4);
        number4Button.setOnClickListener(view -> handleSelectButton(number4Button));


        number5Button = findViewById(R.id.button5);
        number5Button.setOnClickListener(view -> handleSelectButton(number5Button));


        number6Button = findViewById(R.id.button6);
        number6Button.setOnClickListener(view -> handleSelectButton(number6Button));


        number7Button = findViewById(R.id.button7);
        number7Button.setOnClickListener(view -> handleSelectButton(number7Button));


        number8Button = findViewById(R.id.button8);
        number8Button.setOnClickListener(view -> handleSelectButton(number8Button));


        number9Button = findViewById(R.id.button9);
        number9Button.setOnClickListener(view -> handleSelectButton(number9Button));

        bsButton = findViewById(R.id.buttonBs);
        bsButton.setOnClickListener(view -> {
            if(currentInput.length() == 1){
                currentInput.setText("0");
            } else {
                currentInput.setText(currentInput.getText().toString().substring(0, currentInput.length() - 1));
            }
        });

        ceButton = findViewById(R.id.buttonCE);
        ceButton.setOnClickListener(view -> {
            currentInput.setText("0");
        });
    }

    public Double convert(String from, String to, Double value) {
        return (rateList.get(from) / rateList.get(to)) * value;
    }

    @SuppressLint("SetTextI18n")
    private void handleSelectButton(Button clickedButton){
        if(currentInput.getText().toString().equals("0.0") || currentInput.getText().toString().equals("0")) { //Nếu chỉ có số 0
            currentInput.setText(clickedButton.getText().toString());
        }
        else {
            currentInput.setText(currentInput.getText().toString() + clickedButton.getText().toString());
        }

        if (currentInput == topInput){
            bottomInput.setText(String.valueOf(convert(topCurrency, bottomCurrency, Double.parseDouble(topInput.getText().toString()))));
        } else {
            topInput.setText(String.valueOf(convert(bottomCurrency, topCurrency, Double.parseDouble(bottomInput.getText().toString()))));
        }
    }
}