package com.example.mobiledevhomework20212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EmailAdapter emailAdapter;
    ArrayList<EmailModel> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.emailRecyclerView);

        emails = new ArrayList<>();
        for (int i = 0; i < 50; i ++){
            emails.add(new EmailModel(
                    randomStringGenerator(3, 10),
                    Calendar.getInstance().getTime(),
                    randomStringGenerator(5, 10),
                    randomStringGenerator(5, 50)
            ));
        }

        emailAdapter = new EmailAdapter(emails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(emailAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //handle menu click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static String randomStringGenerator(final int min, final int max)
    {
        final Random random = new Random();
        final StringBuilder randomStringBuilder = new StringBuilder();
        final int range = new Random().nextInt((max - min) + 1) + min;
        for (int i = 0; i < range; ++i)
            randomStringBuilder.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return randomStringBuilder.toString();
    }
}