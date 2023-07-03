package com.example.revision;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerAdapter extends FirebaseRecyclerAdapter<Customer, CustomerAdapter.ViewHolder> {

    public View v;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CustomerAdapter(@NonNull FirebaseRecyclerOptions<Customer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Customer model) {
        holder.tvName.setText(model.getName());
        holder.tvCnic.setText(model.getCnic());
        String cellNumber = model.getCell();

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+cellNumber));
                v.getContext().startActivity(i);
            }
        });


        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Customers")
                        .child(model.getCnic())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.customer_layout, parent, false
        );

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivCall, ivSms, ivEdit, ivDel, ivPic;
        TextView tvName, tvCnic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCall = itemView.findViewById(R.id.ivCall);
            ivSms = itemView.findViewById(R.id.ivSms);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDelete);
            ivPic = itemView.findViewById(R.id.ivCarPic);
            tvName = itemView.findViewById(R.id.tvName);
            tvCnic = itemView.findViewById(R.id.tvCNIC);


        }
    }
}
