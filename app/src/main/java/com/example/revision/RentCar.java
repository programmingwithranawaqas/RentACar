package com.example.revision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RentCar extends AppCompatActivity{

    String []cars = {"Suzuki", "Corolla", "Honda"};
    Spinner spinner;

    Button btnAdd, btnCancel;
    TextInputEditText etName, etCell, etCnic;
    String spinnerItem="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        spinner = findViewById(R.id.spinner);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etName);
        etCnic = findViewById(R.id.etCnic);
        etCell = findViewById(R.id.etCell);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cars);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerItem = cars[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RentCar.this, MainActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String cell = etCell.getText().toString().trim();
                String cnic = etCnic.getText().toString().trim();
                if(!spinnerItem.isEmpty() && !name.isEmpty() && !cell.isEmpty()
                && !cnic.isEmpty())
                {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("name",name);
                    data.put("cell", cell);
                    data.put("cnic",cnic);
                    data.put("car", spinnerItem);


                    FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Customers")
                            .child(cnic)
                            .updateChildren(data)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RentCar.this, "Data Added", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RentCar.this, MainActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RentCar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });

    }
}