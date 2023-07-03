package com.example.revision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnAddRecord(View v)
    {
        startActivity(new Intent(MainActivity.this, RentCar.class));
        finish();
    }

    public void btnShowRecords(View v)
    {
        startActivity(new Intent(MainActivity.this, ViewCars.class));

    }
}