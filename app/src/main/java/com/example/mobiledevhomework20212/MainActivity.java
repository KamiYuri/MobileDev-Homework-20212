package com.example.mobiledevhomework20212;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText nameInput, mssvInput, cccdInput, phoneNumberInput, emailInput, dateInput, nativeLocalInput, addressInput;
    private RadioButton khmtRadio, ktmtRadio;
    private CheckBox cCheckBox, javaCheckBox, pythonCheckBox, dartCheckBox, conditionCheckBox;
    private Button submitButton;

    final Calendar myCalendar= Calendar.getInstance();

    String name, mssv, cccd, phoneNumber, email, date, nativeLocal, address, major;
    ArrayList<String> programLanguage = new ArrayList<>();

    boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();

    }

    private void setInit() {
        nameInput = findViewById(R.id.nameInput);
        mssvInput = findViewById(R.id.mssvInput);
        cccdInput = findViewById(R.id.cccdInput);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        emailInput = findViewById(R.id.emailInput);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };

        dateInput = findViewById(R.id.dateInput);
        dateInput.setOnClickListener(view -> {
            new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        nativeLocalInput = findViewById(R.id.nativeLocalInput);
        addressInput = findViewById(R.id.addressInput);

        khmtRadio = findViewById(R.id.khmtRadio);
        ktmtRadio = findViewById(R.id.ktmtRadio);

        cCheckBox = findViewById(R.id.cCheckBox);
        javaCheckBox = findViewById(R.id.javaCheckBox);
        pythonCheckBox = findViewById(R.id.pythonCheckBox);
        dartCheckBox = findViewById(R.id.dartCheckBox);

        conditionCheckBox = findViewById(R.id.conditionCheckBox);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(view -> {
            submit();
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateInput.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void submit() {
        if(checkInput(nameInput)){
            name = nameInput.getText().toString();
        }
        if(checkInput(mssvInput)){
            mssv = mssvInput.getText().toString();
        }
        if(checkInput(cccdInput)){
            cccd = cccdInput.getText().toString();
        }
        if(checkInput(phoneNumberInput)){
            phoneNumber = phoneNumberInput.getText().toString();
        }
        if(checkInput(emailInput)){
            email = emailInput.getText().toString();
        }
        if(!conditionCheckBox.isChecked()){
            error = false;
            showToast("Cần phải đồng ý điều kiện");
        }

        date = dateInput.getText().toString();
        nativeLocal = nativeLocalInput.getText().toString();
        address = addressInput.getText().toString();

        if(khmtRadio.isChecked()){
            major = "KHMT";
        } else if(ktmtRadio.isChecked()){
            major = "KTMT";
        }

        if(cCheckBox.isChecked()){
            programLanguage.add("C");
        }
        if(javaCheckBox.isChecked()){
            programLanguage.add("Java");
        }
        if(pythonCheckBox.isChecked()){
            programLanguage.add("Python");
        }
        if(dartCheckBox.isChecked()){
            programLanguage.add("Dart");
        }

        if(!error){
            showToast("Thành công");
        }
    }

    private void showToast(String message) {
        Toast toast = new Toast(this);
        toast.setText(message);
        toast.show();
    }

    private boolean checkInput(EditText input) {
        String data = input.getText().toString();
        if(data.isEmpty()){
            error = true;
            input.setError("Trống");
            return false;
        }

        error = false;
        return true;
    }
}