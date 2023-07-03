package com.example.revision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewCars extends AppCompatActivity {

    RecyclerView rvCars;
    CustomerAdapter cAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cars);
        rvCars = findViewById(R.id.rvCars);
        rvCars.setHasFixedSize(true);
        rvCars.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Customer> options
                = new FirebaseRecyclerOptions.Builder<Customer>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("Customers"), Customer.class)
                .build();

        cAdapter = new CustomerAdapter(options);
        rvCars.setAdapter(cAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        cAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cAdapter.stopListening();
    }
}