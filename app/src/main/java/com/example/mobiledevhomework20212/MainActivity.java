package com.example.mobiledevhomework20212;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    private EditText topInput, bottomInput, currentInput;
    private Button number0Button, number1Button, number2Button,
            number3Button, number4Button, number5Button,
            number6Button, number7Button, number8Button,
            number9Button, ceButton, bsButton, dotButton;

    String[] currency = {
            "VND", "USD", "YEN"
    };

    HashMap<String, Float> rateToUSD = new HashMap<>();

    String topCurrency, bottomCurrency;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rateToUSD.put("VND", 23182F);
        rateToUSD.put("YEN", 134.42F);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currency);

        Spinner topSpinner = findViewById(R.id.topSpinner);
        topSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                topCurrency = topSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        topSpinner.setAdapter(adapter);
        topSpinner.setSelection(0);

        Spinner bottomSpinner = findViewById(R.id.bottomSpinner);
        bottomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bottomCurrency = bottomSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bottomSpinner.setAdapter(adapter);
        bottomSpinner.setSelection(0);

        topInput = findViewById(R.id.topInput);
        topInput.setOnTouchListener((view, motionEvent) -> {
            int inType = topInput.getInputType(); // backup the input type
            topInput.setInputType(InputType.TYPE_NULL); // disable soft input
            topInput.onTouchEvent(motionEvent); // call native handler
            currentInput = topInput;
            topInput.setInputType(inType); // restore input type
            return true; //
        });
        topInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float toprate = rateToUSD.get(topSpinner.getSelectedItem().toString());
                float botrate = rateToUSD.get(bottomSpinner.getSelectedItem().toString());

                bottomInput.setText(String.valueOf(Float.parseFloat(topInput.getText().toString()) * toprate / botrate));
            }
        });

        bottomInput = findViewById(R.id.bottomInput);
        bottomInput.setOnTouchListener((view, motionEvent) -> {
            int inType = bottomInput.getInputType(); // backup the input type
            bottomInput.setInputType(InputType.TYPE_NULL); // disable soft input
            bottomInput.onTouchEvent(motionEvent); // call native handler
            currentInput = bottomInput;
            bottomInput.setInputType(inType); // restore input type
            return true;
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

    @SuppressLint("SetTextI18n")
    private void handleSelectButton(Button clickedButton){
        if(currentInput.getText().toString().equals("0")) { //Nếu chỉ có số 0
            currentInput.setText(clickedButton.getText().toString());
        }
        else {
            currentInput.setText(currentInput.getText().toString() + clickedButton.getText().toString());
        }
    }
}