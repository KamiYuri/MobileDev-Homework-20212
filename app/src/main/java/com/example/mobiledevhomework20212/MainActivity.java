package com.example.mobiledevhomework20212;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button number0Button, number1Button, number2Button,
            number3Button, number4Button, number5Button,
            number6Button, number7Button, number8Button,
            number9Button, ceButton, cButton,
            bsButton, divisionButton, multiplicationButton,
            subtractionButton, additionButton, addOrSubButton,
            dotButton, equalButton;
    TextView upLineTextView, bottomLineTextView;
    private String param1, param2, operator, result;
    private boolean errorFlag, equalFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        param1 = "0";
        param2 = "0";
        operator = "";

        initButton();
        upLineTextView = findViewById(R.id.upLineTextView);
        bottomLineTextView = findViewById(R.id.bottomLineTextView);

        upLineTextView.setText("");
        upLineTextView.setVisibility(View.GONE);
        bottomLineTextView.setText("0");
    }

    private void initButton() {
        number0Button = findViewById(R.id.number_0_button);
        number0Button.setOnClickListener(view -> input(number0Button));

        number1Button = findViewById(R.id.number_1_button);
        number1Button.setOnClickListener(view -> input(number1Button));

        number2Button = findViewById(R.id.number_2_button);
        number2Button.setOnClickListener(view -> input(number2Button));


        number3Button = findViewById(R.id.number_3_button);
        number3Button.setOnClickListener(view -> input(number3Button));


        number4Button = findViewById(R.id.number_4_button);
        number4Button.setOnClickListener(view -> input(number4Button));


        number5Button = findViewById(R.id.number_5_button);
        number5Button.setOnClickListener(view -> input(number5Button));


        number6Button = findViewById(R.id.number_6_button);
        number6Button.setOnClickListener(view -> input(number6Button));


        number7Button = findViewById(R.id.number_7_button);
        number7Button.setOnClickListener(view -> input(number7Button));


        number8Button = findViewById(R.id.number_8_button);
        number8Button.setOnClickListener(view -> input(number8Button));


        number9Button = findViewById(R.id.number_9_button);
        number9Button.setOnClickListener(view -> input(number9Button));


        ceButton = findViewById(R.id.ce_button);
        ceButton.setOnClickListener(view -> reset());

        cButton = findViewById(R.id.c_button);
        cButton.setOnClickListener(view -> {
            if(equalFlag){
                return;
            }

            bottomLineTextView.setText("0");

            if(operator.isEmpty()){
                param1 = bottomLineTextView.getText().toString();
            } else {
                param2 = bottomLineTextView.getText().toString();
            }
        });

        bsButton = findViewById(R.id.bs_button);
        bsButton.setOnClickListener(view -> {
            if(equalFlag){
                return;
            }

            String currentInput = bottomLineTextView.getText().toString();

            if(currentInput.length() == 1){
                bottomLineTextView.setText("0");
            } else {
                bottomLineTextView.setText(currentInput.substring(0, currentInput.length() - 1));
            }

            if(operator.isEmpty()){
                param1 = bottomLineTextView.getText().toString();
            } else {
                param2 = bottomLineTextView.getText().toString();
            }
        });

        divisionButton = findViewById(R.id.division_button);
        divisionButton.setOnClickListener(view -> setOperator(divisionButton));

        multiplicationButton = findViewById(R.id.multiplication_button);
        multiplicationButton.setOnClickListener(view -> setOperator(multiplicationButton));

        subtractionButton = findViewById(R.id.subtraction_button);
        subtractionButton.setOnClickListener(view -> setOperator(subtractionButton));

        additionButton = findViewById(R.id.addition_button);
        additionButton.setOnClickListener(view -> setOperator(additionButton));

        addOrSubButton = findViewById(R.id.addOrSub_button);
        addOrSubButton.setOnClickListener(view -> {
            if(operator.equals("+")){
                setOperator(subtractionButton);
            } else {
                setOperator(additionButton);
            }
        });

        dotButton = findViewById(R.id.dot_button);

        equalButton = findViewById(R.id.equal_button);
        equalButton.setOnClickListener(view -> showResult());
    }

    private void reset() {
        equalFlag = errorFlag = false;
        param1 = param2 = "0";
        operator = "";

        upLineTextView.setText("");
        upLineTextView.setVisibility(View.GONE);

        bottomLineTextView.setText("0");
    }

    @SuppressLint("SetTextI18n")
    private void showResult() {
        if(errorFlag) {
            reset();
            return;
        }

        if(operator.isEmpty()){
            return;
        }

        if(equalFlag) {
            boolean t = Float.parseFloat(result) <= -999999999L;
            if(t){
                errorFlag = true;
                reset();
                showToast("Qua dai");
                return;
            }
            param1 = result;
        }

        equalFlag = true;
        upLineTextView.setText(param1 + " " + operator + " " + param2);

        result = "";
        switch (operator){
            case "+":
                if(Math.log10(Double.parseDouble(param1) + Double.parseDouble(param2)) > 9){
                    errorFlag = true;
                    reset();
                    showToast("Qua dai");
                    return;
                }
                result = String.valueOf(Integer.parseInt(param1) + Integer.parseInt(param2));
                break;
            case "-":
                result = String.valueOf(Integer.parseInt(param1) - Integer.parseInt(param2));
                break;
            case "x":
                if(Math.log10(Double.parseDouble(param1) * Double.parseDouble(param2)) > 9){
                    errorFlag = true;
                    reset();
                    showToast("Qua dai");
                    return;
                }
                result = String.valueOf(Integer.parseInt(param1) * Integer.parseInt(param2));
                break;
            case "/":
                try {
                    result = String.valueOf(Integer.parseInt(param1) / Integer.parseInt(param2));
                } catch (Exception e){
                    errorFlag = true;
                    reset();
                    showToast("Chia cho 0");
                    return;
                }
                break;
        }
        bottomLineTextView.setText(result);
    }

    private void showToast(String message) {
        Toast toast = new Toast(this);
        toast.setText(message);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    private void setOperator(Button clickedButton) {
        operator = clickedButton.getText().toString();
        upLineTextView.setText(param1 + " " + operator + " ");
        bottomLineTextView.setText(param2);
        upLineTextView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void input(Button clickedButton) {
        if(errorFlag || equalFlag) //Nếu có lỗi hoặc đã ấn '='
            reset();
        //Nếu không có lỗi
        String currentInput = bottomLineTextView.getText().toString();
        if(currentInput.equals("0")){ //Nếu chỉ có số 0
            bottomLineTextView.setText(clickedButton.getText().toString()); //thay thế số 0
        } else if (currentInput.length() < 9)
            bottomLineTextView.setText(bottomLineTextView.getText().toString() + clickedButton.getText().toString());
        if(operator.isEmpty())
            param1 = bottomLineTextView.getText().toString();
        else
            param2 = bottomLineTextView.getText().toString();
    }
}