package com.example.revision;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.customer_edit_layout);
                dialog.show();
                dialog.setCancelable(false);

                TextInputEditText etName = dialog.findViewById(R.id.etNameEdit);
                etName.setText(model.getName());
                TextInputEditText etCell = dialog.findViewById(R.id.etCellEdit);
                etCell.setText(model.getCell());
                TextInputEditText etCnic = dialog.findViewById(R.id.etCnicEdit);
                etCnic.setText(model.getCnic());
                Spinner spinner = dialog.findViewById(R.id.spinnerEdit);
                String []cars = {"Suzuki", "Corolla", "Honda"};
                ArrayAdapter aa = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, cars);
                spinner.setAdapter(aa);

                int pos = 0;
                for (int i=0; i<cars.length; i++)
                {
                    if(cars[i].equals(model.getCar()))
                        pos = i;
                }

                spinner.setSelection(pos);

                final String[] car = {""};

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        car[0] = cars[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                Button btnUpdate = dialog.findViewById(R.id.btnOkEdit);
                Button btnCancel = dialog.findViewById(R.id.btnCancelEdit);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("name", etName.getText().toString().trim());
                        data.put("cell", etCell.getText().toString().trim());
                        data.put("cnic", etCnic.getText().toString().trim());
                        data.put("car", car[0]);


                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Customers")
                                .child(model.getCnic())
                                .updateChildren(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(view.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        holder.ivSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.send_sms_layout);
                dialog.setCancelable(false);

                dialog.show();

                Button btnCancel = dialog.findViewById(R.id.btnCancelSms);
                Button btnOk = dialog.findViewById(R.id.btnOkSms);
                TextInputEditText etCellNumber = dialog.findViewById(R.id.etCellNumber);
                TextInputEditText etSms = dialog.findViewById(R.id.etSms);
                etCellNumber.setText(model.getCell());
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "Ok", Toast.LENGTH_SHORT).show();
                    }
                });
                
            }
        });

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
