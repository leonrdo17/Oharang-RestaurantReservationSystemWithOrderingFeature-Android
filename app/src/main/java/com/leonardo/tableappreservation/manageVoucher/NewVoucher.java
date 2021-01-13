package com.leonardo.tableappreservation.manageVoucher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leonardo.tableappreservation.R;

public class NewVoucher extends AppCompatActivity {
    private EditText editTextEmail;
    MaterialSpinner voucherSpinner;

    Button saveButton;
    String voucher;
    String email;

    Query query;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference voucherReference = fStore.collection("vouchers");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_voucher);

        editTextEmail = findViewById(R.id.emailText);
        voucherSpinner = findViewById(R.id.statusSpinner);
        saveButton = findViewById(R.id.saveButton);

        voucherSpinner.setItems("RM50 OFF VOUCHER");

        voucherSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {

                    case 0:
                        voucher = "RM50 OFF VOUCHER";

                        return;

                }

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewVoucher();
            }
        });


    }

    private void saveNewVoucher() {
        email = editTextEmail.getText().toString();

        if (voucher == null) {
            Toast.makeText(this, "Please select the voucher!", Toast.LENGTH_SHORT).show();
            return;
        } else if (email.trim().isEmpty()){
            Toast.makeText(this, "Please insert the email!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            query = voucherReference.whereEqualTo("email", email)
                    .whereEqualTo("voucher", voucher);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()) {
                            CollectionReference voucherRef = FirebaseFirestore.getInstance()
                                    .collection("vouchers");
                            voucherRef.add(new Voucher(email, voucher));
                            Toast.makeText(NewVoucher.this, "Voucher has been added!", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {
                            Toast.makeText(NewVoucher.this, "The selected email is already have a voucher!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
    }
}
